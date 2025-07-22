package io.github.restart.gmo_danggeun.dto.chat;

import lombok.Data;

public record ConfirmTradeDto(
    Long tradeId,
    Long chatRoomId
) {

}
