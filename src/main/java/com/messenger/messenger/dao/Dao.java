package com.messenger.messenger.dao;

import com.messenger.messenger.config.Login;
import com.messenger.messenger.model.Chat;
import com.messenger.messenger.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Dao {
    int createUser(UUID id, User user);
    default int createUser(User user){
      UUID id = UUID.randomUUID();
      return createUser(id, user);
    }

    List<User> selectAllUsers();
    int updateUserById(UUID id, User user);
    String logIn(Login login);
    List<User> getAllFriends(String token);
    List<User> searchUsers(String searchLine);
    void addFriend(String token, UUID id);
    void sendMessage(String token, UUID idFriend,String message);
    List<Chat> getAllMessages(String token, UUID idFriend);
}
