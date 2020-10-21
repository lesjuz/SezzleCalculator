package com.sezzle.Calculator.service;



import com.sezzle.Calculator.model.Message;

import java.util.List;

public interface MessageService {
    void saveMessage(Message message);
    List<Message> getLatestTen();
}
