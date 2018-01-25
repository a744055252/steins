package com.guanhuan.controller;

import com.guanhuan.component.message.ConsumerService;
import com.guanhuan.component.message.ProducerService;
import com.guanhuan.component.message.impl.MessageServiceImpl;
import com.guanhuan.config.ResultStatus;
import com.guanhuan.model.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * 消息队列服务
 *
 * @author liguanhuan_a@163.com
 * @create 2017-12-27 9:54
 **/

//@RestController
//@RequestMapping("/Message")
public class MessageController {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Resource(name = "producerServiceActiveMQ")
    private ProducerService producerService;

    @Resource(name= "consumerServiceActiveMQ")
    private ConsumerService consumerService;

    @Resource(name = "messageServiceImpl")
    private MessageServiceImpl messageService;

    @Autowired
    private Destination queueDestination;

    @Autowired
    private Destination emailDestination;

    @Autowired
    private Destination phoneMsgDestination;

    @Autowired
    private Destination topicDestination;

    @RequestMapping(value = "/message/{msg}", method = RequestMethod.PUT)
    public ResultModel<String> sendMessage(@PathVariable("msg") String msg) throws Exception {
        producerService.sendMessage(msg, queueDestination);
        return ResultModel.ok();
    }

    @RequestMapping(value = "/emailMessage/{msg}", method = RequestMethod.PUT)
    public ResultModel<String> sendEmailMessage(@PathVariable("msg") String msg) throws Exception {
        producerService.sendMessage(msg, emailDestination);
        return ResultModel.ok();
    }

    @RequestMapping(value = "/phoneMsgMessage/{msg}", method = RequestMethod.PUT)
    public ResultModel<String> sendPhoneMsgMessage(@PathVariable("msg") String msg) throws Exception {
        producerService.sendMessage(msg, phoneMsgDestination);
        return ResultModel.ok();
    }

    @RequestMapping(value = "/phoneMsgMessage", method = RequestMethod.GET)
    public ResultModel<String> getPhoneMsgMessage() throws Exception {
        return ResultModel.ok(consumerService.getMessage(phoneMsgDestination));
    }

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public ResultModel<String> getmessage() throws Exception {
        String result = consumerService.getMessage();
        return ResultModel.ok(result);
    }


    @RequestMapping(value = "/phoneTopic", method = RequestMethod.GET)
    public ResultModel<String> getPhoneTopic() throws Exception {
        return ResultModel.ok(consumerService.getMessage(topicDestination));
    }

    @RequestMapping(value = "/phoneTopic/{msg}", method = RequestMethod.PUT)
    public ResultModel<String> sendPhoneTopic(@PathVariable String msg) throws Exception {
        producerService.sendMessage(msg, topicDestination);
        return ResultModel.ok();
    }

    @RequestMapping(value = "/emailMessage", method = RequestMethod.POST)
    public ResultModel<?> sendEmail()  {
        try {
            messageService.sendMessage("744055252@qq.com", emailDestination);
        } catch (Exception e) {
            logger.error("发送邮件失败", e);
            return ResultModel.error(ResultStatus.USER_EMAIL_ERROR);
        }
        return ResultModel.ok();
    }
}
