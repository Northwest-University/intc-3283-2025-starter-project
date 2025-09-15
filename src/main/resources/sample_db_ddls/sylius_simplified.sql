DROP DATABASE IF EXISTS sylius;
CREATE DATABASE IF NOT EXISTS sylius;
USE sylius;


CREATE TABLE sylius_order
(
    id                    CHAR(36) PRIMARY KEY, -- UUID stored as text
    customer_id           CHAR(36),
    state                 VARCHAR(50),
    currency_code         CHAR(3),
    locale_code           VARCHAR(10),
    checkout_completed_at DATETIME NULL
) ENGINE = InnoDB;

CREATE TABLE sylius_order_item
(
    id                 CHAR(36) PRIMARY KEY,
    order_id           CHAR(36) NOT NULL,
    product_variant_id CHAR(36) NOT NULL,
    quantity           INT,
    unit_price         INT, -- stored in cents
    total              INT,
    FOREIGN KEY (order_id) REFERENCES sylius_order (id)
) ENGINE = InnoDB;

CREATE TABLE sylius_address
(
    id           CHAR(36) PRIMARY KEY,
    first_name   VARCHAR(255),
    last_name    VARCHAR(255),
    street       VARCHAR(255),
    city         VARCHAR(255),
    postcode     VARCHAR(20),
    country_code CHAR(2)
) ENGINE = InnoDB;

CREATE TABLE sylius_order_payment
(
    id       CHAR(36) PRIMARY KEY,
    order_id CHAR(36) NOT NULL,
    method   VARCHAR(255),
    amount   INT,
    state    VARCHAR(50),
    FOREIGN KEY (order_id) REFERENCES sylius_order (id)
) ENGINE = InnoDB;
