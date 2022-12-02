package com.markwasilwa.bootjms.tasks;

import com.markwasilwa.bootjms.jms.JmsProducer;
import lombok.extern.slf4j.Slf4j;
import com.markwasilwa.bootjms.model.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class EmployeePublisher {

    @Autowired
    JmsProducer jmsProducer;

    @Value("${active-mq.topic}")
    private String topic;

    @Scheduled(fixedDelay = 1000 * 5)
    public void publish() {
        Employee employee = new Employee();
        employee.setEmployeeId("1");
        employee.setEmployeeFullName("Mark Wasilwa");
        employee.setEmployeeFirstName("Mark");
        employee.setEmployeeMiddleName("Mbirira");
        employee.setEmployeeLastName("Wasilwa");
        jmsProducer.sendMessage(employee);
    }
}
