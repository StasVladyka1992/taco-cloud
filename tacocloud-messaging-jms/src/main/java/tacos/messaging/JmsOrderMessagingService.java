package tacos.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import tacos.Order;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

  private JmsTemplate template;

  @Autowired
  public JmsOrderMessagingService(JmsTemplate template) {
    this.template = template;
  }

  @Override
  public void sendOrder(Order order) {
    //this::addOrderSource - это MessagePostProcessor. Который используется для метода convertAndSend,
    //т.к. при его использовании я не имею прямого доступа к Message. В данном случае вместо изменения структур Order,
    //используемых для конвертации, я использую message property, которую потом можно будет получить.
    template.convertAndSend("tacocloud.order.queue", order, this::addOrderSource);
  }
  
  private Message addOrderSource(Message message) throws JMSException {
    message.setStringProperty("X_ORDER_SOURCE", "WEB");
    return message;
  }

}
