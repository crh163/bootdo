package com.bootdo.api.entity.res.question;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rory.chen
 * @date 2021/6/22
 */
@Data
public class GetQuestionIndexRes {

    @ApiModelProperty("栏目id")
    private Long id;

    @ApiModelProperty("栏目标题")
    private String title;

    @ApiModelProperty("图片url")
    private String photoUrl;

    @ApiModelProperty("栏目的问卷总数（共xx问卷）")
    private Integer topicNumber;

}
