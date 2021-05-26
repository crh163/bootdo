package com.bootdo.common.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author rory.chen
 * @date 2021-01-21 17:50
 */
@Data
@Accessors(chain = true)
public class BaseModel {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 创建人id
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Long createId;

    /**
     * 创建时间
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Date createDate;

    /**
     * 最近修改人id
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Long updateId;

    /**
     * 最近修改时间
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Date updateDate;

}
