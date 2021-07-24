package com.bootdo.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.PsyClockRecord;
import com.bootdo.api.entity.req.clock.ManagerQueryListReq;
import com.bootdo.api.entity.vo.clock.QueryQuestion;
import com.bootdo.api.mapper.PsyClockRecordMapper;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.domain.page.ManPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-21 18:06
 */
@Service
@Slf4j
public class PsyClockRecordService extends BaseService<PsyClockRecordMapper, PsyClockRecord> {

    @Autowired
    private PsyClockRecordMapper psyClockRecordMapper;

    /**
     * 获取用户最近一次提交记录时间
     *
     * @param openId
     * @return
     */
    public String selectBeforeClockTime(String openId) {
        QueryWrapper<PsyClockRecord> wrapper = new QueryWrapper<PsyClockRecord>()
                .eq(ColumnConsts.OPENID, openId)
                .orderByDesc(ColumnConsts.SUBMIT_DATE_FULL)
                .last("LIMIT 0,1");
        PsyClockRecord record = getOne(wrapper);
        return record == null ? null : record.getSubmitDateFull();
    }

    public ManPage selectListBySearchKey(ManagerQueryListReq queryListReq) {
        if (queryListReq.getPage() != null && queryListReq.getPage() != 0) {
            queryListReq.setPage(queryListReq.getPage() - 1);
        }
        Integer count = psyClockRecordMapper.selectListBySearchKeyCount(queryListReq);
        List<QueryQuestion> queryQuestions = psyClockRecordMapper.selectListBySearchKey(queryListReq);
        ManPage page = new ManPage();
        page.setRows(queryQuestions);
        page.setTotal(count);
        return page;
    }

    public List<QueryQuestion> selectExcelOutList(ManagerQueryListReq queryListReq){
        return psyClockRecordMapper.selectListBySearchKey(queryListReq);
    }

    public List<QueryQuestion> getLastRecordList(String today) {
        return psyClockRecordMapper.getLastRecordList(today);
    }

}
