CREATE TABLE CATEGORY(
    ID INT GENERATED ALWAYS AS IDENTITY,
    NAME VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE ITEM (
    ID INT GENERATED ALWAYS AS IDENTITY,
    NAME VARCHAR(50) NOT NULL,
    DESCRIPTION VARCHAR(100) NOT NULL,
    PRICE DECIMAL(5,2) NOT NULL,
    IMAGE_NAME VARCHAR(MAX),
    ACTIVE BOOLEAN,
    AMOUNT INT,
    CATEGORY_ID INT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY(ID)
);

CREATE TABLE users(
                      username VARCHAR(50) NOT NULL,
                             FIRST_NAME VARCHAR(50) NOT NULL,
                             LAST_NAME VARCHAR(50) NOT NULL,
                             password VARCHAR(100) NOT NULL,
                             enabled TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY (username)
);

CREATE TABLE authorities(
                     username VARCHAR(50) NOT NULL,
                     authority VARCHAR(50) NOT NULL,
                     FOREIGN KEY (username) REFERENCES users(username)
);


CREATE TABLE USER_ACCOUNT_LOGIN(
                                   ID INT GENERATED ALWAYS AS IDENTITY,
                                   IP_ADDRESS VARCHAR(50) NOT NULL,
                                   USER_ID VARCHAR(50) NOT NULL,
                                   LOCAL_DATETIME DATETIME NOT NULL,
                                   PRIMARY KEY (ID),
                                   FOREIGN KEY (USER_ID) REFERENCES users(username)
);