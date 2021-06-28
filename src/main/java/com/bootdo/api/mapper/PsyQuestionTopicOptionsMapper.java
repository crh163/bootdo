package com.bootdo.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootdo.api.entity.db.PsyQuestionTopicOptions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-12 18:32
 */
public interface PsyQuestionTopicOptionsMapper extends BaseMapper<PsyQuestionTopicOptions> {

    Integer selectSumScoreByIds(@Param("questionId") Long questionId,
                                @Param("optionIds") List<Long> optionIds);

}
