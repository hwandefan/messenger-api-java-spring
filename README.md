# messenger-api-java-spring
Messenger - Server Api on Java Spring Boot <br />
<br />
Database: PostgresSQL on Docker. All migrations you can find at files. <br />
<br />
Commands for ServerApi: <br />
/user - (@GET) Get all users <br />
/user/signup - (@POST) Register user <br />
/user/signin - (@POST) Login user, get token <br />
/user/get_friends - (@GET) Get friends list by token <br />
/user/add_friend/?idFriend= - (@GET) Add friend by id <br />
/user/search/?search= - (@GET) Searh Users by param (login or name)<br />
/chat/send/?whom=&message= - (@POST) Send message from token user to user by id in paramcolumn <br />
/chat/getMessages/?whom= - (@GET) Get all messages between token user and param user <br />
