package com.bootdo.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootdo.api.entity.db.PsyQuestionTopic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-12 18:32
 */
public interface PsyQuestionTopicMapper extends BaseMapper<PsyQuestionTopic> {

    List<Long> selectTopicIdsByQuestionId(@Param("questionId") Long questionId);

}
