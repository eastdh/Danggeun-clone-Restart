package io.github.restart.gmo_danggeun.service.chat;

import io.github.restart.gmo_danggeun.dto.chat.ChatRoomDetailDto;
import io.github.restart.gmo_danggeun.dto.chat.ChatRoomSummaryDto;
import java.util.List;

public interface ChatRoomService {

  List<ChatRoomSummaryDto> getChatRoomSummaries(Long userId, boolean showUnreadOnly);

  ChatRoomDetailDto getChatRoomDetail(Long chatRoomId, Long userId);

}
