INSERT INTO CATEGORY(NAME) VALUES ('Miješalice');
INSERT INTO CATEGORY(NAME) VALUES ('Tuševi');
INSERT INTO CATEGORY(NAME) VALUES ('Bidei');

INSERT INTO ITEM(NAME, DESCRIPTION, PRICE, IMAGE_NAME, ACTIVE, AMOUNT, CATEGORY_ID) VALUES ('Miješalica za sudoper Voxort Kaya Silver', 'Boja: crna, D: 14,9 cm, V:14,4 cm', 29.99, NULL, true, 25, 1);
INSERT INTO ITEM(NAME, DESCRIPTION, PRICE, IMAGE_NAME, ACTIVE, AMOUNT, CATEGORY_ID) VALUES ('Miješalica podžbukna za umivaonik Voxort Perla', 'Boja: crna, D: 18,3 cm', 19.99, NULL, true, 15, 1);
INSERT INTO ITEM(NAME, DESCRIPTION, PRICE, IMAGE_NAME, ACTIVE, AMOUNT, CATEGORY_ID) VALUES ('Miješalica podžbukna za umivaonik Voxort Tea Black', 'Boja: crna, D: 18,1 cm, V:38,2 cm', 24.99, NULL, true, 15, 1);
INSERT INTO ITEM(NAME, DESCRIPTION, PRICE, IMAGE_NAME, ACTIVE, AMOUNT, CATEGORY_ID) VALUES ('Miješalica za umivaonik potisna Voxort, krom', 'Boja: crna, dužina izljeva: 12,5 cm, visina izljeva: 10,9 cm', 34.99, NULL, true, 0, 1);
INSERT INTO ITEM(NAME, DESCRIPTION, PRICE, IMAGE_NAME, ACTIVE, AMOUNT, CATEGORY_ID) VALUES ('Miješalica za kadu Voxort Iris, krom/crna', 'Boja: crna, dužina izljeva: 21,67 cm, visina izljeva: 24,84 cm', 49.99, NULL, true, 5, 1);
INSERT INTO ITEM(NAME, DESCRIPTION, PRICE, IMAGE_NAME, ACTIVE, AMOUNT, CATEGORY_ID) VALUES ('Miješalica za tuš kadu Voxort Iris, krom/crna', 'Boja: crna, D: 12,8 cm, visina izljeva: 24,84 cm, promjer 1 cm', 84.99, NULL, true, 0, 1);

INSERT INTO users(username, FIRST_NAME, LAST_NAME, password) VALUES ('leon.kruslin@waterworks.hr', 'Leon', 'Krušlin', '$2a$12$tmc8NWNsAMpBxg0N7Kl4COnFi7IQ1QZ5Yxw.iXJx6OW00XjUHhxt.');
INSERT INTO users(username, FIRST_NAME, LAST_NAME, password) VALUES ('pero.peric@mail.hr', 'Pero', 'Perić', '$2a$12$tmc8NWNsAMpBxg0N7Kl4COnFi7IQ1QZ5Yxw.iXJx6OW00XjUHhxt.');

INSERT INTO authorities(username, authority) VALUES('leon.kruslin@waterworks.hr', 'ADMIN');
INSERT INTO authorities(username, authority) VALUES('pero.peric@mail.hr', 'USER');