package com.bootdo.api.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.*;
import com.bootdo.api.entity.req.common.CommonIdReq;
import com.bootdo.api.entity.res.audio.GetAudioIndexListRes;
import com.bootdo.api.entity.res.audio.GetAudioListRes;
import com.bootdo.api.entity.res.common.Response;
import com.bootdo.api.service.PsyAudioIndexService;
import com.bootdo.api.service.PsyAudioRecordService;
import com.bootdo.api.service.PsyAudioService;
import com.bootdo.api.service.PsyClockRecordService;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.constant.CommonConsts;
import com.bootdo.common.constant.ResponseCodeEnum;
import com.bootdo.common.exception.BasicException;
import com.bootdo.common.utils.DataUtils;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "音频接口")
@RequestMapping("/api/audio")
public class PsyAudioController {

    @Autowired
    private PsyAudioService psyAudioService;

    @Autowired
    private PsyClockRecordService psyClockRecordService;

    @Autowired
    private PsyAudioRecordService psyAudioRecordService;

    @Autowired
    private PsyAudioIndexService psyAudioIndexService;

    @Autowired
    private HttpServletRequest request;

    @ApiOperation("获取音频栏目列表")
    @PostMapping("/getAudioIndexList")
    public Response getAudioIndexList() {
        clock();
        List<PsyAudioIndex> list = psyAudioIndexService.list(new QueryWrapper<PsyAudioIndex>()
                .orderByAsc(ColumnConsts.ORDER_NUM));
        return ResponseUtil.getSuccess(DataUtils.coverList(list, GetAudioIndexListRes.class));
    }

    @ApiOperation("获取音频列表")
    @PostMapping("/getAudioList")
    public Response getAudioList(@RequestBody CommonIdReq commonIdReq) {
        QueryWrapper<PsyAudio> wrapper = new QueryWrapper<PsyAudio>()
                .eq(ColumnConsts.INDEX_ID, commonIdReq.getId())
                .orderByAsc(ColumnConsts.ORDER_NUM)
                .select(ColumnConsts.ID, ColumnConsts.AUDIO_NAME, ColumnConsts.AUDIO_AUTHOR,
                        ColumnConsts.AUDIO_AVATAR_URL, ColumnConsts.AUDIO_URL);
        List<PsyAudio> list = psyAudioService.list(wrapper);
        return ResponseUtil.getSuccess(DataUtils.coverList(list, GetAudioListRes.class));
    }

    @ApiOperation("获取音频详情信息")
    @PostMapping("/getAudioInfoById")
    public Response getAudioInfoById(@RequestBody CommonIdReq commonIdReq) throws BasicException {
        SysWxUser userInfo = (SysWxUser) request.getAttribute(CommonConsts.WX_API_USER_INFO);
        PsyAudio audio = psyAudioService.getById(commonIdReq.getId());
        if (audio == null) {
            throw new BasicException(ResponseCodeEnum.NOT_EXIST_AUDIO);
        }
        //保存音频聆听记录
        PsyAudioRecord audioRecord = new PsyAudioRecord();
        audioRecord.setOpenId(userInfo.getOpenId());
        audioRecord.setAudioId(audio.getId());
        audioRecord.setAudioName(audio.getAudioName());
        audioRecord.setSubmitDate(LocalDate.now().format(CommonConsts.DTF_DAY));
        audioRecord.setSubmitDateFull(LocalDateTime.now().format(CommonConsts.DTF_SECONDS));
        log.info("用户 {} 获取音频详情 {}，进行聆听记录保存。", userInfo.getOpenId(), audio.getAudioName());
        psyAudioRecordService.save(audioRecord);
        return ResponseUtil.getSuccess(audio);
    }


    /**
     * 打卡记录
     *
     * @return
     */
    private boolean clock(){
        SysWxUser userInfo = (SysWxUser) request.getAttribute(CommonConsts.WX_API_USER_INFO);
        PsyClockRecord clockRecord = new PsyClockRecord();
        clockRecord.setOpenId(userInfo.getOpenId());
        clockRecord.setSubmitDate(LocalDate.now().format(CommonConsts.DTF_DAY));
        clockRecord.setSubmitDateFull(LocalDateTime.now().format(CommonConsts.DTF_SECONDS));
        log.info("用户 {} 获取音频列表，进行打卡记录保存。", userInfo.getOpenId());
        return psyClockRecordService.save(clockRecord);
    }


}
