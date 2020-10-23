package com.sezzle.Calculator.controler;



import com.sezzle.Calculator.model.Message;
import com.sezzle.Calculator.service.MessageConsumerService;
import com.sezzle.Calculator.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;


@RestController
public class ChatController {

   /* @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;*/

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageConsumerService messageConsumerService;

    @GetMapping("/")
    public void welcome(){
        
    }

    @CrossOrigin(origins ="https://sezzle-cal-client.herokuapp.com")
    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
    public void sendMessage(@RequestBody Message message) {
        message.setTimestamp(LocalDateTime.now());
        messageService.saveMessage(message);
        messageConsumerService.listen(message);
    }

    //    -------------- WebSocket API ----------------
    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message) {
        //Sending this message to all the subscribers
        return message;
    }

    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    public Message addUser(@Payload Message message,
                           SimpMessageHeaderAccessor headerAccessor) {
        // Add user in web socket session
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }

    @CrossOrigin(origins ="https://sezzle-cal-client.herokuapp.com")
    @GetMapping("/chat")
    public List<Message> getPreviousMessages()
    {
        return messageService.getLatestTen();
    }


}