package com.bootdo.api.service;

import com.bootdo.api.entity.db.PsyQuestionRecord;
import com.bootdo.api.entity.db.SysWxUser;
import com.bootdo.api.entity.req.question.SubmitQuestionReq;
import com.bootdo.api.entity.req.question.TopicGapFill;
import com.bootdo.api.entity.req.question.TopicSelect;
import com.bootdo.api.entity.res.SubmitQuestionRes;
import com.bootdo.api.mapper.PsyQuestionRecordMapper;
import com.bootdo.api.mapper.PsyQuestionTopicRecordMapper;
import com.bootdo.common.constant.CommonConsts;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rory.chen
 * @date 2021-01-21 18:06
 */
@Service
@Slf4j
public class PsyQuestionRecordService extends BaseService<PsyQuestionRecordMapper, PsyQuestionRecord> {

    @Autowired
    private PsyQuestionTopicRecordMapper psyQuestionTopicRecordMapper;

    @Autowired
    private Gson gson;

    @Transactional(rollbackFor = Exception.class)
    public void insertRecord(HttpServletRequest request,
                             SubmitQuestionReq questionReq,
                             SubmitQuestionRes submitQuestionRes) {
        SysWxUser userInfo = (SysWxUser) request.getAttribute(CommonConsts.WX_API_USER_INFO);
        PsyQuestionRecord questionRecord = new PsyQuestionRecord();
        questionRecord.setUserId(userInfo.getId());
        questionRecord.setOpenId(userInfo.getOpenId());
        questionRecord.setQuestionId(questionReq.getQuestionId());
        questionRecord.setSubmitScore(submitQuestionRes.getSelectedScore() + "");
        questionRecord.setSumScore(submitQuestionRes.getSumScore());
        questionRecord.setSubmitDate(LocalDate.now().format(CommonConsts.DTF_DAY));
        questionRecord.setSubmitDateFull(LocalDateTime.now().format(CommonConsts.DTF_SECONDS));
        questionRecord.setRequestJson(gson.toJson(questionReq));
        questionRecord.setResponseJson(gson.toJson(submitQuestionRes));
        save(questionRecord);
        //新增详细选项数据
        List<Long> selectedOptionId = questionReq.getTopicSelects().stream()
                .map(TopicSelect::getOptionId).collect(Collectors.toList());
        //选择题插入
        psyQuestionTopicRecordMapper.insertBatchRecord(questionRecord.getId(),
                userInfo.getId(), questionReq.getQuestionId(), selectedOptionId);
        //填空题插入
        if (questionReq.getTopicGapFills() != null) {
            for (TopicGapFill gapFill : questionReq.getTopicGapFills()) {
                psyQuestionTopicRecordMapper.insertGapFill(questionRecord.getId(),
                        userInfo.getId(), questionReq.getQuestionId(),
                        gapFill.getTopicId(), gapFill.getGapFillText());
            }
        }
    }
}
