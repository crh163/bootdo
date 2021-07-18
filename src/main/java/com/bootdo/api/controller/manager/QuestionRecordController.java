package com.bootdo.api.controller.manager;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.PsyQuestionTopicRecord;
import com.bootdo.api.entity.property.excel.QuestionExcelProperty;
import com.bootdo.api.entity.property.excel.TopicGapFillingExcelProperty;
import com.bootdo.api.entity.property.excel.TopicSelectExcelProperty;
import com.bootdo.api.entity.req.question.ManagerQueryListReq;
import com.bootdo.api.entity.vo.question.QueryQuestion;
import com.bootdo.api.service.PsyQuestionRecordService;
import com.bootdo.api.service.PsyQuestionService;
import com.bootdo.api.service.PsyQuestionTopicRecordService;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.domain.page.ManPage;
import com.bootdo.common.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/manager/question")
public class QuestionRecordController {

    @Autowired
    private PsyQuestionRecordService psyQuestionRecordService;

    @Autowired
    private PsyQuestionTopicRecordService psyQuestionTopicRecordService;

    @Autowired
    private PsyQuestionService psyQuestionService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @GetMapping("/list")
    @RequiresPermissions("manager:question:list")
    public String list() {
        request.setAttribute("questions", psyQuestionService.list());
        return "manager/question/list";
    }

    @PostMapping("/list")
    @RequiresPermissions("manager:question:list")
    @ResponseBody
    public ManPage list(@RequestBody ManagerQueryListReq queryListReq) {
        return psyQuestionRecordService.selectListBySearchKey(queryListReq);
    }

    @GetMapping("/excelOutBatch")
    @RequiresPermissions("manager:question:excel")
    @ResponseBody
    public void excelOutBatch(String ids) {
        setExcelOutCommonResponse();
        ManagerQueryListReq queryListReq = new ManagerQueryListReq();
        queryListReq.setRecordIds(ids);
        List<QueryQuestion> queryQuestions = psyQuestionRecordService.selectExcelOutList(queryListReq);
        List<QuestionExcelProperty> properties = new ArrayList<>();
        for (int i = 0; i < queryQuestions.size(); i++) {
            QuestionExcelProperty excelProperty = DataUtils.coverData(queryQuestions.get(i), QuestionExcelProperty.class);
            excelProperty.setOrder(String.valueOf(i + 1));
            properties.add(excelProperty);
        }
        try {
            EasyExcel.write(response.getOutputStream(), QuestionExcelProperty.class)
                    .sheet("sheet1")
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .doWrite(properties);
        } catch (Exception e) {
            log.error("问卷导出失败！", e);
        }
    }

    @GetMapping("/excelOut")
    @ResponseBody
    public void excelOut(String id) {
        setExcelOutCommonResponse();
        List<PsyQuestionTopicRecord> topicRecordList = psyQuestionTopicRecordService.list(new QueryWrapper<PsyQuestionTopicRecord>()
                .orderByAsc(ColumnConsts.ORDER_NUM)
                .eq(ColumnConsts.QUESTION_RECORD_ID, id));
        List<TopicSelectExcelProperty> selectExcels = new ArrayList<>();
        List<TopicGapFillingExcelProperty> gapFillingExcels = new ArrayList<>();
        int i=1;
        for (PsyQuestionTopicRecord record : topicRecordList) {
            if ("1".equals(record.getQuestionTopicType())) {
                TopicSelectExcelProperty property = new TopicSelectExcelProperty();
                property.setOrder(String.valueOf(i));
                property.setQuestionTopicText(record.getQuestionTopicText());
                property.setQuestionOptionText(record.getQuestionOptionText());
                property.setQuestionOptionScore(record.getQuestionOptionScore());
                selectExcels.add(property);
            } else if ("2".equals(record.getQuestionTopicType())) {
                TopicGapFillingExcelProperty property = new TopicGapFillingExcelProperty();
                property.setOrder(String.valueOf(i));
                property.setQuestionTopicText(record.getQuestionTopicText());
                property.setQuestionOptionText(record.getQuestionGapFillText());
                gapFillingExcels.add(property);
            }
            i++;
        }
        try {
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
            //选择题答案
            WriteSheet selectSheet = EasyExcel.writerSheet("选择题提交记录").head(TopicSelectExcelProperty.class)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
            excelWriter.write(selectExcels, selectSheet);
            //填空题答案
            if (!CollectionUtils.isEmpty(gapFillingExcels)) {
                WriteSheet gapFillingSheet = EasyExcel.writerSheet("填空题提交记录").head(TopicGapFillingExcelProperty.class)
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
                excelWriter.write(gapFillingExcels, gapFillingSheet);
            }
            excelWriter.finish();
        } catch (Exception e) {
            log.error("导出失败！", e);
        }
    }

    private void setExcelOutCommonResponse() {
        String fileName = null;
        try {
            fileName = URLEncoder.encode("问卷提交记录", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("URLEncoder.encode: ", e);
        }
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

    }

}
