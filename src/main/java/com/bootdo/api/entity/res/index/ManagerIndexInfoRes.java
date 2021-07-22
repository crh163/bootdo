package com.bootdo.api.entity.res.index;

import com.bootdo.api.entity.db.PsyAudioRecord;
import com.bootdo.api.entity.db.PsyClockRecord;
import com.bootdo.api.entity.vo.question.QueryQuestion;
import lombok.Data;

import java.util.List;

@Data
public class ManagerIndexInfoRes {

    private String questionNumber;

    private String clockNumber;

    private String audioNumber;

    private List<QueryQuestion> questionRecordList;

    private List<PsyClockRecord> clockRecordList;

    private List<PsyAudioRecord> audioRecordList;

}
