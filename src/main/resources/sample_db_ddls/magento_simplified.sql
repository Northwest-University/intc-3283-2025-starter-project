DROP DATABASE IF EXISTS magento;
CREATE DATABASE IF NOT EXISTS magento;
USE magento;

CREATE TABLE quote
(
    entity_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    store_id    SMALLINT,
    is_active   TINYINT(1),
    created_at  DATETIME,
    updated_at  DATETIME
) ENGINE = InnoDB;

CREATE TABLE quote_item
(
    item_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    quote_id   BIGINT NOT NULL,
    product_id BIGINT,
    qty        DECIMAL(12, 4),
    price      DECIMAL(12, 4),
    row_total  DECIMAL(12, 4),
    FOREIGN KEY (quote_id) REFERENCES quote (entity_id)
) ENGINE = InnoDB;

CREATE TABLE quote_address
(
    address_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quote_id   BIGINT NOT NULL,
    firstname  VARCHAR(255),
    lastname   VARCHAR(255),
    street     TEXT,
    city       VARCHAR(255),
    postcode   VARCHAR(20),
    country_id CHAR(2),
    FOREIGN KEY (quote_id) REFERENCES quote (entity_id)
) ENGINE = InnoDB;

CREATE TABLE quote_payment
(
    payment_id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    quote_id               BIGINT NOT NULL,
    method                 VARCHAR(255),
    additional_information TEXT,
    FOREIGN KEY (quote_id) REFERENCES quote (entity_id)
) ENGINE = InnoDB;
