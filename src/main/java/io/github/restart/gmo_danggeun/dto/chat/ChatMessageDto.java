package io.github.restart.gmo_danggeun.dto.chat;

public class ChatMessageDto {

    private Long id;

    private Long chatRoomId;

    private Long writerId;

    private String content;

    private String createdAt;

    private Boolean readOrNot;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getReadOrNot() {
        return readOrNot;
    }

    public void setReadOrNot(Boolean readOrNot) {
        this.readOrNot = readOrNot;
    }

}