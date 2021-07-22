package com.bootdo.api.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.api.entity.db.PsyAudioRecord;
import com.bootdo.api.entity.db.PsyClockRecord;
import com.bootdo.api.entity.res.common.Response;
import com.bootdo.api.entity.res.index.ManagerIndexInfoRes;
import com.bootdo.api.entity.vo.question.QueryQuestion;
import com.bootdo.api.service.PsyAudioRecordService;
import com.bootdo.api.service.PsyClockRecordService;
import com.bootdo.api.service.PsyQuestionRecordService;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.constant.CommonConsts;
import com.bootdo.common.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private PsyQuestionRecordService psyQuestionRecordService;

    @Autowired
    private PsyClockRecordService psyClockRecordService;

    @Autowired
    private PsyAudioRecordService psyAudioRecordService;

    @PostMapping("/getIndexInfo")
    @ResponseBody
    public Response list() {
        String today = LocalDate.now().format(CommonConsts.DTF_DAY);
        List<String> todayList = psyQuestionRecordService.getAllTodayData(today);
        List<QueryQuestion> questionList = psyQuestionRecordService.getLastRecordList(today);
        List<PsyClockRecord> clockList = psyClockRecordService.list(new QueryWrapper<PsyClockRecord>()
                .eq(ColumnConsts.SUBMIT_DATE, today).orderByDesc(ColumnConsts.SUBMIT_DATE_FULL)
                .last("limit 0,4"));
        List<PsyAudioRecord> audioList = psyAudioRecordService.list(new QueryWrapper<PsyAudioRecord>()
                .eq(ColumnConsts.SUBMIT_DATE, today).orderByDesc(ColumnConsts.SUBMIT_DATE_FULL)
                .last("limit 0,4"));
        ManagerIndexInfoRes res = new ManagerIndexInfoRes();
        if (todayList != null && todayList.size() == 3) {
            res.setQuestionNumber(todayList.get(0));
            res.setClockNumber(todayList.get(1));
            res.setAudioNumber(todayList.get(2));
        }
        res.setQuestionRecordList(questionList);
        res.setClockRecordList(clockList);
        res.setAudioRecordList(audioList);
        return ResponseUtil.getSuccess(res);
    }

}
