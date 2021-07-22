package com.bootdo.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootdo.api.entity.db.PsyQuestionRecord;
import com.bootdo.api.entity.vo.question.QueryQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-12 18:32
 */
public interface PsyQuestionRecordMapper extends BaseMapper<PsyQuestionRecord> {

    List<String> getAllTodayData(@Param("today") String today);

    List<QueryQuestion> getLastRecordList(@Param("today") String today);
}
