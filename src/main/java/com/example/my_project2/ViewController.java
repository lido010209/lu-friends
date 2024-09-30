package com.example.my_project2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lu")
public class ViewController {
    @GetMapping("login")
    public String login(){
        return "user/login.html";
    }
    @GetMapping("friend")
    public String friend(){
        return "message/list-friend.html";
    }
    @GetMapping("friend/{id}")
    public String chat(){
        return "message/message-base.html";
    }
}
