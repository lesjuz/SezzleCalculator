package com.sezzle.Calculator;


import com.sezzle.Calculator.controler.ChatController;
import com.sezzle.Calculator.model.Message;
import com.sezzle.Calculator.repository.MessageRepository;
import com.sezzle.Calculator.service.MessageConsumerService;
import com.sezzle.Calculator.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ChatController.class)
@RunWith(SpringRunner.class)
public class ChatControllerTest {
    @Autowired
    private MockMvc mvc;


    @MockBean
    private MessageService messageService;

    @MockBean
    private MessageConsumerService messageConsumerService;


    @Test
    public void testImHealthy() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void testReturnLastTenMessages() throws Exception {
        Mockito.when(messageService.getLatestTen()).thenReturn(Collections.singletonList(new Message("jules", "1+2=3", LocalDateTime.now())));
        mvc.perform(get("/chat")).andExpect(status().isOk());
    }



}