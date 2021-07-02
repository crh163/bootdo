package com.bootdo.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.PsyQuestion;
import com.bootdo.api.entity.db.PsyQuestionScoreRange;
import com.bootdo.api.entity.req.common.CommonIdReq;
import com.bootdo.api.entity.req.question.SubmitQuestionReq;
import com.bootdo.api.entity.req.question.TopicSelect;
import com.bootdo.api.entity.res.Response;
import com.bootdo.api.entity.res.SubmitQuestionRes;
import com.bootdo.api.service.*;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.constant.ResponseCodeEnum;
import com.bootdo.common.exception.BasicException;
import com.bootdo.common.utils.ResponseUtil;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(tags = "问卷接口")
@RequestMapping("/api/question")
public class PsyQuestionController {

    @Autowired
    private PsyQuestionService psyQuestionService;

    @Autowired
    private PsyQuestionTopicService psyQuestionTopicService;

    @Autowired
    private PsyQuestionTopicOptionsService psyQuestionTopicOptionsService;

    @Autowired
    private PsyQuestionScoreRangeService psyQuestionScoreRangeService;

    @Autowired
    private PsyQuestionRecordService psyQuestionRecordService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private Gson gson;

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
        //1.判断提交的数据是否有问题
        PsyQuestion question = psyQuestionService.getById(questionReq.getQuestionId());
        checkQuestionInfo(question, questionReq);
        //2.获取问卷的评分标准
        List<PsyQuestionScoreRange> scoreRanges = psyQuestionScoreRangeService.list(
                new QueryWrapper<PsyQuestionScoreRange>().eq(ColumnConsts.QUESTION_ID, questionReq.getQuestionId()));
        //3.计算用户总得分（选择题得分），封装页面显示结果
        SubmitQuestionRes submitQuestionRes = buildSubmitQuestionRes(question, questionReq, scoreRanges);
        //4.提交记录写入记录表中（重要）
        psyQuestionRecordService.insertRecord(request, questionReq, submitQuestionRes);
        return ResponseUtil.getSuccess(submitQuestionRes);
    }

    /**
     * 计算用户总得分，封装页面显示结果
     *
     * @param question
     * @param questionReq
     * @param scoreRanges
     * @return
     */
    private SubmitQuestionRes buildSubmitQuestionRes(PsyQuestion question,
                                                     SubmitQuestionReq questionReq,
                                                     List<PsyQuestionScoreRange> scoreRanges){
        List<Long> selectedOptionId = questionReq.getTopicSelects().stream()
                .map(TopicSelect::getOptionId).collect(Collectors.toList());
        Integer selectedScore = psyQuestionTopicOptionsService.selectSumScoreByIds(questionReq.getQuestionId(), selectedOptionId);

        SubmitQuestionRes submitQuestionRes = new SubmitQuestionRes();
        submitQuestionRes.setSumScore(question.getSumScore());
        submitQuestionRes.setSelectedScore(selectedScore);
        if (CollectionUtils.isEmpty(scoreRanges)) {
            //无分数区间判定，这些都是高分或者低分就对应好现象
            submitQuestionRes.setResult(question.getRemark());
        } else {
            //获取得分所在区间的 result 和 advice
            for (PsyQuestionScoreRange scoreRange : scoreRanges) {
                if (scoreRange.getScoreLow() < selectedScore && selectedScore < scoreRange.getScoreHigh()) {
                    submitQuestionRes.setResult(scoreRange.getResult());
                    submitQuestionRes.setAdvice(scoreRange.getAdvice());
                }
            }
        }
        return submitQuestionRes;
    }

    /**
     * 校验提交参数是否有问题
     *
     * @param question
     * @param questionReq
     * @throws BasicException
     */
    private void checkQuestionInfo(PsyQuestion question, SubmitQuestionReq questionReq)
            throws BasicException {
        if (question == null) {
            throw new BasicException(ResponseCodeEnum.NOT_EXIST_QUESTION);
        }
        //选择题肯定不会为空，填空题绝大多数问卷都为空
        if (CollectionUtils.isEmpty(questionReq.getTopicSelects())) {
            throw new BasicException(ResponseCodeEnum.FAIL_SUBMIT_QUESTION);
        }
        //判断该问卷的选择题答案是否全部提交
        List<Long> topicIds = psyQuestionTopicService.selectTopicIdsByQuestionId(questionReq.getQuestionId());
        List<Long> selectsTopicList = questionReq.getTopicSelects().stream()
                .map(TopicSelect::getTopicId).sorted().collect(Collectors.toList());
        if (!gson.toJson(topicIds).equals(gson.toJson(selectsTopicList))) {
            throw new BasicException(ResponseCodeEnum.FAIL_SUBMIT_QUESTION);
        }
    }

}
