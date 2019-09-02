package com.spring.service;

import com.spring.config.MQConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wang pengfei
 * @date 2019/9/2 15:14
 * @ClassName: hi-rabbit
 * @Description:
 */
@Service
public class Produce {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void  send(String message){
        rabbitTemplate.convertAndSend(MQConstants.EXCHANGE_NAME,MQConstants.ROUTING_KEY,message);
    }
}
