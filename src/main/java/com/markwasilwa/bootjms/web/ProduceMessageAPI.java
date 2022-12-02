package com.markwasilwa.bootjms.web;


import com.markwasilwa.bootjms.model.dto.Employee;
import com.markwasilwa.bootjms.jms.JmsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProduceMessageAPI {

    @Autowired
    JmsProducer jmsProducer;

    @RequestMapping(value = "/api/employee",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee sendMessage(@RequestBody Employee employee) {
        jmsProducer.sendMessage(employee);
        return employee;
    }
}
