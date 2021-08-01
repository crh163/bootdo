package com.bootdo.api.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.SysWxUser;
import com.bootdo.api.entity.db.SysWxUserInfo;
import com.bootdo.api.entity.res.common.Response;
import com.bootdo.api.entity.res.index.IndexInfoRes;
import com.bootdo.api.service.PsyClockRecordService;
import com.bootdo.api.service.PsyQuestionRecordService;
import com.bootdo.api.service.SysWxUserInfoService;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.constant.CommonConsts;
import com.bootdo.common.domain.sys.DictDO;
import com.bootdo.common.utils.FieldUtil;
import com.bootdo.common.utils.RedisTemplateUtil;
import com.bootdo.common.utils.ResponseUtil;
import com.bootdo.system.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "首页基础信息接口")
@RequestMapping("/api/index")
public class AppIndexController {

    @Autowired
    private SysWxUserInfoService sysWxUserInfoService;

    @Autowired
    private PsyQuestionRecordService psyQuestionRecordService;

    @Autowired
    private PsyClockRecordService psyClockRecordService;

    @Autowired
    private DictService dictService;

    @Autowired
    private HttpServletRequest request;

    @ApiOperation("查询首页基础信息")
    @PostMapping("/getIndexInfo")
    public Response getIndexInfo(){
        String token = request.getHeader(CommonConsts.X_ACCESS_TOKEN);
        SysWxUser sysWxUser = RedisTemplateUtil.getRedisString(
                CommonConsts.WX_TOKEN_REDIS_PREFIX + token, SysWxUser.class);
        //如果未登陆则需要返回部分信息
        if (sysWxUser == null) {
            return ResponseUtil.getSuccess(buildBaseIndexInfoRes());
        }
        SysWxUserInfo userInfo = sysWxUserInfoService.getOne(new QueryWrapper<SysWxUserInfo>()
                .eq(ColumnConsts.OPENID, sysWxUser.getOpenId()));
        IndexInfoRes indexInfoRes = buildBaseIndexInfoRes();
        indexInfoRes.setUserInfoStatus(FieldUtil.judgeClassHasNull(userInfo));
        indexInfoRes.setQuestionSubmitNewTime(psyQuestionRecordService.selectQuestionSubmitNewTime(sysWxUser.getOpenId()));
        indexInfoRes.setBeforeClockTime(psyClockRecordService.selectBeforeClockTime(sysWxUser.getOpenId()));
        return ResponseUtil.getSuccess(indexInfoRes);
    }

    /**
     * 封装首页基本信息
     *
     * @return
     */
    private IndexInfoRes buildBaseIndexInfoRes(){
        IndexInfoRes indexInfoRes = new IndexInfoRes();
        //问卷本次最后截止时间
        List<DictDO> dictList = dictService.listByType(CommonConsts.DICT_APP_QUESTION_TIME);
        if (!CollectionUtils.isEmpty(dictList)) {
            indexInfoRes.setQuestionLastTime(dictList.get(0).getValue());
        }
        return indexInfoRes;
    }

    @ApiOperation("删除问卷信息redis数据")
    @PostMapping("/deleteRedisQuestion")
    public Response deleteRedisQuestion(){

        return ResponseUtil.getSuccess();
    }

}
