package com.bootdo.api.service;

import com.bootdo.api.entity.db.PsyQuestionTopic;
import com.bootdo.api.mapper.PsyQuestionTopicMapper;
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
public class PsyQuestionTopicService extends BaseService<PsyQuestionTopicMapper, PsyQuestionTopic> {

    @Autowired
    private PsyQuestionTopicMapper psyQuestionTopicMapper;

    public List<Long> selectTopicIdsByQuestionId(Long questionId) {
        return psyQuestionTopicMapper.selectTopicIdsByQuestionId(questionId);
    }
}
