package com.bootdo.api.entity.res.index;

import lombok.Data;

import java.util.List;

@Data
public class ManagerIndexInfoRes {

    private String questionNumber;

    private String clockNumber;

    private String audioNumber;

    private List<com.bootdo.api.entity.vo.question.QueryQuestion> questionRecordList;

    private List<com.bootdo.api.entity.vo.clock.QueryQuestion> clockRecordList;

    private List<com.bootdo.api.entity.vo.audio.QueryQuestion> audioRecordList;

}
