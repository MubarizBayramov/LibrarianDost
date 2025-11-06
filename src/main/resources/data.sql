
CREATE TABLE seller (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL
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
    balance DOUBLE DEFAULT 1000
);

CREATE TABLE buyer_book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    buyer_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_buyer FOREIGN KEY (buyer_id) REFERENCES buyer(id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book(id)
);

INSERT INTO seller(id, name, phone) VALUES (1, 'Hegel', '0551234567');
INSERT INTO seller(id, name, phone) VALUES (2, 'Spinosa', '0518913254');

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


INSERT INTO buyer(name, phone, email) VALUES ('Buyer1', '0501111111', 'buyer1@example.com');
INSERT INTO buyer(name, phone, email) VALUES ('Buyer2', '0502222222', 'buyer2@example.com');
INSERT INTO buyer(name, phone, email) VALUES ('Buyer3', '0503333333', 'buyer3@example.com');
INSERT INTO buyer(name, phone, email) VALUES ('Buyer4', '0504444444', 'buyer4@example.com');
INSERT INTO buyer(name, phone, email) VALUES ('Buyer5', '0501111112', 'buyer5@example.com');


UPDATE buyer SET balance = 10000 WHERE balance IS NULL;
