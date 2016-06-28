-- Table definitions

create table user(
    username varchar(32) primary key,
    password varchar(100),
    email varchar(100),
    is_admin char(1));

-- The admin account
insert into user value('root', '12345678', 'admin@bookstore.com', 'Y');

create table book(
    id int auto_increment primary key,
    ISBN varchar(32),
    name varchar(128),
    price int);

create table `order`(
    id int auto_increment primary key,
    username varchar(32),
    foreign key (username) references user(username) on delete cascade
);

create table order_item(
    order_id int,
    book_id int,
    num int,
    foreign key (order_id) references `order`(id) on delete cascade,
    foreign key (book_id) references book(id));

-- Initial data for test

-- normal user
insert into user value('user', 'test', 'test@bookstore.com', 'N');

-- some book
insert into book(ISBN, name, price) value('978-1', '精通php', 50);
insert into book(ISBN, name, price) value('978-2', '深入理解java', 500);
