package com.bootdo.testDemo.api;

import com.bootdo.api.entity.db.PsyQuestion;
import com.bootdo.api.entity.db.PsyQuestionScoreRange;
import com.bootdo.api.entity.db.PsyQuestionTopic;
import com.bootdo.api.entity.db.PsyQuestionTopicOptions;
import com.bootdo.api.service.PsyQuestionScoreRangeService;
import com.bootdo.api.service.PsyQuestionService;
import com.bootdo.api.service.PsyQuestionTopicOptionsService;
import com.bootdo.api.service.PsyQuestionTopicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelTest {

    @Autowired
    private PsyQuestionService questionService;

    @Autowired
    private PsyQuestionTopicService topicService;

    @Autowired
    private PsyQuestionTopicOptionsService optionsService;

    @Autowired
    private PsyQuestionScoreRangeService scoreRangeService;

    @Test
    public void excelIn() throws Exception {
        //量表1 心理僵化问卷
        addQuestion(0, new String[]{"从未", "罕见", "少见", "有时", "多见", "常见", "总是"},
                new String[]{"1", "2", "3", "4", "5", "6", "7"});
        //量表2 正念注意觉知量表（MAAS）量表
        addQuestion(1, new String[]{"几乎没有", "非常少见", "有点少见", "有点频繁", "非常频繁", "几乎总是"},
                new String[]{"6", "5", "4", "3", "2", "1"});
        //量表3 PHQ-9抑郁症筛查自评量表
        Long questionId = addQuestion(2, new String[]{"没有", "有几天", "一半以上时间", "几乎天天"},
                new String[]{"0", "1", "2", "3"});
        addScore(questionId, Arrays.asList("0#4#没有抑郁症#注意自我保重",
                "5#9#可能有轻微抑郁症#建议咨询心理医生或心理医学工作者",
                "10#14#可能有中度抑郁症#最好咨询心理医生或心理医学工作者",
                "15#19#可能有中重度抑郁症#建议咨询心理医生或精神科医生",
                "20#27#可能有重度抑郁症#一定要看心理医生或精神科医生"));
        //量表4 GAD-7焦虑症筛查自评量表
        questionId = addQuestion(3, new String[]{"没有", "有几天", "一半以上时间", "几乎天天"},
                new String[]{"0", "1", "2", "3"});
        addScore(questionId, Arrays.asList("0#4#没有焦虑症#注意自我保重",
                "5#9#可能有轻微焦虑症#建议咨询心理医生或心理医学工作者",
                "10#13#可能有中度焦虑症#最好咨询心理医生或心理医学工作者",
                "14#18#可能有中重度焦虑症#建议咨询心理医生或精神科医生",
                "19#21#可能有重度焦虑症#一定要看心理医生或精神科医生"));
        //量表5 失眠严重指数（Insomnia Severity Index，ISI）
        addQuestion(4, new String[]{"无", "轻度", "中度", "重度", "极重度"},
                new String[]{"0", "1", "2", "3", "4"});
        //量表6 知觉压力量表
        addQuestion(5, new String[]{"从不", "偶尔", "有时", "时常", "总是"},
                new String[]{"0", "1", "2", "3", "4"});
        //量表7  心理弹性量表简版（CD-RISC-10）
        addQuestion(6, new String[]{"从来不", "很少", "有时", "经常", "一直如此"},
                new String[]{"0", "1", "2", "3", "4"});
        //量表8简明国际神经精神访谈-自杀模块（MINI-C）
        // TODO 每个题目的是的对应分数都不一样,  score 100要改
        questionId = addQuestion(7, new String[]{"否", "是"},
                new String[]{"0", "1"});
        addScore(questionId, Arrays.asList("1#5#低风险# ", "6#9#中等风险# ", "10#100#高风险# "));
        //量表9 冗思反应量表
        addQuestion(8, new String[]{"从来没有", "有时", "经常", "总是"},
                new String[]{"1", "2", "3", "4"});
        //量表10  应激反应问卷--冗思分量表
        addQuestion(9, new String[]{"一点都不", "一点点", "一些", "很多"},
                new String[]{"1", "2", "3", "4"});
        //量表11  效果评估问卷OQ-45
        addQuestion(10, new String[]{"从未", "很少有", "有时有", "较多时候", "总是这样"},
                new String[]{"4", "3", "2", "1", "0"});

    }

    /**
     * 除1-11以外
     * @throws Exception
     */
    @Test
    public void excelIn2() throws Exception {
        //童年创伤量表
        addQuestion(0, new String[]{"从不", "偶尔", "有时", "常常", "总是"},
                new String[]{"1", "2", "3", "4", "5"});
        //IRI量表（共情量表）
        addQuestion(1, new String[]{"非常不符合", "有些不符合", "不能确定", "有些符合", "非常符合"},
                new String[]{"1", "2", "3", "4", "5"});
        //TAS量表（述情障碍量表）
        addQuestion(2, new String[]{"完全不同意", "不同意", "中立", "同意", "完全同意"},
                new String[]{"1", "2", "3", "4", "5"});
        //DARS量表（快感缺失量表）
        addQuestion(3, new String[]{"无", "偶尔", "有时", "常常", "总是"},
                new String[]{"0", "1", "2", "3", "4"});
        //青少年自伤行为问卷
        addQuestion(4, new String[]{"没有", "偶尔", "有时", "常常", "总是"},
                new String[]{"0", "1", "2", "3", "4"});
        //自伤功能问卷
        addQuestion(5, new String[]{"完全不符合", "不符合", "不确定", "符合", "完全符合"},
                new String[]{"0", "1", "2", "3", "4"});
        //自尊量表
        addQuestion(6, new String[]{"从不", "很少", "有时", "常常", "总是"},
                new String[]{"1", "2", "3", "4", "5"});
        //社会支持问卷
        addQuestion(7, new String[]{"非常不同意", "不同意", "有点不同意", "中立", "有点同意", "同意", "非常同意"},
                new String[]{"1", "2", "3", "4", "5", "6", "7"});
        //生活满意度问卷
        addQuestion(8, new String[]{"很可怕", "非常不满意", "满意与不满意部分差不多", "非常满意", "很幸福"},
                new String[]{"1", "2", "3", "4", "5"});
    }

    private void addScore(Long questionId, List<String> scoreStrList){
        for (String scoreStr : scoreStrList) {
            String[] split = scoreStr.split("#");
            PsyQuestionScoreRange scoreRange = new PsyQuestionScoreRange();
            scoreRange.setQuestionId(questionId);
            scoreRange.setCreateId(1L);
            scoreRange.setScoreLow(Integer.parseInt(split[0]));
            scoreRange.setScoreHigh(Integer.parseInt(split[1]));
            scoreRange.setResult(split[2]);
            scoreRange.setAdvice(split[3].replace(" ", ""));
            scoreRangeService.save(scoreRange);
        }
    }

    private Long addQuestion(Integer sheetNum, String[] optionNames, String[] optionScores) throws Exception {
        String fileUrl = "C:\\Users\\Administrator\\Desktop\\question2.xlsx";
        InputStream in = new FileInputStream(new File(fileUrl));
        Workbook workbook = WorkbookFactory.create(in);
        Sheet sheet = workbook.getSheetAt(sheetNum);
        Row row0 = sheet.getRow(0);
        PsyQuestion question = new PsyQuestion();
        question.setTitle(row0.getCell(0).getStringCellValue());
        question.setGuide(row0.getCell(1).getStringCellValue());
        question.setIndexId(1L);
        question.setOrderNum(99);
        if (row0.getCell(2) != null) {
            question.setRemark(row0.getCell(2).getStringCellValue());
        }
        Integer sumScore = sheet.getLastRowNum() * Integer.parseInt(optionScores[optionScores.length - 1]);
        question.setSumScore(sumScore + "");
        question.setCreateId(1L);
        questionService.save(question);
        log.info("正在导入问卷：" + row0.getCell(0).getStringCellValue());
        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            Row rowi = sheet.getRow(i);
            if (rowi == null || rowi.getCell(0) == null) {
                continue;
            }
            PsyQuestionTopic topic = new PsyQuestionTopic();
            topic.setQuestionId(Integer.parseInt(question.getId() + ""));
            //选择题
            topic.setTopicType(1);
            topic.setTopic(rowi.getCell(0).getStringCellValue());
            topic.setOrderNum(i);
            topic.setCreateId(1L);
            topicService.save(topic);
            log.info("正在导入题目：" + rowi.getCell(0).getStringCellValue());
            for (int j = 0; j < optionNames.length; j++) {
                PsyQuestionTopicOptions options = new PsyQuestionTopicOptions();
                options.setQuestionId(Integer.parseInt(question.getId() + ""));
                options.setQuestionTopicId(Integer.parseInt(topic.getId() + ""));
                options.setOptionName(optionNames[j]);
                options.setOptionScore(optionScores[j]);
                options.setCreateId(1L);
                optionsService.save(options);
            }
        }
        return question.getId();
    }

}
