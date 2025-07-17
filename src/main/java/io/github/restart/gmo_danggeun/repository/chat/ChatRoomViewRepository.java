package io.github.restart.gmo_danggeun.repository.chat;

import io.github.restart.gmo_danggeun.entity.readonly.ChatRoomView;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomViewRepository extends JpaRepository<ChatRoomView, Long> {

  List<ChatRoomView> findAllByMeId(Long userId);

  Optional<ChatRoomView> findByChatRoomIdAndMeId(Long chatRoomId, Long userId);
}
