package com.solution.onlinestore.configs;

import com.solution.onlinestore.utils.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bimeri Noel
 * @date 5/22/2023
 */

@Configuration
public class MQConfig {
  public static final String queue = Constants.MESSAGE_QUEUE;
  public static final String exchangeKeyEmail = Constants.EXCHANGE_NOTIFICATION;
  public static final String routineKeyEmail = Constants.ROUTING_KEY_NOTIFICATION;

  @Bean
  public Queue queue() {
   return new Queue(queue);
  }

  @Bean
  public TopicExchange exchange() {
   return new TopicExchange(exchangeKeyEmail);
  }

  @Bean
  public Binding binding(Queue queue, TopicExchange exchange) {
   return BindingBuilder.bind(queue).to(exchange).with(routineKeyEmail);
  }

  @Bean
  public MessageConverter messageConverter() {
   return new Jackson2JsonMessageConverter();
  }

  @Bean
  public AmqpTemplate template(ConnectionFactory connectionFactory) {
   RabbitTemplate template = new RabbitTemplate(connectionFactory);
   template.setMessageConverter(messageConverter());
   return template;
  }
}
