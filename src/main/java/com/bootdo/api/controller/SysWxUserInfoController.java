package com.bootdo.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.SysWxUser;
import com.bootdo.api.entity.db.SysWxUserInfo;
import com.bootdo.api.entity.req.common.CommonOpenIdReq;
import com.bootdo.api.entity.res.Response;
import com.bootdo.api.service.SysWxUserInfoService;
import com.bootdo.api.service.SysWxUserService;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.constant.ResponseCodeEnum;
import com.bootdo.common.utils.FieldUtil;
import com.bootdo.common.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "个人信息完善")
@RequestMapping("/api/syswxuser")
public class SysWxUserInfoController {

    @Autowired
    private SysWxUserService sysWxUserService;

    @Autowired
    private SysWxUserInfoService sysWxUserInfoService;

    @ApiOperation("提交个人信息")
    @PostMapping("/submitWxUserInfo")
    public Response submitWxUserInfo(@RequestBody SysWxUserInfo infoReq){
        //查询是否存在该用户
        SysWxUser wxUser = sysWxUserService.getOne(new QueryWrapper<SysWxUser>()
                .eq(ColumnConsts.OPENID, infoReq.getOpenId()));
        if (wxUser == null) {
            return ResponseUtil.getFail(ResponseCodeEnum.NOT_EXIST_USER);
        }
        QueryWrapper<SysWxUserInfo> wrapper = new QueryWrapper<SysWxUserInfo>()
                .eq(ColumnConsts.OPENID, infoReq.getOpenId());
        SysWxUserInfo userInfo = sysWxUserInfoService.getOne(wrapper);
        if (userInfo == null) {
            sysWxUserInfoService.save(infoReq);
        } else {
            sysWxUserInfoService.update(infoReq, wrapper);
        }
        return ResponseUtil.getSuccess();
    }

    @ApiOperation("查询个人信息完善状态")
    @PostMapping("/getUserInfoStatus")
    public Response getUserInfoStatus(@RequestBody CommonOpenIdReq commonOpenIdReq){
        QueryWrapper<SysWxUserInfo> wrapper = new QueryWrapper<SysWxUserInfo>()
                .eq(ColumnConsts.OPENID, commonOpenIdReq.getOpenId());
        SysWxUserInfo userInfo = sysWxUserInfoService.getOne(wrapper);
        if (userInfo == null) {
            return ResponseUtil.getSuccess("未填写");
        } else if (FieldUtil.judgeClassHasNull(userInfo)){
            return ResponseUtil.getSuccess("待完善");
        } else {
            return ResponseUtil.getSuccess("已完善");
        }
    }

}
