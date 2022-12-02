package com.markwasilwa.bootjms.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markwasilwa.bootjms.model.dto.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
@Slf4j
public class JmsConsumer implements MessageListener {

    @Autowired
    ObjectMapper objectMapper;

    @Value(value = "${active-mq.topic}")
    String topic;

    @Override
    @JmsListener(destination = "${active-mq.topic}")
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String jmsCorrelationID = textMessage.getJMSCorrelationID();
            Employee employee = objectMapper.readValue(jmsCorrelationID, Employee.class);
            log.info("Received message from Topic:{} -> {}", topic, employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
