package com.messenger.messenger.components;

import com.messenger.messenger.components.JwtTokenUtil;
import com.messenger.messenger.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Component
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //Create token
    public String createAuthentificationToken(User user){
        final String token = jwtTokenUtil.generateToken(user);
        return  token;
    }

    //Validating
    public Boolean validateToken(String token, List<User> list) {
        if(token == null) return false;
        return jwtTokenUtil.validateToken(token, list);
    }

    public String getCurrentUserByToken(String token){
        return jwtTokenUtil.getCurrentUserByToken(token);
    }
    public UUID getCurrentUserId(String token, List<User> DB) { return jwtTokenUtil.getCurrentUserId(token.replace("Bearer", ""), DB);}
}
