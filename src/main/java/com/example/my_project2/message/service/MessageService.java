package com.example.my_project2.message.service;

import com.example.my_project2.message.dto.MessageDto;
import com.example.my_project2.message.entity.Message;
import com.example.my_project2.message.repo.MessageRepo;
import com.example.my_project2.service.WebService;
import com.example.my_project2.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepo messageRepo;
    private final WebService webService;
//    @Autowired
//    private final SimpMessagingTemplate messagingTemplate;

    public MessageDto create(MessageDto dto){
        UserEntity sender = webService.userLogin();
        UserEntity receiver = webService.userById(dto.getReceiverId());

        Message newMessage = Message.builder()
                .content(dto.getContent())
                .sender(sender)
                .receiver(receiver)
                .confirm(false)
                .build();
        if (dto.getReplyId()!=null){
            Message replyMessage= messageRepo.findById(dto.getReplyId())
                    .orElseThrow(()-> new IllegalArgumentException("No exist message!!!"));
            newMessage.setReply(replyMessage.getContent());
            newMessage.setReplyId(dto.getReplyId());
        }
        MessageDto messageDto =MessageDto.dto(messageRepo.save(newMessage));
//                // Broadcast the message to WebSocket subscribers
//                messagingTemplate.convertAndSend("/topic/messages", messageDto);
        return messageDto;
    }

    public MessageDto edit(MessageDto dto, Long messageId){
        UserEntity sender = webService.userLogin();
        Message message = messageRepo.findById(messageId)
                .orElseThrow(()-> new IllegalArgumentException("No exist message!!"));
        if (!message.getSender().getId().equals(sender.getId()))
            throw new IllegalArgumentException("No exist message");
        else if (message.isConfirm()) throw new IllegalArgumentException("Cannot edit this message");
        message.setContent(dto.getContent());
        return MessageDto.dto(messageRepo.save(message));
    }

    public MessageDto remove(Long messageId){
        UserEntity sender = webService.userLogin();
        Message message = messageRepo.findById(messageId)
                .orElseThrow(()-> new IllegalArgumentException("No exist message!!"));
        if (!message.getSender().getId().equals(sender.getId())
                && !message.getReceiver().getId().equals(sender.getId()))
            throw new IllegalArgumentException("No exist message");
        message.setContent("This message has been removed!!!");
        return MessageDto.dto(messageRepo.save(message));
    }

    public void delete(Long messageId){
        UserEntity sender = webService.userLogin();
        Message message = messageRepo.findById(messageId)
                .orElseThrow(()-> new IllegalArgumentException("No exist message!!"));
        if (!message.getSender().getId().equals(sender.getId()))
            throw new IllegalArgumentException("No exist message");
        else if (message.isConfirm()) throw new IllegalArgumentException("Cannot delete this message");
        messageRepo.delete(message);
    }

    // Receiver
    public MessageDto confirm(Long messageId){
        UserEntity receiver= webService.userLogin();
        Message message = messageRepo.findById(messageId)
                .orElseThrow(()-> new IllegalArgumentException("No exist message!!"));
        if (!message.getReceiver().getId().equals(receiver.getId()))
            throw new IllegalArgumentException("No exist message");
        message.setConfirm(true);
        return MessageDto.dto(messageRepo.save(message));
    }

//    public MessageDto replyMessage(Long messageId, Long replyId){
//        if (messageId.equals(replyId))
//            throw new IllegalArgumentException("Cannot reply same message");
//        Message reply = messageRepo.findById(messageId)
//                .orElseThrow(()-> new IllegalArgumentException("No exist message"));
//
//    }

    public List<MessageDto> chatbot(Long receiverId){
        List<MessageDto> list = new ArrayList<>();
        UserEntity sender = webService.userLogin();
        UserEntity receiver = webService.userById(receiverId);

        List<Message> messages = messageRepo.chatMessages(sender, receiver);
        for (Message message: messages){
            list.add(MessageDto.dto(message));
        }
        return list;
    }

    public MessageDto oneMes(Long id){
        Message message = messageRepo.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("No exist message")
        );
        return MessageDto.dto(message);
    }

}
