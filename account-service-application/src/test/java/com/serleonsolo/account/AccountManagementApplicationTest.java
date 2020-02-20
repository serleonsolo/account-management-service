package com.serleonsolo.account;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountManagementApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads()
            throws Throwable {
        Assert.assertNotNull("The application context should have loaded.", applicationContext);
    }

}
