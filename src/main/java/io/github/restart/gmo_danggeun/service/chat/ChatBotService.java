package io.github.restart.gmo_danggeun.service.chat;

public interface ChatBotService {

  /**
   * 사용자의 프롬프트를 받아 Gemini 모델로부터 답변을 생성합니다.
   *
   * @param prompt 사용자 입력 텍스트
   * @return 생성된 답변 텍스트
   */
  public String generateReply(String prompt);

}
