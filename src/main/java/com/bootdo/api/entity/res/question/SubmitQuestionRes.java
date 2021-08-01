package com.bootdo.api.entity.res.question;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubmitQuestionRes {

    @ApiModelProperty("问卷标题")
    private String title;

    @ApiModelProperty("用户头像url")
    private String userAvatarUrl;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("提交时间")
    private String submitDate;

    @ApiModelProperty("试卷得分")
    private Integer selectedScore;

    @ApiModelProperty("试卷总分")
    private String sumScore;

    @ApiModelProperty("考试结果")
    private String result;

    @ApiModelProperty("建议(部分问卷返回该字段为空)")
    private String advice;

    @ApiModelProperty("下一个问卷的id")
    private Long nextQuestionId;

}
