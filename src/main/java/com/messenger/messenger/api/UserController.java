package com.messenger.messenger.api;

import com.messenger.messenger.config.Login;
import com.messenger.messenger.model.User;
import com.messenger.messenger.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Call - Get All users function
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //Call - Register function
    @PostMapping("signup")
    public void singup(@RequestBody User user){
        userService.addUser(user);
    }

    //Call - login function and gets token
    @PostMapping("signin")
    public String signin(@RequestBody Login loginData)
    {
        return  userService.logIn(loginData);
    }

    //Call - get all user's friends by token
    @GetMapping("get_friends")
    public List<User> getFriends(@RequestHeader(value = "Authorization",required = false) String token) {
        if(!(userService.validateToken(token))) return null;
        return userService.getAllFiends(token);
    }

    //Add friend to user account
    @GetMapping("add_friend")
    public void addFriend(@RequestHeader(value = "Authorization",required = false) String token,
    @RequestParam(value = "idFriend", required = false, defaultValue = "") String id){
        if((!(userService.validateToken(token))) || (id.equals(""))) return;
        try {
            userService.addFriend(token, UUID.fromString(id));
        } catch (Exception e) {return;}
    }

    //Route With params
    @GetMapping("search")
    public List<User> searchFriends(@RequestHeader(value = "Authorization",required = false) String token,
                                    @RequestParam(value = "search", required = false, defaultValue = "") String searchLine){
        if(!(userService.validateToken(token))) return null;
        return userService.searchUsers(searchLine);
        }

    @GetMapping("user_id_by_token")
    public String userIdByToken(@RequestHeader(value = "Authorization",required = false) String token){
        if(!(userService.validateToken(token))) return null;
        try {
            return userService.getIdByToken(token);
        } catch (Exception e){return null;}
    }
}
