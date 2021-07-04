package com.bootdo.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.PsyQuestionRecord;
import com.bootdo.api.entity.db.SysWxUser;
import com.bootdo.api.entity.req.question.SubmitQuestionReq;
import com.bootdo.api.entity.req.question.info.TopicGapFill;
import com.bootdo.api.entity.req.question.info.TopicSelect;
import com.bootdo.api.entity.res.question.SubmitQuestionRes;
import com.bootdo.api.mapper.PsyQuestionRecordMapper;
import com.bootdo.api.mapper.PsyQuestionTopicRecordMapper;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.constant.CommonConsts;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void insertRecord(SysWxUser userInfo,
                             SubmitQuestionReq questionReq,
                             SubmitQuestionRes submitQuestionRes) {
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

    /**
     * 获取用户最近一次提交记录时间
     *
     * @param openId
     * @return
     */
    public String selectQuestionSubmitNewTime(String openId) {
        QueryWrapper<PsyQuestionRecord> wrapper = new QueryWrapper<PsyQuestionRecord>()
                .eq(ColumnConsts.OPENID, openId)
                .orderByDesc(ColumnConsts.SUBMIT_DATE_FULL)
                .last("LIMIT 0,1");
        PsyQuestionRecord record = getOne(wrapper);
        return record == null ? null : record.getSubmitDateFull();
    }

}
