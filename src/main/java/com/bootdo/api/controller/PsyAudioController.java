package com.bootdo.api.controller;

import com.bootdo.api.entity.db.PsyClockRecord;
import com.bootdo.api.entity.db.SysWxUser;
import com.bootdo.api.entity.res.Response;
import com.bootdo.api.service.PsyClockRecordService;
import com.bootdo.common.constant.CommonConsts;
import com.bootdo.common.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@Api(tags = "音频接口")
@RequestMapping("/api/audio")
public class PsyAudioController {

    @Autowired
    private PsyClockRecordService psyClockRecordService;

    @Autowired
    private HttpServletRequest request;

    @ApiOperation("打卡")
    @PostMapping("/clock")
    public Response clock(){
        SysWxUser userInfo = (SysWxUser) request.getAttribute(CommonConsts.WX_API_USER_INFO);
        PsyClockRecord clockRecord = new PsyClockRecord();
        clockRecord.setOpenId(userInfo.getOpenId());
        clockRecord.setSubmitDate(LocalDate.now().format(CommonConsts.DTF_DAY));
        clockRecord.setSubmitDateFull(LocalDateTime.now().format(CommonConsts.DTF_SECONDS));
        psyClockRecordService.save(clockRecord);
        return ResponseUtil.getSuccess();
    }


}
