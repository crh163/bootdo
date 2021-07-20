package com.bootdo.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootdo.api.entity.db.PsyAudioRecord;
import com.bootdo.api.entity.req.audio.ManagerQueryListReq;
import com.bootdo.api.entity.vo.audio.QueryQuestion;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-12 18:32
 */
public interface PsyAudioRecordMapper extends BaseMapper<PsyAudioRecord> {

    Integer selectListBySearchKeyCount(ManagerQueryListReq queryListReq);

    List<QueryQuestion> selectListBySearchKey(ManagerQueryListReq queryListReq);

}
