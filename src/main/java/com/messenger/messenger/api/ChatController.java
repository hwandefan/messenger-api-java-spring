package com.messenger.messenger.api;

import com.messenger.messenger.model.Chat;
import com.messenger.messenger.service.ChatService;
import com.messenger.messenger.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("chat")
@RestController
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    //Send message from user with token to another user by id
    @PostMapping("send")
    public void sendMessage(@RequestHeader(value = "Authorization",required = false) String token,
                            @RequestParam(value = "message", required = true, defaultValue = "") String message,
                            @RequestParam(value = "whom", required = true) UUID friend){
        if(!(userService.validateToken(token))) return;
        try{
            chatService.sendMessage(token,friend,message);
        } catch (Exception e) {
            return;
        }

    }

    //Get all messages between token user and user by id
    @GetMapping("getMessages")
    public List<Chat> getMessage(@RequestHeader(value = "Authorization",required = false) String token,
                                 @RequestParam(value = "whom", required = true) UUID friend){
        if(!(userService.validateToken(token))) return null;
        try{
            return chatService.getAllMessages(token,friend);
        }catch (Exception e){return null;}
    }
}
