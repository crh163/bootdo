package com.bootdo.api.controller.manager;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.bootdo.api.entity.property.excel.AudioExcelProperty;
import com.bootdo.api.entity.req.audio.ManagerQueryListReq;
import com.bootdo.api.entity.vo.audio.QueryQuestion;
import com.bootdo.api.service.PsyAudioRecordService;
import com.bootdo.common.domain.page.ManPage;
import com.bootdo.common.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/manager/audio")
public class AudioRecordController {

    @Autowired
    private PsyAudioRecordService psyAudioRecordService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @GetMapping("/list")
    @RequiresPermissions("manager:audio:list")
    public String list() {
        return "manager/audio/list";
    }

    @PostMapping("/list")
    @RequiresPermissions("manager:audio:list")
    @ResponseBody
    public ManPage list(@RequestBody ManagerQueryListReq queryListReq) {
        return psyAudioRecordService.selectListBySearchKey(queryListReq);
    }

    @GetMapping("/excelOutBatch")
    @RequiresPermissions("manager:audio:excel")
    @ResponseBody
    public void excelOutBatch(String ids) {
        setExcelOutCommonResponse();
        ManagerQueryListReq queryListReq = new ManagerQueryListReq();
        queryListReq.setRecordIds(ids);
        List<QueryQuestion> queryQuestions = psyAudioRecordService.selectExcelOutList(queryListReq);
        List<AudioExcelProperty> properties = new ArrayList<>();
        for (int i = 0; i < queryQuestions.size(); i++) {
            AudioExcelProperty excelProperty = DataUtils.coverData(queryQuestions.get(i), AudioExcelProperty.class);
            excelProperty.setOrder(String.valueOf(i + 1));
            properties.add(excelProperty);
        }
        try {
            EasyExcel.write(response.getOutputStream(), AudioExcelProperty.class)
                    .sheet("sheet1")
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .doWrite(properties);
        } catch (Exception e) {
            log.error("音频播放记录导出失败！", e);
        }
    }

    private void setExcelOutCommonResponse() {
        String fileName = null;
        try {
            fileName = URLEncoder.encode("音频播放记录", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("URLEncoder.encode: ", e);
        }
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

    }

}
