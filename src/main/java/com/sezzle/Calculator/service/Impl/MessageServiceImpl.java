package com.sezzle.Calculator.service.Impl;


import com.sezzle.Calculator.model.Message;
import com.sezzle.Calculator.repository.MessageRepository;
import com.sezzle.Calculator.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public List<Message> getLatestTen() {
        return messageRepository.findTop10ByOrderByTimestampDesc();
    }
}
