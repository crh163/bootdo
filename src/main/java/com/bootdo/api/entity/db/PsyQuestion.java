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
public class PsyQuestion extends BaseModel implements Serializable {

    @ApiModelProperty("问卷标题")
    private String title;

    @ApiModelProperty("问卷指导语")
    private String guide;

    @ApiModelProperty("问卷评分方式（无区间判断）")
    private String remark;

}
