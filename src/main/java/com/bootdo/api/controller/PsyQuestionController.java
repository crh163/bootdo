package com.bootdo.api.controller;

import com.bootdo.api.entity.req.CommonOpenIdReq;
import com.bootdo.api.entity.res.Response;
import com.bootdo.api.service.PsyQuestionService;
import com.bootdo.common.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "问卷接口")
@RequestMapping("/api/question")
public class PsyQuestionController {

    @Autowired
    private PsyQuestionService psyQuestionService;

    @ApiOperation("查询个人信息完善状态")
    @PostMapping("/getUserInfoStatus")
    public Response getUserInfoStatus(CommonOpenIdReq commonOpenIdReq){
        return ResponseUtil.getSuccess();
    }

}
