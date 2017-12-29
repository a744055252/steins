package com.guanhuan.component.message.impl;

import com.guanhuan.component.message.destination.MyDestination;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** 
* MessageServiceImpl Tester. 
* 
* @author guanhuan
* @since <pre>12/27/2017</pre> 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-context.xml",
        "classpath:spring/spring-servlet.xml",
        "classpath:spring/spring-task.xml",
        "classpath:spring/spring-activemq.xml"
})
public class MessageServiceImplTest { 

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImplTest.class);

    @Autowired
    private ProducerServiceActiveMQ producerServiceActiveMQ;

    @Autowired
    private ConsumerServiceActiveMQ consumerServiceActiveMQ;

    private MyDestination myDestination;

    @Before
    public void before() throws Exception { 
    
    } 

    @After
    public void after() throws Exception { 
    
    } 

    /** 
    * 
    * Method: sendMessage(String message) 
    * 
    */ 
    @Test
    @Async
    public void testSendMessage() throws Exception {
        new Thread() {
            public void run() {
                for(int i=0; i < 100; i++) {
                    System.out.println("testSendMessage:"+i);
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                int i = 0;
                while(true) {
                    if(i >= 100)
                        break;
                    System.out.println("testGetMessage:"+i);
                }
            }
        }.start();
    }
    
    /** 
    * 
    * Method: getMessage() 
    * 
    */ 
    @Test
    @Async
    public void testGetMessage() throws Exception {
    }
    
        
}
