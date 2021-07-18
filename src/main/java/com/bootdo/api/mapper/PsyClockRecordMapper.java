package com.bootdo.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootdo.api.entity.db.PsyClockRecord;
import com.bootdo.api.entity.req.clock.ManagerQueryListReq;
import com.bootdo.api.entity.vo.clock.QueryQuestion;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-12 18:32
 */
public interface PsyClockRecordMapper extends BaseMapper<PsyClockRecord> {

    Integer selectListBySearchKeyCount(ManagerQueryListReq queryListReq);

    List<QueryQuestion> selectListBySearchKey(ManagerQueryListReq queryListReq);

}
