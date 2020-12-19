package com.messenger.messenger.service;

import com.messenger.messenger.dao.Dao;
import com.messenger.messenger.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {
    private final Dao dao;

    @Autowired
    public ChatService(Dao dao) {
        this.dao = dao;
    }

    public void sendMessage(String token, UUID idFriend, String message){
        dao.sendMessage(token,idFriend,message);
    }
    public List<Chat> getAllMessages(String token, UUID idFriend){
        return dao.getAllMessages(token,idFriend);
    }
}
