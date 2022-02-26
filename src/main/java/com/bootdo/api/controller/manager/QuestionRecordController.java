package com.bootdo.api.controller.manager;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.PsyQuestionTopic;
import com.bootdo.api.entity.db.PsyQuestionTopicRecord;
import com.bootdo.api.entity.property.excel.QuestionExcelProperty;
import com.bootdo.api.entity.property.excel.TopicGapFillingExcelProperty;
import com.bootdo.api.entity.property.excel.TopicSelectExcelProperty;
import com.bootdo.api.entity.req.question.ManagerQueryListReq;
import com.bootdo.api.entity.vo.question.QueryQuestion;
import com.bootdo.api.service.PsyQuestionRecordService;
import com.bootdo.api.service.PsyQuestionService;
import com.bootdo.api.service.PsyQuestionTopicRecordService;
import com.bootdo.api.service.PsyQuestionTopicService;
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
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/manager/question")
public class QuestionRecordController {

    @Autowired
    private PsyQuestionRecordService psyQuestionRecordService;

    @Autowired
    private PsyQuestionTopicService psyQuestionTopicService;

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

    @GetMapping("/excelOutBatchDetail")
    @RequiresPermissions("manager:question:excel")
    @ResponseBody
    public void excelOutBatchDetail(String ids) {
        setExcelOutCommonResponse();
        ManagerQueryListReq queryListReq = new ManagerQueryListReq();
        queryListReq.setRecordIds(ids);
        List<QueryQuestion> queryQuestions = psyQuestionRecordService.selectExcelOutList(queryListReq);
        //转换成x轴问卷，y轴用户
        Map<Long, List<QueryQuestion>> sheetMap = new HashMap<>();
        for (int i = 0; i < queryQuestions.size(); i++) {
            List<QueryQuestion> questionDatas = sheetMap.get(queryQuestions.get(i).getQuestionId());
            if (questionDatas == null) {
                questionDatas = new ArrayList<>();
            }
            questionDatas.add(queryQuestions.get(i));
            sheetMap.put(queryQuestions.get(i).getQuestionId(), questionDatas);
        }
        try {
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
            int sheetNo = 0;
            for (Long questionId : sheetMap.keySet()) {
                List<QueryQuestion> questionDatas = sheetMap.get(questionId);
                if (CollectionUtils.isEmpty(questionDatas)) {
                    continue;
                }
                List<List<String>> execlData = new ArrayList<>();
                for (int i = 0; i < queryQuestions.size(); i++) {
                    QueryQuestion queryQuestion = queryQuestions.get(i);
                    //查出问卷答案作为sheet的data
                    List<PsyQuestionTopicRecord> topicRecordList = psyQuestionTopicRecordService.list(new QueryWrapper<PsyQuestionTopicRecord>()
                            .orderByAsc(ColumnConsts.ORDER_NUM)
                            .eq(ColumnConsts.QUESTION_RECORD_ID, queryQuestion.getId()));
                    List<String> data = new ArrayList<>();
                    data.add(String.valueOf(i + 1));
                    data.add(queryQuestion.getName());
                    data.add(queryQuestion.getNickName());
                    data.add(queryQuestion.getSubmitDateFull());
                    data.add(queryQuestion.getSubmitScore());
                    data.add(queryQuestion.getSumScore());
                    for (PsyQuestionTopicRecord record : topicRecordList) {
                        data.add(record.getQuestionOptionText() + "(" + record.getQuestionOptionScore() + "分)");
                    }
                    execlData.add(data);
                }
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo, questionDatas.get(0).getTitle())
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
                WriteTable writeTable = EasyExcel.writerTable(0).needHead(Boolean.TRUE).build();
                writeTable.setHead(getHeadList(questionId));
                excelWriter.write(execlData, writeSheet, writeTable);
                sheetNo++;
            }
            excelWriter.finish();
        } catch (Exception e) {
            log.error("批量问卷明细导出失败！", e);
        }
    }

    private List<List<String>> getHeadList(Long questionId) {
        //查出问卷的题目作为sheet的head
        List<PsyQuestionTopic> questionList = psyQuestionTopicService.list(new QueryWrapper<PsyQuestionTopic>()
                .orderByAsc(ColumnConsts.ORDER_NUM)
                .eq(ColumnConsts.QUESTION_ID, questionId));
        List<List<String>> headList = new ArrayList<>();
        addTitle(headList, "序号");
        addTitle(headList, "用户名称");
        addTitle(headList, "微信名称");
        addTitle(headList, "提交时间");
        addTitle(headList, "问卷得分");
        addTitle(headList, "问卷总分");
        for (PsyQuestionTopic topic : questionList) {
            addTitle(headList, topic.getTopic());
        }
        return headList;
    }

    private void addTitle(List<List<String>> headList, String title) {
        List<String> headTitle = new ArrayList<>();
        headTitle.add(title);
        headList.add(headTitle);
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
