DROP DATABASE IF EXISTS woo_commerce_simplified;
CREATE DATABASE IF NOT EXISTS woo_commerce_simplified;
USE woo_commerce_simplified;

CREATE TABLE wp_posts
(
    ID            BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_type     VARCHAR(20),
    post_status   VARCHAR(20),
    post_date     DATETIME,
    post_modified DATETIME
) ENGINE = InnoDB;

CREATE TABLE wp_postmeta
(
    meta_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id    BIGINT NOT NULL,
    meta_key   VARCHAR(255),
    meta_value LONGTEXT,
    FOREIGN KEY (post_id) REFERENCES wp_posts (ID)
) ENGINE = InnoDB;

CREATE TABLE wp_woocommerce_order_items
(
    order_item_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id        BIGINT NOT NULL,
    order_item_name VARCHAR(255),
    order_item_type VARCHAR(50),
    FOREIGN KEY (order_id) REFERENCES wp_posts (ID)
) ENGINE = InnoDB;

CREATE TABLE wp_woocommerce_order_itemmeta
(
    meta_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_item_id BIGINT NOT NULL,
    meta_key      VARCHAR(255),
    meta_value    LONGTEXT,
    FOREIGN KEY (order_item_id) REFERENCES wp_woocommerce_order_items (order_item_id)
) ENGINE = InnoDB;
