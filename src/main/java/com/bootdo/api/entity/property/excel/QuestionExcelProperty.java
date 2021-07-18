package com.bootdo.api.entity.property.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * 批量导出问卷
 */
@Data
public class QuestionExcelProperty extends BaseRowModel {

    @ExcelProperty(value = "序号", index = 0)
    private String order;

    @ExcelProperty(value = "问卷名称", index = 1)
    private String title;

    @ExcelProperty(value = "所属栏目", index = 2)
    private String indexTitle;

    @ExcelProperty(value = "用户名称", index = 3)
    private String name;

    @ExcelProperty(value = "微信名称", index = 4)
    private String nickName;

    @ExcelProperty(value = "提交时间", index = 5)
    private String submitDateFull;

    @ExcelProperty(value = "问卷得分", index = 6)
    private String submitScore;

    @ExcelProperty(value = "问卷总分", index = 7)
    private String sumScore;


}
