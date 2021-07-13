package com.bootdo.api.entity.property.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class QuestionExcelProperty {

    @ExcelProperty(value = "问卷名称", index = 0)
    private String title;

    @ExcelProperty(value = "用户名称", index = 1)
    private String name;

    @ExcelProperty(value = "微信名称", index = 2)
    private String nickName;

    @ExcelProperty(value = "提交时间", index = 3)
    private String submitDateFull;

    @ExcelProperty(value = "问卷得分", index = 4)
    private String submitScore;

    @ExcelProperty(value = "问卷总分", index = 5)
    private String sumScore;


}
