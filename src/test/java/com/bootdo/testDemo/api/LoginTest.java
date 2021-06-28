package com.bootdo.testDemo.api;

import com.bootdo.api.controller.SysWxUserController;
import com.bootdo.api.entity.req.common.CommonCodeReq;
import com.bootdo.api.entity.res.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {

    @Autowired
    private SysWxUserController sysWxUserController;

    @Test
    public void getQuestionList() {
        Response response = sysWxUserController.login(
                new CommonCodeReq("051GiKkl2Sy7i748Naml2Mls7U1GiKk3"));
        System.out.println(response);
    }

}
