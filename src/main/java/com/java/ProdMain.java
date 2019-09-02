package com.java;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Wang pengfei
 * @date 2019/9/2 15:07
 * @ClassName: hi-rabbit
 * @Description:
 */
public class ProdMain {
    private static String exchangeName ="test_mq_ex";
    private static String queueName ="test_queue";
    private static String routingKey ="routingKey";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory =new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection =connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT.getType(),true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);
        String message = "hello world";
        channel.basicPublish(exchangeName,routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        channel.close();
        connection.close();

    }
}
