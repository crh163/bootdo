package com.bootdo.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootdo.api.entity.db.PsyQuestion;
import com.bootdo.api.entity.res.question.GetQuestionRes;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-12 18:32
 */
public interface PsyQuestionMapper extends BaseMapper<PsyQuestion> {

    List<GetQuestionRes> selectQuestionList();

}
