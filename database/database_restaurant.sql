DROP database if exists flying_dutchman;
CREATE database flying_dutchman;
USE flying_dutchman;
CREATE TABLE products
(
    product_id   int primary key auto_increment,
    product_name varchar(100)  not null,
    image_path   varchar(100) default 'src/main/webapp/images/logo.jpg',
    cost         decimal(5, 2) not null,
    description  text(2000),
    active       boolean default false
);
CREATE TABLE users
(
    username     varchar(50) primary key not null unique ,
    password     varchar(50)             not null,
    first_name   varchar(50)             not null,
    last_name    varchar(50) default 'not filled',
    phone_number varchar(22)             not null unique,
    e_mail       varchar(50)             not null unique ,
    user_role    int     default 3,
    active       boolean default true
);
CREATE TABLE orders
(
    order_id   int primary key auto_increment,
    username   varchar(50)   not null,
    order_date datetime    default now(),
    order_cost decimal(6, 2) not null,
    status     varchar(10) default 'NEW',
    FOREIGN KEY (username) REFERENCES users (username)
);
CREATE TABLE orders_details
(

    order_id           int,
    product_id         int,
    number_of_products int not null,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id),
    FOREIGN KEY (product_id) REFERENCES products (product_id)
);
INSERT INTO users (username, password, first_name, last_name, phone_number, e_mail, user_role)
values ('admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', -- password=admin
        'Bob', 'Morley', '+080158742115241', 'morley@yahoo.com', 1),
       ('manager', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', -- password=1234
        'Aliaksei', 'Halkin', '+375292597148', 'pochtax3@gmail.com', 2),
       ('cook', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', -- password=1234
        'Jessika', 'Smith', '+375292555148', 'poc3@gmail.com', 4);
INSERT INTO users (username, password, first_name, last_name, phone_number, e_mail)
values ('Boby', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', -- password=1234
        'Bob', 'Jakobson',  '+375291122333', 'bob2@gmail.com'),
       ('maroon', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', -- password=1234
        'Tom', 'Hornet', '+375252584568', 'ff@tut.by'),
       ('kitty17', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', -- password=1234
        'Ann', 'Cruise', '+375291221333', 'ann@gmail.com'),
       ('boris', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', -- password=1234
        'Boris', 'Swift', '+375297722333', 'bob@gmail.com');



INSERT into orders (username, order_cost)
values ('maroon', 45.23),
       ('Boby', 52.23),
       ('kitty17', 58.22),
       ('boris', 34.52);

INSERT into products (product_name, image_path, cost, description)
values ('Octopus tentacles', 'images/img1.jpg', 10.14,
        'Freshening octopus tentacles with lemon sauce,100g'),
       ('Tiger prawns grilled', 'images/img2.jpg', 12.99,
        'Crazy Tiger prawns and lime! 100g You will like it!'),
       ('Tuna grilled', 'images/img3.jpg', 23.99,
        'Freshening Tuna grilled with classic cream sauce,100g'),
       ('Squid grilled', 'images/img4.jpg', 10.99,
        'Freshening Squid grilled.,100g Now with pepper'),
       ('Chilean mussels in wine sauce', 'images/img5.jpg', 8.22,
        'Freshening Chilean mussels in wine sauce.,100g Taste new mussels and it will become your favourite!'),
       ('Giant mussels baked in dough', 'images/img6.jpg', 8.11,
        'Freshening Giant mussels baked, 100g'),
       ('Cod fillet in cream sauce', 'images/img7.jpg', 7.99,
        'Nutritious protein bar balanced especially for adults,100g'),
       ('Cappuccino coffee', 'images/img8.jpg', 3.00,
        'Cappuccino coffee for professional programmer'),
       ('Americano coffee', 'images/img9.jpg', 3.00,
        'Americano coffee  for you'),
       ('Caesar salad', 'images/img12.jpg', 5.59,
        'Multivitamin complex for all people,100g ,100g'),
       ('Mashed potatoes', 'images/img13.jpg', 27.30,
        ' Multivitamin potatoes for all people,100g'),
       ('Basmati rice', 'images/img14.jpg', 6.20,
        'Basmati rice for all,100g'),
       ('Green tea', 'images/img10.jpg', 2.00,
        'Classical Chinese tea'),
       ('Black tea', 'images/img11.jpg', 2.00,
        'Classical Black tea'),
       ('Soup Tam-Yam', 'images/img15.jpg', 6.50,
        'Soup with tuna and some vegetables'),
       ('Beer Krušovice', 'images/img16.jpg', 6.50,
        'original bear 0.5l');


INSERT into orders_details
values (1, 1, 2),
       (1, 2, 1),
       (1, 8, 1),
       (2, 3, 5),
       (2, 7, 2),
       (2, 4, 3),
       (3, 8, 2),
       (4, 9, 1),
       (4, 5, 1),
       (4, 1, 1);