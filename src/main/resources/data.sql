DROP TABLE IF EXISTS buyer_book;
DROP TABLE IF EXISTS buyer;
DROP TABLE IF EXISTS book;
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

CREATE TABLE buyer_book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    buyer_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    CONSTRAINT fk_buyer_book_buyer FOREIGN KEY (buyer_id) REFERENCES buyer(id),
    CONSTRAINT fk_buyer_book_book FOREIGN KEY (book_id) REFERENCES book(id)
);

-- Rolları əlavə edirik
INSERT INTO roles (name, seller, buyer) VALUES
('ROLE_ADD_BOOK', 1, 0),
('ROLE_DELETE_BOOK', 1, 0),
('ROLE_UPDATE_BOOK', 1, 0),
('ROLE_SEARCH_BOOK', 1, 1);

-- Seller-ləri əlavə edirik
INSERT INTO seller(name, phone, password, balance) VALUES
('Hegel', '0551234567', '1234', 0),
('Spinosa', '0518913254', '12345', 0);

-- Kitabları əlavə edirik (hər seller üçün)
INSERT INTO book(name, author, amount, stock, seller_id) VALUES
('Java Basics', 'Alice', 78, 208, 1),
('Spring Intro', 'Bob', 52, 241, 1),
('Hibernate Guide', 'Charlie', 91, 282, 1),
('Algorithms 101', 'David', 33, 293, 1),
('Data Structures', 'Eve', 120, 124, 1),
('Microservices', 'Frank', 67, 259, 1),
('REST APIs', 'Grace', 85, 426, 1),
('Docker Essentials', 'Hank', 44, 827, 1),
('Kubernetes Guide', 'Ivy', 98, 288, 1),
('Design Patterns', 'Jack', 72, 299, 1),
('Java Basics', 'Alice', 141, 310, 2),
('Spring Intro', 'Bob', 67, 311, 2),
('Hibernate Guide', 'Charlie', 122, 326, 2),
('Algorithms 101', 'David', 58, 343, 2),
('Data Structures', 'Eve', 134, 534, 2),
('Microservices', 'Frank', 95, 635, 2),
('REST APIs', 'Grace', 110, 936, 2),
('Docker Essentials', 'Hank', 73, 378, 2),
('Kubernetes Guide', 'Ivy', 149, 389, 2),
('Design Patterns', 'Jack', 88, 390, 2);

-- Buyer-ləri əlavə edirik
INSERT INTO buyer(name, phone, email, password) VALUES
('Buyer1', '0501111111', 'buyer1@example.com', '1234'),
('Buyer2', '0502222222', 'buyer2@example.com', '12345'),
('Buyer3', '0503333333', 'buyer3@example.com', '123456'),
('Buyer4', '0504444444', 'buyer4@example.com', '1234567'),
('Buyer5', '0501111112', 'buyer5@example.com', '12345678');
