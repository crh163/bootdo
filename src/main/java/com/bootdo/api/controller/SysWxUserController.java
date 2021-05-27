package com.bootdo.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.SysWxUser;
import com.bootdo.api.entity.req.CommonCodeReq;
import com.bootdo.api.service.SysWxUserService;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.constant.CommonConsts;
import com.bootdo.common.domain.properties.WechatAuthProperties;
import com.bootdo.common.utils.ResponseUtil;
import com.bootdo.api.entity.res.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@Api(tags = "微信用户")
@RequestMapping("/api/syswxuser")
public class SysWxUserController {

    @Autowired
    private SysWxUserService sysWxUserService;

    @Autowired
    private WechatAuthProperties wechatAuthProperties;

    @Autowired
    private HttpServletRequest request;

    @ApiOperation("微信用户登录")
    @PostMapping("/login")
    public Response login(@RequestBody CommonCodeReq commonCodeReq) {
        String code = commonCodeReq.getCode();
        Map<String, String> sessionMap = loginWxSession(code);
        if(Objects.isNull(sessionMap) || Objects.isNull(sessionMap.get(CommonConsts.OPENID))){
            return ResponseUtil.getFail(sessionMap.get(CommonConsts.ERRMSG));
        }
        String token = sysWxUserService.handleWxSessionInfo(sessionMap);
        return ResponseUtil.getSuccess(token);
    }

    @ApiOperation("上传微信用户信息")
    @PostMapping("/uploadWxUserInfo")
    public Response uploadWxUserInfo(@RequestBody SysWxUser sysWxUser){
        //更新时忽略客户端上传的session
        sysWxUser.setSessionKey(null);
        sysWxUser.setToken(null);
        SysWxUser userInfo = (SysWxUser) request.getAttribute(CommonConsts.WX_API_USER_INFO);
        sysWxUserService.update(sysWxUser, new QueryWrapper<SysWxUser>()
                .eq(ColumnConsts.OPENID, userInfo.getOpenId()));
        return ResponseUtil.getSuccess();
    }

    /**
     * 微信程序登录
     * @param code
     * @return
     */
    private Map<String, String> loginWxSession(String code) {
        log.info("loginWxSession 微信用户登录code : {}", code);
        String url = wechatAuthProperties.getLoginUrl()
                + "?appid=" + wechatAuthProperties.getAppid()
                + "&secret=" + wechatAuthProperties.getSecret()
                + "&js_code=" + code
                + "&grant_type=" + wechatAuthProperties.getGrantType();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String json = EntityUtils.toString(responseEntity);
                log.info("loginWxSession 响应内容为:" + json);
                return new Gson().fromJson(json, new TypeToken<Map<String, String>>() {
                }.getType());
            }
        } catch (Exception e) {
            log.error("微信授权登录失败！！！", e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("资源释放失败！！！", e);
            }
        }
        return new HashMap<>();
    }


}
