package com.spring.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Wang pengfei
 * @date 2019/9/2 15:17
 * @ClassName: hi-rabbit
 * @Description:
 */
@Component
public class MyMessageListener  {

    @RabbitListener(queues = MQConstants.QUEUE_NAME)
    public void processss(Message message, Channel channel) throws IOException {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        String content = new String(message.getBody());
        System.out.println(routingKey);
        System.out.println("******************");
        System.out.println(content);
        //根据对不同的routingKey进行你的业务处理实现异步
        channel.basicAck( message.getMessageProperties().getDeliveryTag(), false);
    }

}
