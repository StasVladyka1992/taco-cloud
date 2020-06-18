package tacos.kitchen.messaging.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import tacos.Order;
import tacos.kitchen.OrderReceiver;

@Profile("rabbitmq-template")
@Component("templateOrderReceiver")
public class RabbitOrderReceiver implements OrderReceiver {

  private RabbitTemplate rabbit;

  public RabbitOrderReceiver(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }
  
  public Order receiveOrder() {
    return (Order) rabbit.receiveAndConvert("tacocloud.order.queue");
  }

  public Order receiveOrderWithParametrization () {
    //in order to use ParametrizedTypeReference works only with SmartMessageConverter and Jackson2JsonMessageConverter
    return  rabbit.receiveAndConvert("tacocloud.order.queue", new ParameterizedTypeReference<Order>() {});
  }
}
