package com.messenger.messenger.components;

import com.messenger.messenger.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550158165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5*60*60;
    @Value("${jwt.secret}")
    private String secret;

    //Getting user from token
    public String getUserNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    //get Expiration Data from token (Standard - 1 hour)
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //Get One claim from token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //Get all claims
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //Check token time
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //Return generated token
    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user.getLogin());
    }

    //generate token
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token, List<User>DB){
        try {
            if (token == null) return false;
            String BearerToken = token.replace("Bearer", "");
            if (BearerToken.equals("") || BearerToken.equals(" ")) return false;
            return (checkInDatabase(BearerToken, DB) && !isTokenExpired(BearerToken));
        } catch (Exception e){return false;}
    }

    //Exists token user in DB
    private Boolean checkInDatabase(String token, List<User> DB){
        final String username = getUserNameFromToken(token);
        List<User> OneUser = DB.stream().filter((x)->x.getLogin().equals(username)).collect(Collectors.toList());
        return OneUser.size() == 1;
    }

    public String getCurrentUserByToken(String token){
        return getUserNameFromToken(token);
    }

    public UUID getCurrentUserId(String token, List<User> DB) {
        List<User> OneUser = DB.stream().filter((x)->x.getLogin().equals(getUserNameFromToken(token))).collect(Collectors.toList());
        if(OneUser.size() == 1) return  OneUser.get(0).getId();
        return null;
    }
}
