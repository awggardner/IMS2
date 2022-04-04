drop schema ims;

CREATE SCHEMA IF NOT EXISTS `ims`;

USE `ims` ;

CREATE TABLE IF NOT EXISTS customers (
    id INT(11) NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(40) DEFAULT NULL,
    surname VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS items (
    item_id INT(11) NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(40) DEFAULT NULL,
    item_description VARCHAR(200) DEFAULT NULL,
    item_price FLOAT DEFAULT NULL,
    PRIMARY KEY (item_id)
);

CREATE TABLE IF NOT EXISTS orders (
	order_id INT(11) NOT NULL AUTO_INCREMENT,
    customer_id INT(11) NOT NULL,
    PRIMARY KEY(order_id),
    FOREIGN KEY(customer_id) REFERENCES customers(id)
    );
    
CREATE TABLE IF NOT EXISTS order_items (
	order_id INT (11) NOT NULL,
    item_id INT (11) NOT NULL,
    
    PRIMARY KEY(order_id, item_id),
    FOREIGN KEY(order_id) REFERENCES orders (order_id),
    FOREIGN KEY(item_id) REFERENCES items (item_id)
    );
    
show tables;

SELECT * FROM items;
SELECT * FROM orders;
SELECT * FROM customers;

INSERT INTO orders 

SELECT o.order_id AS order_id, c.id AS customer_id, c.first_name, c.surname,
i.Item_id AS item_id, i.item_name, i.item_price
FROM orders o 
JOIN customers c ON o.customer_id = c.id 
JOIN order_items oi ON o.order_id = oi.order_id
JOIN items i ON oi.item_id = i.item_id
ORDER BY order_id;