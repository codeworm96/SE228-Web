-- Table definitions

create table user(
    username varchar(32) primary key,
    password varchar(100),
    is_admin char(1));

-- The admin account
insert into user values('root', '12345678', 'Y');

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
    status int,
    date DATE,
    foreign key (username) references user(username) on delete cascade);

create table order_item(
    order_id int,
    book_id int,
    num int,
    price int,
    foreign key (order_id) references `order`(id) on delete cascade,
    foreign key (book_id) references book(id));

-- Procedures
DELIMITER //

CREATE PROCEDURE book_stat()
    BEGIN
        SELECT name, sum(num * order_item.price) AS price
        FROM (book JOIN order_item on book.id=book_id) JOIN `order` on order_id=`order`.id
        WHERE status=1
        GROUP BY name;
    END //

CREATE PROCEDURE category_stat()
    BEGIN
        SELECT category, sum(num * order_item.price) AS price
        FROM (book JOIN order_item on book.id=book_id) JOIN `order` on order_id=`order`.id
        WHERE status=1
        GROUP BY category;
    END //

CREATE PROCEDURE day_stat(in startDate DATE, in endDate DATE)
    BEGIN
        SELECT date_format(date, "%Y-%m-%d"), sum(price) as price
        FROM `order`
        WHERE status=1 and date BETWEEN startDate AND endDate
        GROUP BY date
        ORDER BY date;
    END //

CREATE PROCEDURE month_stat(in startDate DATE, in endDate DATE)
    BEGIN
        SELECT date_format(date, "%Y-%m"), sum(price) as price
        FROM `order`
        WHERE status=1 and date BETWEEN startDate AND endDate
        GROUP BY extract(YEAR_MONTH FROM date)
        ORDER BY date;
    END //

CREATE PROCEDURE year_stat(in startDate DATE, in endDate DATE)
    BEGIN
        SELECT YEAR(date), sum(price) as price
        FROM `order`
        WHERE status=1 and date BETWEEN startDate AND endDate
        GROUP BY YEAR(date)
        ORDER BY YEAR(date);
    END //

DELIMITER ;