package io.github.restart.gmo_danggeun.service.chat.impl;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import io.github.restart.gmo_danggeun.config.GeminiProperties;
import io.github.restart.gmo_danggeun.service.chat.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatBotServiceImpl implements ChatBotService {

  private final Client client;
  private final String defaultModel;

  @Autowired
  public ChatBotServiceImpl(GeminiProperties props) {
    this.client = Client.builder()
        .apiKey(props.getApiKey())
        .build();
    this.defaultModel = props.getModel();
  }

  @Override
  public String generateReply(String prompt) {
    GenerateContentResponse response = client.models.generateContent(defaultModel, prompt, null);
    return response.text();
  }
}
