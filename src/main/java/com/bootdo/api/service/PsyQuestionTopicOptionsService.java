package com.bootdo.api.service;

import com.bootdo.api.entity.db.PsyQuestionTopicOptions;
import com.bootdo.api.mapper.PsyQuestionTopicOptionsMapper;
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
public class PsyQuestionTopicOptionsService extends BaseService<PsyQuestionTopicOptionsMapper, PsyQuestionTopicOptions> {

    @Autowired
    private PsyQuestionTopicOptionsMapper psyQuestionTopicOptionsMapper;

    public Integer selectSumScoreByIds(Long questionId, List<Long> selectedOptionId) {
        return psyQuestionTopicOptionsMapper.selectSumScoreByIds(questionId, selectedOptionId);
    }
}
