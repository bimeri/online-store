package com.solution.onlinestore.services;

import com.solution.onlinestore.configs.MQConfig;
import com.solution.onlinestore.dto.EmailDto;
import com.solution.onlinestore.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Bimeri Noel
 * @date 5/22/2023
 */

@Service
public class RabbitService {
 private TopicExchange topicExchange;
 private final RabbitTemplate template;
 private final AmqpAdmin amqpAdmin;
 private final Logger LOG = LogManager.getLogger(RabbitService.class);

 @Autowired
 void setPushNotificationExchange(@Qualifier("exchange") TopicExchange exchange) {
  this.topicExchange = exchange;
 }

 @Autowired
 public RabbitService(RabbitTemplate rabbitTemplate, AmqpAdmin amqpAdmin) {
  this.template = rabbitTemplate;
  this.amqpAdmin = amqpAdmin;
 }

 public void sendNotificationToRabbit(EmailDto message) {
  Queue queue = new Queue(Constants.MESSAGE_QUEUE);
  Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(MQConfig.routineKeyEmail);
  amqpAdmin.declareQueue(queue);
  amqpAdmin.declareBinding(binding);
  template.convertAndSend(Constants.EXCHANGE_NOTIFICATION, MQConfig.routineKeyEmail, message);
  LOG.info("successfully sent notification to rabbit");
 }
}
