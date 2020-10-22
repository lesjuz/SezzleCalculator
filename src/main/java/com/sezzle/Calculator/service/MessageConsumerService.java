package com.sezzle.Calculator.service;


import com.sezzle.Calculator.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {
    @Autowired
    SimpMessagingTemplate template;


    public void listen(Message message) {

        template.convertAndSend("/topic/group", message);
    }
}
