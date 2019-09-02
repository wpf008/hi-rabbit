package com.spring;

import com.spring.service.Produce;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Wang pengfei
 * @date 2019/9/2 15:09
 * @ClassName: hi-rabbit
 * @Description:
 */
@EnableRabbit
@ComponentScan("com.spring")
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =new AnnotationConfigApplicationContext(Application.class);
        Produce prod = context.getBean(Produce.class);
        prod.send("hi rabbit mq");
    }
}
