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
public class PsyClockRecord extends BaseModel implements Serializable {

    @ApiModelProperty("wx openid")
    private String openId;

    @ApiModelProperty("提交时间(年月日)")
    private String submitDate;

    @ApiModelProperty("提交时间(年月日-时分秒)")
    private String submitDateFull;

}
