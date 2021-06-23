package com.bootdo.api.controller;

import com.bootdo.api.entity.req.common.CommonIdReq;
import com.bootdo.api.entity.req.question.SubmitQuestionReq;
import com.bootdo.api.entity.res.Response;
import com.bootdo.api.service.PsyQuestionScoreRangeService;
import com.bootdo.api.service.PsyQuestionService;
import com.bootdo.common.exception.BasicException;
import com.bootdo.common.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "问卷接口")
@RequestMapping("/api/question")
public class PsyQuestionController {

    @Autowired
    private PsyQuestionService psyQuestionService;

    @Autowired
    private PsyQuestionScoreRangeService psyQuestionScoreRangeService;

    @ApiOperation("获取问卷列表")
    @PostMapping("/getQuestionList")
    public Response getQuestionList() {
        return ResponseUtil.getSuccess(psyQuestionService.selectQuestionList());
    }

    @ApiOperation("根据问卷id获取问卷详细信息")
    @PostMapping("/getQuestionTopicById")
    public Response getQuestionTopicById(@RequestBody CommonIdReq commonIdReq) throws BasicException {
        return ResponseUtil.getSuccess(psyQuestionService.getQuestionTopicById(commonIdReq.getId()));
    }

    @ApiOperation("提交问卷答案")
    @PostMapping("/submitQuestion")
    public Response submitQuestion(@RequestBody SubmitQuestionReq questionReq) throws BasicException {
        //判断提交的数据是否有问题，同一个用户同一个问卷提交时间间隔不得小于2秒
        //1.获取问卷的评分标准
        //2.计算用户总得分，封装页面显示结果
        //3.提交记录写入记录表中（重要）
        return ResponseUtil.getSuccess();
    }

}
