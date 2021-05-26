package com.bootdo.common.domain.model;

import com.bootdo.common.constant.CommonConsts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页查询模板
 *
 * @author rory.chen
 * @date 2021-01-22 14:25
 */
@Data
public class QueryModel {

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("每页个数")
    private Integer pageSize;

    @ApiModelProperty("模糊查询字段")
    private String searchKey;

    @ApiModelProperty("模糊查询输入值")
    private String searchValue;

    @ApiModelProperty("排序字段")
    private String orderField;

    @ApiModelProperty("asc 或 desc (默认asc)")
    private String orderSeq = "asc";

    @ApiModelProperty("选中的记录ID列表")
    private List<Long> ids;

    @ApiModelProperty("选中的单个记录ID")
    private Long id;

    public boolean isAsc() {
        return CommonConsts.ASC.equals(orderSeq);
    }

}
