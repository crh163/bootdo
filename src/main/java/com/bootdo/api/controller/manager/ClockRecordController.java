package com.bootdo.api.controller.manager;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.bootdo.api.entity.property.excel.ClockExcelProperty;
import com.bootdo.api.entity.req.clock.ManagerQueryListReq;
import com.bootdo.api.entity.vo.clock.QueryQuestion;
import com.bootdo.api.service.PsyClockRecordService;
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
@RequestMapping("/manager/clock")
public class ClockRecordController {

    @Autowired
    private PsyClockRecordService psyClockRecordService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @GetMapping("/list")
    @RequiresPermissions("manager:clock:list")
    public String list() {
        return "manager/clock/list";
    }

    @PostMapping("/list")
    @RequiresPermissions("manager:clock:list")
    @ResponseBody
    public ManPage list(@RequestBody ManagerQueryListReq queryListReq) {
        return psyClockRecordService.selectListBySearchKey(queryListReq);
    }

    @GetMapping("/excelOutBatch")
    @RequiresPermissions("manager:clock:excel")
    @ResponseBody
    public void excelOutBatch(String ids) {
        setExcelOutCommonResponse();
        ManagerQueryListReq queryListReq = new ManagerQueryListReq();
        queryListReq.setRecordIds(ids);
        List<QueryQuestion> queryQuestions = psyClockRecordService.selectExcelOutList(queryListReq);
        List<ClockExcelProperty> properties = new ArrayList<>();
        for (int i = 0; i < queryQuestions.size(); i++) {
            ClockExcelProperty excelProperty = DataUtils.coverData(queryQuestions.get(i), ClockExcelProperty.class);
            excelProperty.setOrder(String.valueOf(i + 1));
            properties.add(excelProperty);
        }
        try {
            EasyExcel.write(response.getOutputStream(), ClockExcelProperty.class)
                    .sheet("sheet1")
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .doWrite(properties);
        } catch (Exception e) {
            log.error("问卷导出失败！", e);
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
