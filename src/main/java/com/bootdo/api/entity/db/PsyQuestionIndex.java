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
public class PsyQuestionIndex extends BaseModel implements Serializable {

    @ApiModelProperty("问卷标题")
    private String indexTitle;

    @ApiModelProperty("图片url")
    private String photoUrl;

    @ApiModelProperty("排序")
    private Integer orderNum;

}
