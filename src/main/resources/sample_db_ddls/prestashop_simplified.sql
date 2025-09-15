DROP DATABASE IF EXISTS prestashop_simplified;
CREATE DATABASE IF NOT EXISTS prestashop_simplified;
USE prestashop_simplified;

CREATE TABLE ps_cart
(
    id_cart             INT AUTO_INCREMENT PRIMARY KEY,
    id_customer         INT,
    id_address_delivery INT,
    id_address_invoice  INT,
    id_currency         INT,
    secure_key          VARCHAR(255),
    date_add            DATETIME,
    date_upd            DATETIME
) ENGINE = InnoDB;

CREATE TABLE ps_cart_product
(
    id_cart              INT NOT NULL,
    id_product           INT NOT NULL,
    id_product_attribute INT DEFAULT 0,
    quantity             INT,
    PRIMARY KEY (id_cart, id_product, id_product_attribute),
    FOREIGN KEY (id_cart) REFERENCES ps_cart (id_cart)
) ENGINE = InnoDB;

CREATE TABLE ps_cart_rule
(
    id_cart_rule INT AUTO_INCREMENT PRIMARY KEY,
    description  VARCHAR(255),
    quantity     INT,
    priority     INT
) ENGINE = InnoDB;

CREATE TABLE ps_cart_cart_rule
(
    id_cart      INT NOT NULL,
    id_cart_rule INT NOT NULL,
    PRIMARY KEY (id_cart, id_cart_rule),
    FOREIGN KEY (id_cart) REFERENCES ps_cart (id_cart),
    FOREIGN KEY (id_cart_rule) REFERENCES ps_cart_rule (id_cart_rule)
) ENGINE = InnoDB;
