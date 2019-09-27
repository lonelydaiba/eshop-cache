package com.daily.eshop.cache.ha;

import com.daily.eshop.cache.ha.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    /**
     * 测试同步
     */
    @Test
    public void testGetUserId() {
        System.out.println("=================" + userService.getUserId("lisi"));
    }

    /**
     * 测试异步
     */
/*    @Test
    public void testGetUserName() throws ExecutionException, InterruptedException {
        System.out.println("=================" + userService.getUserName(30L).get());
    }*/
}
