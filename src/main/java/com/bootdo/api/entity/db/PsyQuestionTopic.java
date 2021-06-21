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
public class PsyQuestionTopic extends BaseModel implements Serializable {

    @ApiModelProperty("问卷列表id")
    private Integer questionId;

    @ApiModelProperty("题目")
    private String topic;

    @ApiModelProperty("题目类型 1 选择题 2 填空题")
    private Integer topicType;

    @ApiModelProperty("填空题答案 json数据 [xx,xx]")
    private String gapFillJson;

}
