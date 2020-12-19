package com.messenger.messenger.service;

import com.messenger.messenger.components.JwtAuthenticationController;
import com.messenger.messenger.config.Login;
import com.messenger.messenger.dao.Dao;
import com.messenger.messenger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Service for user
@Service
public class UserService {
    private final Dao dao;

    @Autowired
    public JwtAuthenticationController jwtAuthenticationController;

    @Autowired
    public UserService(@Qualifier("postgres") Dao dao) {
        this.dao = dao;
    }

    public int addUser(User user) {
        return dao.createUser(user);
    }

    public List<User> getAllUsers() {
        return dao.selectAllUsers();
    }

    public int updateUserById(UUID id, User user)
    {
        return dao.updateUserById(id, user);
    }

    public String logIn(Login login) { return dao.logIn(login); }

    public Boolean validateToken(String token) { return jwtAuthenticationController.validateToken(token, getAllUsers()); }

    public List<User> getAllFiends(String token){ return dao.getAllFriends(token);}

    public List<User> searchUsers(String searchLine){ return dao.searchUsers(searchLine);}

    public void addFriend(String token, UUID id){ dao.addFriend(token, id); };

    public String getIdByToken(String token){ return jwtAuthenticationController.getCurrentUserId(token,getAllUsers()).toString(); }
}
