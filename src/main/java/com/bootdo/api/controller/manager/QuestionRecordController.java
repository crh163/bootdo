package com.bootdo.api.controller.manager;

import com.bootdo.api.entity.res.common.Response;
import com.bootdo.common.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/manager/question")
public class QuestionRecordController {

    @GetMapping("/list")
    @RequiresPermissions("manager:question:list")
    public String list() {
        return "manager/question/list";
    }

    @PostMapping("/list")
    @ResponseBody
    @RequiresPermissions("manager:question:list")
    public Response list(String x) {
        return ResponseUtil.getSuccess();
    }

}
