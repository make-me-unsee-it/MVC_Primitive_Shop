create table goods
(
    id      int PRIMARY KEY AUTO_INCREMENT,
    title   VARCHAR(50),
    price   DOUBLE
);

insert into goods (title, price)
values ('iphone 10', 499.9);
insert into goods (title, price)
values ('samsung galaxy c5', 300.0);
insert into goods (title, price)
values ('google pixel 4', 250.0);
insert into goods (title, price)
values ('nokia 1100', 15.0);

create table users
(
    id          int PRIMARY KEY AUTO_INCREMENT,
    userName    VARCHAR(50),
    password    VARCHAR(255)
);
insert into users (userName, password)
values ('Michail', '$2a$04$EWCL1lLnDbksuAisA7OlMursz4XyycGvtJ/ZVI9NthbkHPkMEJRk.');
insert into users (userName, password)
values ('Andrey', '$2a$04$INs191JI5GhQN1f54g4dnea0CaA/DDfpqVLfz5eHyr2GoUa/6gL1W');
insert into users (userName, password)
values ('Nikolay', '$2a$04$whXhhdottMkDd.Mdem2/6.jeEllaeKl8/tv4Mi2pXd536hBZwRfXO');
insert into users (userName, password)
values ('Roma', '$2a$04$wYfN0ckKPzxOBwiMKLUoNe.RFe6iEo9xS0VsyQ36SZzdmvR9p9zc.');

create table orders
(
    id          int PRIMARY KEY AUTO_INCREMENT,
    userId      int,
    totalPrice  DOUBLE,
    FOREIGN KEY (userId) REFERENCES users(id)
);

create table ordergoods
(
    id          int PRIMARY KEY AUTO_INCREMENT,
    orderId     int,
    goodId      int,
    FOREIGN KEY (orderId) REFERENCES orders(id),
    FOREIGN KEY (goodId) REFERENCES goods(id)
);