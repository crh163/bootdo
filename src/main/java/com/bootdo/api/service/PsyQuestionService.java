package com.bootdo.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.PsyQuestion;
import com.bootdo.api.entity.db.PsyQuestionTopic;
import com.bootdo.api.entity.db.PsyQuestionTopicOptions;
import com.bootdo.api.entity.res.GetQuestionRes;
import com.bootdo.api.entity.res.question.GetQuestion;
import com.bootdo.api.entity.res.question.GetQuestionTopic;
import com.bootdo.api.entity.res.question.GetQuestionTopicOptions;
import com.bootdo.api.mapper.PsyQuestionMapper;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.constant.CommonConsts;
import com.bootdo.common.constant.ResponseCodeEnum;
import com.bootdo.common.exception.BasicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-21 18:06
 */
@Service
@Slf4j
public class PsyQuestionService extends BaseService<PsyQuestionMapper, PsyQuestion> {

    @Autowired
    private PsyQuestionTopicService topicService;

    @Autowired
    private PsyQuestionTopicOptionsService topicOptionsService;

    @Autowired
    private PsyQuestionMapper psyQuestionMapper;

    /**
     * 获取问卷列表
     *
     * @return
     */
    public List<GetQuestionRes> selectQuestionList() {
        return psyQuestionMapper.selectQuestionList();
    }

    /**
     * 根据问卷id获取问卷详细信息
     *
     * @param id
     * @return
     */
    public GetQuestion getQuestionTopicById(Long id) throws BasicException {
        PsyQuestion psyQuestion = getById(id);
        if (psyQuestion == null) {
            throw new BasicException(ResponseCodeEnum.NOT_EXIST_QUESTION);
        }
        List<PsyQuestionTopic> questionTopics = topicService.list(new QueryWrapper<PsyQuestionTopic>()
                .eq(ColumnConsts.QUESTION_ID, id));
        List<GetQuestionTopic> topics = new ArrayList<>();
        for (PsyQuestionTopic questionTopic : questionTopics) {
            GetQuestionTopic topic = new GetQuestionTopic();
            topic.setTopicId(questionTopic.getId());
            topic.setTopicName(questionTopic.getTopic());
            topic.setTopicType(questionTopic.getTopicType());
            if (CommonConsts.ONE_INT.equals(questionTopic.getTopicType())) {
                List<GetQuestionTopicOptions> options = new ArrayList<>();
                List<PsyQuestionTopicOptions> topicOptions = topicOptionsService.list(
                        new QueryWrapper<PsyQuestionTopicOptions>()
                        .eq(ColumnConsts.QUESTION_TOPIC_ID, questionTopic.getId()));
                for (PsyQuestionTopicOptions topicOption : topicOptions) {
                    GetQuestionTopicOptions option = new GetQuestionTopicOptions();
                    option.setOptionId(topicOption.getId());
                    option.setOptionName(topicOption.getOptionName());
                    option.setOptionScore(topicOption.getOptionScore());
                    options.add(option);
                }
                topic.setOptions(options);
            }
            topics.add(topic);
        }
        GetQuestion question = new GetQuestion();
        question.setQuestionId(id);
        question.setQuestionGuide(psyQuestion.getGuide());
        question.setQuestionRemark(psyQuestion.getRemark());
        question.setQuestionSumScore(psyQuestion.getSumScore());
        question.setTopics(topics);
        return question;
    }

}
