package com.bootdo.testDemo.api;

import com.bootdo.api.controller.app.PsyQuestionController;
import com.bootdo.api.entity.req.common.CommonIdReq;
import com.bootdo.api.entity.res.common.Response;
import com.bootdo.common.exception.BasicException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionTest {

    @Autowired
    private PsyQuestionController psyQuestionController;

    @Test
    public void getQuestionList() {
        Response response = psyQuestionController.getQuestionList();
        System.out.println(response);
    }

    @Test
    public void getQuestionTopicById() throws BasicException {
        CommonIdReq req = new CommonIdReq();
        req.setId(1L);
        Response response = psyQuestionController.getQuestionTopicById(req);
        System.out.println(response);
    }

}
