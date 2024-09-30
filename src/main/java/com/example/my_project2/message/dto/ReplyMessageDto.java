//package com.example.my_project2.message.dto;
//
//import com.example.my_project2.message.entity.Message;
//import com.example.my_project2.message.entity.ReplyMessage;
//import lombok.*;
//
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@ToString
//public class ReplyMessageDto {
//    private Long id;
//    private String messageReply;
//    private Long messageReplyId;
//    private String messageAttach;
//    private Long messageAttachId;
//
//    public static ReplyMessageDto dto(ReplyMessage replyMessage){
//        ReplyMessageDto replyMessageDto= ReplyMessageDto.builder()
//                .id(replyMessage.getId())
//                .messageReply(replyMessage.getMessageReply().getContent())
//                .messageReplyId(replyMessage.getMessageReply().getId())
//                .messageAttach(replyMessage.getMessageAttach().getContent())
//                .messageAttachId(replyMessage.getMessageAttach().getId())
//                .build();
//        return replyMessageDto;
//    }
//}
