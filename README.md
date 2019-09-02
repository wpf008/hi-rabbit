###### Spring全注解整合RabbitMQ
 
`   

    @Component
    public class RabbitMQConfig {
     
    @Bean
     public ConnectionFactory connectionFactory(){
         CachingConnectionFactory connectionFactory =new CachingConnectionFactory("localhost",5672);
         connectionFactory.setUsername("guest");
         connectionFactory.setPassword("guest");
         connectionFactory.setVirtualHost("/");
         connectionFactory.setPublisherConfirms(true);
         return connectionFactory;
     }
     
     @Bean
     public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
         RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
         return rabbitTemplate;
     }
     @Bean
     public Queue myQueue() {
         return new Queue(MQConstants.QUEUE_NAME);
     }
     
     /**
      * SimpleMessageListenerContainer这个bean是实现消息监听
      * @return
      */
      
     @Bean
     public SimpleMessageListenerContainer container() {
         SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
         container.setConnectionFactory(connectionFactory());
         container.setQueueNames(new String[]{MQConstants.QUEUE_NAME});
         container.setChannelTransacted(false);
         container.setConcurrentConsumers(10);
         container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
         container.setMessageListener(new MessageListenerAdapter(new ChannelAwareMessageListener(){
             @Override
             public void onMessage(Message message, Channel channel) throws Exception {
                 long deliveryTag = message.getMessageProperties().getDeliveryTag();
                 String content = new String(message.getBody());
                 String routingKey = message.getMessageProperties().getReceivedRoutingKey();
                 System.out.println(content);
                 System.out.println(routingKey);
                 //根据对不同的routingKey进行你的业务处理实现异步
                 channel.basicAck(deliveryTag, false);
             }
         }));
         return container;
     }
     
     /**
      *RabbitListenerContainerFactory这个Bean
      * 配合MyMessageListener类以及Application类开启@EnableRabbit
      * 可以实现SimpleMessageListenerContainer bean一样的消息监听
      @Bean
      public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
        }
      */
 
     @Bean
     public DirectExchange exchange() {
         return new DirectExchange(MQConstants.EXCHANGE_NAME);
     }
     @Bean
     public Binding binding() {
         return BindingBuilder.bind(this.myQueue()).to(this.exchange()).with(MQConstants.ROUTING_KEY);
     }
    }
 `