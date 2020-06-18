package tacos.messaging;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import tacos.Order;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessagingConfig {

    @Bean
    public MappingJackson2MessageConverter messageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
        //это необходимо, для большей гибкости. Вообще тут должно быть имя класс, в который конвертить содержимое
        //но может быть и просто имя. Главное, чтобы у получателя и отправителя эти параметры были одинаковы. Тут мы
        //присваиваем имя для typeIdProperty.
        messageConverter.setTypeIdPropertyName("_typeId");
        //order - это значение, которое будет присвоено property "_typeId". У receiver будет настроен аналогичный
        //converter, который будет имеет свое понятие, какой класс скрывается за "order"
        typeIdMappings.put("order", Order.class);
        messageConverter.setTypeIdMappings(typeIdMappings);

        return messageConverter;
    }


//ручное создание бина Destination, для указания, куда отправлять сообщения через jms
//это также можно делать с помощью указания property
// spring:
//  jms:
//    template:
//      default-destination: tacocloud.order.queue

//    @Bean
//    public Destination orderQueue() {
//        return new ActiveMQQueue("tacocloud.order.queue");
//    }

}
