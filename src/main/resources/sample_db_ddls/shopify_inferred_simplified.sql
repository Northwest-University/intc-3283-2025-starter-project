DROP DATABASE IF EXISTS shopify;
CREATE DATABASE IF NOT EXISTS shopify;
USE shopify;

CREATE TABLE cart
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    token       CHAR(36) UNIQUE,
    created_at  DATETIME,
    updated_at  DATETIME
) ENGINE = InnoDB;

CREATE TABLE cart_line_items
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id    BIGINT NOT NULL,
    variant_id BIGINT NOT NULL,
    quantity   INT,
    price      DECIMAL(12, 4),
    FOREIGN KEY (cart_id) REFERENCES cart (id)
) ENGINE = InnoDB;

CREATE TABLE checkout
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id             BIGINT,
    order_id            BIGINT,
    status              VARCHAR(50),
    shipping_address_id BIGINT,
    billing_address_id  BIGINT,
    FOREIGN KEY (cart_id) REFERENCES cart (id)
) ENGINE = InnoDB;

CREATE TABLE address
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    street     VARCHAR(255),
    city       VARCHAR(255),
    province   VARCHAR(100),
    country    VARCHAR(100),
    zip        VARCHAR(20)
) ENGINE = InnoDB;
