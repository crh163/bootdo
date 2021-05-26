package com.bootdo.common.domain.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rory.chen
 * @date 2021-01-07 14:39
 */
@Data
@ApiModel("分页信息")
public class PageDto {

    @ApiModelProperty("当前页")
    Long currentPage;

    @ApiModelProperty("每页个数")
    Long pageSize;

    @ApiModelProperty("总页数")
    Long totalPages;

    @ApiModelProperty("总行数")
    Long totalRows;
}
