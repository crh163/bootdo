package com.bootdo.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.PsyQuestionRecord;
import com.bootdo.api.entity.db.SysWxUser;
import com.bootdo.api.entity.req.question.ManagerQueryListReq;
import com.bootdo.api.entity.req.question.SubmitQuestionReq;
import com.bootdo.api.entity.req.question.info.TopicGapFill;
import com.bootdo.api.entity.req.question.info.TopicSelect;
import com.bootdo.api.entity.res.question.SubmitQuestionRes;
import com.bootdo.api.entity.vo.question.QueryQuestion;
import com.bootdo.api.mapper.PsyQuestionRecordMapper;
import com.bootdo.api.mapper.PsyQuestionTopicRecordMapper;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.constant.CommonConsts;
import com.bootdo.common.domain.page.ManPage;
import com.bootdo.common.domain.sys.DictDO;
import com.bootdo.system.service.DictService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    private DictService dictService;

    @Autowired
    private PsyQuestionRecordMapper psyQuestionRecordMapper;

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
        //????????????????????????
        List<Long> selectedOptionId = questionReq.getTopicSelects().stream()
                .map(TopicSelect::getOptionId).collect(Collectors.toList());
        //???????????????
        psyQuestionTopicRecordMapper.insertBatchRecord(questionRecord.getId(),
                userInfo.getId(), questionReq.getQuestionId(), selectedOptionId);
        //???????????????
        if (questionReq.getTopicGapFills() != null) {
            for (TopicGapFill gapFill : questionReq.getTopicGapFills()) {
                psyQuestionTopicRecordMapper.insertGapFill(questionRecord.getId(),
                        userInfo.getId(), questionReq.getQuestionId(),
                        gapFill.getTopicId(), gapFill.getGapFillText());
            }
        }
    }

    /**
     * ??????????????????????????????????????????
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

    /**
     * manager - ????????????
     *
     * @param queryListReq
     * @return
     */
    public ManPage selectListBySearchKey(ManagerQueryListReq queryListReq) {
        if (queryListReq.getPage() != null && queryListReq.getPage() != 0) {
            queryListReq.setPage(queryListReq.getPage() - 1);
        }
        Integer count = psyQuestionTopicRecordMapper.selectListBySearchKeyCount(queryListReq);
        List<QueryQuestion> queryQuestions = psyQuestionTopicRecordMapper.selectListBySearchKey(queryListReq);
        ManPage page = new ManPage();
        page.setRows(queryQuestions);
        page.setTotal(count);
        return page;
    }

    public List<QueryQuestion> selectExcelOutList(ManagerQueryListReq queryListReq){
        return psyQuestionTopicRecordMapper.selectListBySearchKey(queryListReq);
    }

    public List<String> getAllTodayData(String today) {
        return psyQuestionRecordMapper.getAllTodayData(today);
    }

    public List<QueryQuestion> getLastRecordList(String today) {
        return psyQuestionRecordMapper.getLastRecordList(today);
    }

    public List<Long> getSubmitQuestionIdByOpenId(String openId) {
        String startDate = "";
        String endDate = "";
        //??????????????????????????????
        List<DictDO> dictList = dictService.listByType(CommonConsts.DICT_APP_QUESTION_TIME);
        if (!CollectionUtils.isEmpty(dictList)) {
            startDate = dictList.get(0).getValue();
        }
        //??????????????????????????????
        dictList = dictService.listByType(CommonConsts.DICT_APP_QUESTION_LAST_TIME);
        if (!CollectionUtils.isEmpty(dictList)) {
            endDate = dictList.get(0).getValue();
        }
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return null;
        }
        return psyQuestionRecordMapper.getSubmitQuestionIdByOpenId(openId, startDate, endDate);
    }
}
