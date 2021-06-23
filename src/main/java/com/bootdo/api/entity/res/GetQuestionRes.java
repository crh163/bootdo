package com.bootdo.api.entity.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rory.chen
 * @date 2021/6/22
 */
@Data
public class GetQuestionRes {

    @ApiModelProperty("问卷id")
    private Long id;

    @ApiModelProperty("问卷标题")
    private String title;

    @ApiModelProperty("问卷的问题总数（共xx问）")
    private Integer topicNumber;

}
