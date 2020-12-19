CREATE TABLE "userFriends"(
    id UUID NOT NULL PRIMARY KEY,
    idUser UUID NOT NULL REFERENCES "user" (id),
    idFriend UUID NOT NULL REFERENCES "user" (id)
);