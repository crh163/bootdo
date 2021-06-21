package com.bootdo.api.entity.db;

import com.bootdo.common.domain.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PsyQuestionTopicOptions extends BaseModel implements Serializable {

    @ApiModelProperty("问卷列表id")
    private Integer questionId;

    @ApiModelProperty("问卷题目id")
    private Integer questionTopicId;

    @ApiModelProperty("选项内容")
    private String optionName;

    @ApiModelProperty("选项对应分数")
    private String optionScore;

}
