-- Table definitions

create table user(
    username varchar(32) primary key,
    password varchar(100),
    email varchar(100),
    is_admin char(1));

-- The admin account
insert into user values('root', '12345678', 'admin@bookstore.com', 'Y');

create table book(
    id int auto_increment primary key,
    ISBN varchar(32),
    name varchar(128),
    category varchar(128),
    price int);

create table `order`(
    id int auto_increment primary key,
    username varchar(32),
    price int,
    status varchar(32),
    date DATE,
    foreign key (username) references user(username) on delete cascade
);

create table order_item(
    order_id int,
    book_id int,
    num int,
    price int,
    foreign key (order_id) references `order`(id) on delete cascade,
    foreign key (book_id) references book(id));

-- Initial data for test

-- normal user
insert into user values('user', 'test', 'test@bookstore.com', 'N');

-- some book
insert into book(ISBN, name, category, price) values('978-1', 'Computer Systems', 'computer science', 600);
insert into book(ISBN, name, category, price) values('978-2', 'Database', 'computer science', 50);
insert into book(ISBN, name, category, price) values('978-3', 'The man who changed china', 'biography', 1000);
insert into book(ISBN, name, category, price) values('978-4', 'The man in the high castle', 'science fiction', 233);

-- some order
insert into `order`(username, price, status, date) values('root', 60000, 'paid', '2015-12-31');
insert into `order`(username, price, status, date) values('root', 5000, 'paid', '2016-01-01');
insert into `order`(username, price, status, date) values('root', 100000, 'paid', '2016-01-02');
insert into `order`(username, price, status, date) values('root', 23300, 'paid', '2016-01-03');

insert into order_item(order_id, book_id, num, price) values(1, 1, 100, 600);
insert into order_item(order_id, book_id, num, price) values(2, 2, 100, 50);
insert into order_item(order_id, book_id, num, price) values(3, 3, 100, 1000);
insert into order_item(order_id, book_id, num, price) values(4, 4, 100, 233);
