
CREATE TABLE weather_alerts
(
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    external_id   VARCHAR(255)    NOT NULL,
    sent          DATETIME,
    effective     DATETIME        NOT NULL,
    expires       DATETIME        NOT NULL,
    message_type  VARCHAR(50)     NOT NULL,
    severity      VARCHAR(50)     NOT NULL,
    certainty     VARCHAR(50)     NOT NULL,
    urgency       VARCHAR(50),
    sender        VARCHAR(255)    NOT NULL,
    description   TEXT            NOT NULL,
    instruction   TEXT,
    status        VARCHAR(50)     NOT NULL
);
