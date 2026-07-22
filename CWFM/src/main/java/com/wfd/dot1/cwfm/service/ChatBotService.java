package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.dto.ChatRequest;
import com.wfd.dot1.cwfm.dto.ChatResponse;

public interface ChatBotService {

    ChatResponse processMessage(ChatRequest request);

}