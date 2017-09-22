package net.vicp.sealedbook.dao.provider;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
* SystemFileProvider Tester.
*
* @author shitianshu on 06/22/2017.
*
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/spring-junit.xml"})
public class SystemFileProviderTest {

    @Resource
    private SystemFileProvider systemFileProvider;

    @Before
    public void before() throws Throwable {
        
    }

    @After
    public void after() throws Throwable {
        
    }

    /**
    *
    * Method: insert(String filePath, String fileCode)
    *
    */
    @Test
    public void testInsert() throws Throwable {
        systemFileProvider.insert("", "");
    }



    /** Log util */
    private static final Logger LOG = LoggerFactory.getLogger(SystemFileProviderTest.class);
} 
