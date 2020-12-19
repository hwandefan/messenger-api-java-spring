package com.messenger.messenger.dao;

import com.messenger.messenger.components.JwtAuthenticationController;
import com.messenger.messenger.config.Login;
import com.messenger.messenger.model.Chat;
import com.messenger.messenger.model.FriendLink;
import com.messenger.messenger.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

//Realization of DAO functions
@Repository("postgres")
public class DataAccessService implements Dao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public JwtAuthenticationController jwtAuthenticationController;

    @Override
    public int createUser(UUID id, User user) {
        final String sql = "INSERT INTO \"user\"(id,name, login, password) VALUES (?,?,?,?);";
        String pw_hash = new BCryptPasswordEncoder().encode(user.getPassword());
        jdbcTemplate.update(sql,id,user.getName(),user.getLogin(),pw_hash);
        return 1;
    }

    @Override
    public List<User> selectAllUsers()  {
        final String sql = "SELECT * FROM \"user\";";
        List<User> users = jdbcTemplate.query(sql, (resultSet,i) -> new User(UUID.fromString(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getString("login"),
                resultSet.getString("password")));
        return users;
    }

    @Override
    public int updateUserById(UUID id, User user) {
        return 0;
    }

    @Override
    public String logIn(Login login){
        final String sql = "SELECT * FROM \"user\" WHERE login='"+login.getLogin()+"'";
        List<User> logInUser = jdbcTemplate.query(sql,(resultSet,i) -> new User(UUID.fromString(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getString("login"),
                resultSet.getString("password")));
        if(logInUser.size()==1 && new BCryptPasswordEncoder().matches(login.getPassword(),logInUser.get(0).getPassword()))
        {
            return jwtAuthenticationController.createAuthentificationToken(logInUser.get(0));
        } else return "Error";
    }

    @Override
    public List<User> searchUsers(String searchLine/*name/login*/){
        final String sql = "SELECT * FROM \"user\" WHERE login LIKE '%"+searchLine+"%' OR name LIKE '%"+searchLine+"%'";
        return jdbcTemplate.query(sql,(resultSet,i)-> new User(UUID.fromString(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getString("login"),
                resultSet.getString("password")));
    }

    @Override
    public List<User> getAllFriends(String token) {
        List<User> allUsers = selectAllUsers();
        final String sql = "SELECT id FROM \"user\" WHERE login='"+jwtAuthenticationController.getCurrentUserByToken(token.replace("Bearer",""))+"';";
        List<UUID> idCurrentUser = jdbcTemplate.query(sql, (resultSet,i) -> UUID.fromString(resultSet.getString("id")));
        if(idCurrentUser.size() != 1) return null;
        final String sqlListQuerry = "SELECT * FROM \"userFriends\" WHERE iduser='"+idCurrentUser.get(0)+"';";
        List<FriendLink> friendLinks = jdbcTemplate.query(sqlListQuerry,(resultSet, i) -> new FriendLink(UUID.fromString(resultSet.getString("id")),
                UUID.fromString(resultSet.getString("iduser")),
                UUID.fromString(resultSet.getString("idfriend"))));

        return allUsers.stream().filter((x)->{
            for(FriendLink f: friendLinks) {
                return x.getId().equals(f.getIdFriend());
            }
            return false;
        }).collect(Collectors.toList());
    }

    public void addFriend(String token, UUID friendId) {
        UUID currentUserId = jwtAuthenticationController.getCurrentUserId(token.replace("Bearer",""), selectAllUsers());
        if(currentUserId == null) return;
        final String sql = "INSERT INTO \"userFriends\"(id,iduser,idfriend) VALUES (?,?,?);";
        jdbcTemplate.update(sql,UUID.randomUUID(), currentUserId,friendId);
        jdbcTemplate.update(sql,UUID.randomUUID(), friendId,currentUserId);
    }

    public void sendMessage(String token, UUID idFriend,String message){
        UUID currentUserId = jwtAuthenticationController.getCurrentUserId(token.replace("Bearer", ""), selectAllUsers());
        if(currentUserId == null) return;
        final String sql = "INSERT INTO \"ChatDB\" (id,senderid,recieverid,message,encodestep) VALUES (?,?,?,?,?);";
        jdbcTemplate.update(sql,UUID.randomUUID(),currentUserId,idFriend,message,0);
    }
    public List<Chat> getAllMessages(String token, UUID idFriend){
        UUID currentUserId = jwtAuthenticationController.getCurrentUserId(token.replace("Bearer",""), selectAllUsers());
        if(currentUserId == null) return null;
        final String sql ="SELECT * FROM \"ChatDB\";";
        List<Chat> allMessages = jdbcTemplate.query(sql,(resultSet,i) -> new Chat(UUID.fromString(resultSet.getString("id")),
                UUID.fromString(resultSet.getString("senderid")),
                UUID.fromString(resultSet.getString("recieverid")),
                Integer.parseInt(resultSet.getString("encodestep")),
                resultSet.getString("message")));
        return allMessages.stream().filter((x)-> (x.getSenderId().equals(currentUserId) && x.getRecieverId().equals(idFriend))
                || (x.getSenderId().equals(idFriend) && x.getRecieverId().equals(currentUserId))).collect(Collectors.toList());

    }
}
