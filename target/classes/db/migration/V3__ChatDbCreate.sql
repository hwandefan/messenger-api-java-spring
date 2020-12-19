CREATE TABLE "ChatDB"(
    id UUID NOT NULL PRIMARY KEY,
    senderId UUID NOT NULL REFERENCES "user" (id),
    recieverId UUID NOT NULL REFERENCES "user" (id),
    message VARCHAR (1024) NOT NULL,
    encodeStep INTEGER NOT NULL
);