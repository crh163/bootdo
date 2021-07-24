package com.bootdo.api.service;

import com.bootdo.api.entity.db.PsyAudioRecord;
import com.bootdo.api.entity.req.audio.ManagerQueryListReq;
import com.bootdo.api.entity.vo.audio.QueryQuestion;
import com.bootdo.api.mapper.PsyAudioRecordMapper;
import com.bootdo.common.domain.page.ManPage;
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
public class PsyAudioRecordService extends BaseService<PsyAudioRecordMapper, PsyAudioRecord> {

    @Autowired
    private PsyAudioRecordMapper psyAudioRecordMapper;

    public ManPage selectListBySearchKey(ManagerQueryListReq queryListReq) {
        if (queryListReq.getPage() != null && queryListReq.getPage() != 0) {
            queryListReq.setPage(queryListReq.getPage() - 1);
        }
        Integer count = psyAudioRecordMapper.selectListBySearchKeyCount(queryListReq);
        List<QueryQuestion> queryQuestions = psyAudioRecordMapper.selectListBySearchKey(queryListReq);
        ManPage page = new ManPage();
        page.setRows(queryQuestions);
        page.setTotal(count);
        return page;
    }

    public List<QueryQuestion> selectExcelOutList(ManagerQueryListReq queryListReq){
        return psyAudioRecordMapper.selectListBySearchKey(queryListReq);
    }

    public List<QueryQuestion> getLastRecordList(String today) {
        return psyAudioRecordMapper.getLastRecordList(today);
    }


}
