package com.bootdo.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootdo.api.entity.db.PsyQuestionTopicRecord;
import com.bootdo.api.entity.req.question.ManagerQueryListReq;
import com.bootdo.api.entity.vo.question.QueryQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-12 18:32
 */
public interface PsyQuestionTopicRecordMapper extends BaseMapper<PsyQuestionTopicRecord> {

    /**
     * 批量插入选择题结果
     *
     * @param questionRecordId
     * @param userId
     * @param questionId
     * @param selectedOptionId
     */
    void insertBatchRecord(@Param("questionRecordId") Long questionRecordId,
                           @Param("userId") Long userId,
                           @Param("questionId") Long questionId,
                           @Param("selectedOptionId") List<Long> selectedOptionId);

    /**
     * 插入填空题结果
     *
     * @param questionRecordId
     * @param userId
     * @param topicId
     * @param gapFillText
     */
    void insertGapFill(@Param("questionRecordId") Long questionRecordId,
                       @Param("userId") Long userId,
                       @Param("questionId") Long questionId,
                       @Param("topicId") Long topicId,
                       @Param("gapFillText") String gapFillText);

    Integer selectListBySearchKeyCount(ManagerQueryListReq queryListReq);

    List<QueryQuestion> selectListBySearchKey(ManagerQueryListReq queryListReq);

}
