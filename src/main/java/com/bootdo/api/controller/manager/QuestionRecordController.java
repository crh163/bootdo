package com.bootdo.api.controller.manager;

import com.bootdo.api.entity.req.question.ManagerQueryListReq;
import com.bootdo.api.service.PsyQuestionRecordService;
import com.bootdo.api.service.PsyQuestionService;
import com.bootdo.common.domain.page.ManPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/manager/question")
public class QuestionRecordController {

    @Autowired
    private PsyQuestionRecordService psyQuestionRecordService;

    @Autowired
    private PsyQuestionService psyQuestionService;

    @Autowired
    private HttpServletRequest request;

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

}
