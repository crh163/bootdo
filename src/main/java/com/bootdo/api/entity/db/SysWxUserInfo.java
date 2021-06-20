package com.bootdo.api.entity.db;

import com.bootdo.common.domain.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysWxUserInfo extends BaseModel implements Serializable {

    private static final long serialVersionUID = 6809392313564681945L;

    @ApiModelProperty("微信openid")
    @NotBlank(message = "openid不能为空")
    private String openId;

    @ApiModelProperty("个人信息-姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty("个人信息-手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty("个人信息-身份证号码")
    @NotBlank(message = "身份证号码不能为空")
    private String idCard;

    @ApiModelProperty("人员类型 1 学生 2 政府机关 3 教师 4 运动员")
    private Integer type;

    @ApiModelProperty("职业类型 1 医务人员 2 非医务人员")
    private Integer job;

    @ApiModelProperty("诊断信息-是否在服药")
    private String diagMedicine;

    @ApiModelProperty("诊断信息-服药名称")
    private String diagMedicineName;

    @ApiModelProperty("诊断信息-服药剂量")
    private String diagMedicineNumber;

    @ApiModelProperty("诊断信息-性别")
    private String diagSex;

    @ApiModelProperty("诊断信息-身高")
    private String diagHeight;

    @ApiModelProperty("诊断信息-体重")
    private String diagWeight;

    @ApiModelProperty("诊断信息-受教育程度 1 小学 2 初中 3 中专/高中 4 大专 5 本科 6 硕士及以上")
    private Integer diagEducation;

    @ApiModelProperty("诊断信息-受教育具体年限")
    private Integer diagEducationYear;

    @ApiModelProperty("诊断信息-居住地")
    private String diagAddress;

    @ApiModelProperty("诊断信息-是否独生子女")
    private Integer diagOnlyChild;

    @ApiModelProperty("诊断信息-非独生子女家中排第几")
    private Integer diagChildRank;

    @ApiModelProperty("诊断信息-居住方式 1 和父母住 2 父母其中一人长期外出 3 隔代住 4 单亲家庭 5其中")
    private Integer diagAddressType;

    @ApiModelProperty("诊断信息-居住方式其他-说明")
    private String diagAddressTypeOther;

    @ApiModelProperty("诊断信息-是否首次就诊")
    private Integer diagFirstPatient;

}
