package com.bootdo.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootdo.api.entity.db.PsyQuestionTopicRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-12 18:32
 */
public interface PsyQuestionTopicRecordMapper extends BaseMapper<PsyQuestionTopicRecord> {

    void insertBatchRecord(@Param("questionRecordId") Long questionRecordId,
                           @Param("userId") Long userId,
                           @Param("questionId") Long questionId,
                           @Param("selectedOptionId") List<Long> selectedOptionId);

}
