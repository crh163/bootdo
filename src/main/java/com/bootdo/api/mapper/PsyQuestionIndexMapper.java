package com.bootdo.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootdo.api.entity.db.PsyQuestionIndex;
import com.bootdo.api.entity.res.question.GetQuestionIndexRes;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-12 18:32
 */
public interface PsyQuestionIndexMapper extends BaseMapper<PsyQuestionIndex> {

    List<GetQuestionIndexRes> selectQuestionIndexList();

}
