package com.bootdo.api.entity.property.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * 批量导出问卷
 */
@Data
public class AudioExcelProperty extends BaseRowModel {

    @ExcelProperty(value = "序号", index = 0)
    private String order;

    @ExcelProperty(value = "音频名称", index = 1)
    private String audioName;

    @ExcelProperty(value = "用户名称", index = 2)
    private String name;

    @ExcelProperty(value = "微信名称", index = 3)
    private String nickName;

    @ExcelProperty(value = "手机号码", index = 4)
    private String phone;

    @ExcelProperty(value = "播放时间", index = 5)
    private String submitDateFull;

}
