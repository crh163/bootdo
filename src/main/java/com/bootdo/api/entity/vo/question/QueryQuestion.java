package com.bootdo.api.entity.vo.question;

import lombok.Data;

@Data
public class QueryQuestion {

    private Long id;

    private Long questionId;

    private String title;

    private String indexTitle;

    private String nickName;

    private String name;

    private String submitScore;

    private String sumScore;

    private String submitDateFull;

}
