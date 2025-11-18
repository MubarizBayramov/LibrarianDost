DROP TABLE IF EXISTS buyer_book;
DROP TABLE IF EXISTS buyer;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS seller_roles;
DROP TABLE IF EXISTS seller;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    seller BOOLEAN DEFAULT 0,
    buyer BOOLEAN DEFAULT 0
);

CREATE TABLE seller (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    balance DOUBLE DEFAULT 0
);

CREATE TABLE seller_roles (
    seller_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_seller_roles_seller FOREIGN KEY (seller_id) REFERENCES seller(id),
    CONSTRAINT fk_seller_roles_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    amount DOUBLE NOT NULL,
    stock INT NOT NULL,
    seller_id BIGINT,
    CONSTRAINT fk_book_seller FOREIGN KEY (seller_id) REFERENCES seller(id)
);

CREATE TABLE buyer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
        email VARCHAR(255),
        password VARCHAR(255),
    balance DOUBLE DEFAULT 1000
);

CREATE TABLE buyer_roles (
    buyer_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_buyer_roles_buyer FOREIGN KEY (buyer_id) REFERENCES buyer(id),
    CONSTRAINT fk_buyer_roles_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE buyer_book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    buyer_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,

    CONSTRAINT fk_buyer_book_buyer FOREIGN KEY (buyer_id) REFERENCES buyer(id),
    CONSTRAINT fk_buyer_book_book FOREIGN KEY (book_id) REFERENCES book(id)
);





INSERT INTO roles (name, seller, buyer) VALUES
('ROLE_ADD_BOOK', 1, 0),
('ROLE_DELETE_BOOK', 1, 0),
('ROLE_UPDATE_BOOK', 1, 0),
('ROLE_SEARCH_BOOK', 1, 1);


INSERT INTO seller(name, phone, password, balance) VALUES ('Hegel', '0551234567', '1234', 0);
INSERT INTO seller(name, phone, password, balance) VALUES ('Spinosa', '0518913254', '12345', 0);


INSERT INTO seller_roles (seller_id, role_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3);



INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Java Basics', 'Alice', 78, 208, 1);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Spring Intro', 'Bob', 52, 241, 1);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Hibernate Guide', 'Charlie', 91, 282, 1);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Algorithms 101', 'David', 33, 293, 1);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Data Structures', 'Eve', 120, 124, 1);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Microservices', 'Frank', 67, 259, 1);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('REST APIs', 'Grace', 85, 426, 1);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Docker Essentials', 'Hank', 44, 827, 1);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Kubernetes Guide', 'Ivy', 98, 288, 1);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Design Patterns', 'Jack', 72, 299, 1);

INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Java Basics', 'Alice', 141, 310, 2);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Spring Intro', 'Bob', 67, 311, 2);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Hibernate Guide', 'Charlie', 122, 326, 2);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Algorithms 101', 'David', 58, 343, 2);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Data Structures', 'Eve', 134, 534, 2);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Microservices', 'Frank', 95, 635, 2);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('REST APIs', 'Grace', 110, 936, 2);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Docker Essentials', 'Hank', 73, 378, 2);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Kubernetes Guide', 'Ivy', 149, 389, 2);
INSERT INTO book(name, author, amount, stock, seller_id) VALUES ('Design Patterns', 'Jack', 88, 390, 2);


INSERT INTO buyer(name, phone, email, password) VALUES
('Buyer1', '0501111111', 'buyer1@example.com', '1234'),
('Buyer2', '0502222222', 'buyer2@example.com', '12345'),
('Buyer3', '0503333333', 'buyer3@example.com', '123456'),
('Buyer4', '0504444444', 'buyer4@example.com', '1234567'),
('Buyer5', '0501111112', 'buyer5@example.com', '12345678');

INSERT INTO buyer_roles (buyer_id, role_id) VALUES
(1, 4),
(2, 4),
(3, 4),
(4, 4),
(5, 4);


UPDATE buyer SET balance = 10000 WHERE balance IS NULL;
