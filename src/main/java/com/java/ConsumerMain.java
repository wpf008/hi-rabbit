package com.java;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Wang pengfei
 * @date 2019/9/2 15:08
 * @ClassName: ConsumerMain
 * @Description:
 */
public class ConsumerMain {
    private static String queueName ="test_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Address[] addresses =new Address[]{
                new Address("127.0.0.1",5672)
        };
        ConnectionFactory factory =new ConnectionFactory();
        factory.setPassword("guest");
        factory.setUsername("guest");
        Connection connection =factory.newConnection(addresses);

        final Channel channel = connection.createChannel();
        channel.basicQos(64);
        Consumer consumer =new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("message: "+new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(queueName,consumer);
        TimeUnit.SECONDS.sleep(1);

    }
}
