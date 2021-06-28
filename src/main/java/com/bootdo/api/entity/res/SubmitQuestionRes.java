package com.bootdo.api.entity.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubmitQuestionRes {

    @ApiModelProperty("试卷得分")
    private Integer selectedScore;

    @ApiModelProperty("试卷总分")
    private String sumScore;

    @ApiModelProperty("考试结果")
    private String result;

    @ApiModelProperty("建议(部分问卷返回该字段为空)")
    private String advice;

}
