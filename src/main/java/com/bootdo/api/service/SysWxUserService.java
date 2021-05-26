package com.bootdo.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.SysWxUser;
import com.bootdo.api.mapper.SysWxUserMapper;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.constant.CommonConsts;
import com.bootdo.common.constant.EncryptConsts;
import com.bootdo.common.utils.ArithmeticUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author rory.chen
 * @date 2021-01-21 18:06
 */
@Service
@Slf4j
public class SysWxUserService extends BaseService<SysWxUserMapper, SysWxUser> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 处理微信登录信息
     *
     * @param sessionMap
     * @return token
     */
    public String handleWxSessionInfo(Map<String, String> sessionMap) {
        String openId = sessionMap.get(CommonConsts.OPENID);
        String sessionKey = sessionMap.get(CommonConsts.SESSION_KEY);
        String token = ArithmeticUtil.encryptAes(openId + sessionKey,
                EncryptConsts.AES_KEY);
        //查询是否第一次登录
        QueryWrapper<SysWxUser> wrapper = new QueryWrapper<SysWxUser>().eq(ColumnConsts.OPENID, openId);
        SysWxUser wxUser = getOne(wrapper);
        SysWxUser sysWxUser = new SysWxUser();
        sysWxUser.setSessionKey(sessionKey);
        sysWxUser.setToken(token);
        sysWxUser.setOpenId(openId);
        if (Objects.isNull(wxUser)){
            save(sysWxUser);
        } else {
            update(sysWxUser, wrapper);
        }
        //token 登录信息保存
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(token, new Gson().toJson(sysWxUser), 30, TimeUnit.DAYS);
        return token;
    }

}
