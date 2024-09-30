package com.example.my_project2.message;

import com.example.my_project2.message.dto.MessageDto;
import com.example.my_project2.message.repo.MessageRepo;
import com.example.my_project2.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("messages")
public class MessageController {
    private final MessageService messageService;
    @PostMapping("sender")
    public MessageDto create(
            @RequestBody
            MessageDto dto
    ){
        return messageService.create(dto);
    }

    @PutMapping("sender/{id}")
    public MessageDto edit(
            @PathVariable("id")
            Long messageId,
            @RequestBody
            MessageDto dto
    ){
        return messageService.edit(dto, messageId);
    }
    @PutMapping("remove/{id}")
    public MessageDto remove(@PathVariable("id") Long messageId){
        return messageService.remove(messageId);
    }

    @DeleteMapping("sender/{id}")
    public void delete(@PathVariable("id") Long messageId){
        messageService.delete(messageId);
    }

    @PutMapping("receiver/{id}")
    public MessageDto confirm(@PathVariable("id") Long messageId){
        return messageService.confirm(messageId);
    }

    @GetMapping("chat/{receiverId}")
    public List<MessageDto> chatbot(
            @PathVariable("receiverId")
            Long receiverId
    ){
        return messageService.chatbot(receiverId);
    }
    @GetMapping("{id}")
    public MessageDto oneMes(@PathVariable("id") Long messageId){
        return messageService.oneMes(messageId);
    }
}
