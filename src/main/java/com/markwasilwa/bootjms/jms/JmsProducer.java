package com.markwasilwa.bootjms.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import com.markwasilwa.bootjms.model.dto.Employee;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Slf4j
@Component
public class JmsProducer {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${active-mq.topic}")
    private String topic;

    @Autowired
    ObjectMapper objectMapper;

    public void sendMessage(Employee employee) {
        jmsTemplate.send(topic, session -> {
            TextMessage message = null;
            try {
                message = session.createTextMessage();
                message.setJMSCorrelationID(objectMapper.writeValueAsString(employee));
                message.setJMSExpiration(0);
                message.setJMSMessageID("EmployeeReg");
                message.setJMSDestination(new ActiveMQTopic(topic));
                message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
                message.setJMSPriority(Message.DEFAULT_PRIORITY);
                message.setJMSTimestamp(System.nanoTime());
                message.setJMSType("TEXT");
                log.info("publishing TextMessage");
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            assert message != null;
            return message;
        });
    }
}
