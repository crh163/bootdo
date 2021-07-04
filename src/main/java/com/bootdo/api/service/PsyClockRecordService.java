package com.bootdo.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.PsyClockRecord;
import com.bootdo.api.mapper.PsyClockRecordMapper;
import com.bootdo.common.constant.ColumnConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author rory.chen
 * @date 2021-01-21 18:06
 */
@Service
@Slf4j
public class PsyClockRecordService extends BaseService<PsyClockRecordMapper, PsyClockRecord> {

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

}
