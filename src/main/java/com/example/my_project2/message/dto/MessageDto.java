package com.example.my_project2.message.dto;

import com.example.my_project2.message.entity.Message;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MessageDto {
    private Long id;
    private String content;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private boolean confirm;
    private Long replyId;
    private String reply;
    public static MessageDto dto(Message message){
        MessageDto messageDto = MessageDto.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSender().getId())
                .senderName(message.getSender().getName())
                .receiverId(message.getReceiver().getId())
                .receiverName(message.getReceiver().getName())
                .confirm(message.isConfirm())
                .replyId(message.getReplyId())
                .reply(message.getReply())
                .build();
        return messageDto;
    }
}
