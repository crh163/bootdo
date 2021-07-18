package com.bootdo.api.service;

import com.bootdo.api.entity.db.PsyQuestionIndex;
import com.bootdo.api.entity.res.question.GetQuestionIndexRes;
import com.bootdo.api.mapper.PsyQuestionIndexMapper;
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
public class PsyQuestionIndexService extends BaseService<PsyQuestionIndexMapper, PsyQuestionIndex> {

    @Autowired
    private PsyQuestionIndexMapper psyQuestionIndexMapper;

    /**
     * 获取问卷列表
     *
     * @return
     */
    public List<GetQuestionIndexRes> selectQuestionIndexList() {
        return psyQuestionIndexMapper.selectQuestionIndexList();
    }

}
