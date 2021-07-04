package com.bootdo.api.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.SysWxUser;
import com.bootdo.api.entity.db.SysWxUserInfo;
import com.bootdo.api.entity.res.common.Response;
import com.bootdo.api.service.SysWxUserInfoService;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.constant.CommonConsts;
import com.bootdo.common.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@Api(tags = "个人信息完善")
@RequestMapping("/api/syswxuser")
public class SysWxUserInfoController {

    @Autowired
    private SysWxUserInfoService sysWxUserInfoService;

    @Autowired
    private HttpServletRequest request;

    @ApiOperation("获取个人信息")
    @PostMapping("/getWxUserInfo")
    public Response getWxUserInfo(){
        SysWxUser redisUser = (SysWxUser) request.getAttribute(CommonConsts.WX_API_USER_INFO);
        QueryWrapper<SysWxUserInfo> wrapper = new QueryWrapper<SysWxUserInfo>()
                .eq(ColumnConsts.OPENID, redisUser.getOpenId());
        return ResponseUtil.getSuccess(sysWxUserInfoService.getOne(wrapper));
    }

    @ApiOperation("提交个人信息")
    @PostMapping("/submitWxUserInfo")
    public Response submitWxUserInfo(@RequestBody SysWxUserInfo infoReq){
        SysWxUser redisUser = (SysWxUser) request.getAttribute(CommonConsts.WX_API_USER_INFO);
        infoReq.setOpenId(redisUser.getOpenId());
        QueryWrapper<SysWxUserInfo> wrapper = new QueryWrapper<SysWxUserInfo>()
                .eq(ColumnConsts.OPENID, redisUser.getOpenId());
        SysWxUserInfo userInfo = sysWxUserInfoService.getOne(wrapper);
        if (userInfo == null) {
            sysWxUserInfoService.save(infoReq);
        } else {
            sysWxUserInfoService.update(infoReq, wrapper);
        }
        return ResponseUtil.getSuccess();
    }

}
