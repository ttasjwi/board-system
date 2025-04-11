CREATE TABLE IF NOT EXISTS members(
    member_id     BIGINT       NOT NULL PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(68)  NOT NULL,
    username      VARCHAR(15)  NOT NULL UNIQUE,
    nickname      VARCHAR(15)  NOT NULL UNIQUE,
    role          VARCHAR(10)  NOT NULL,
    registered_at DATETIME     NOT NULL
);

CREATE TABLE IF NOT EXISTS social_connections(
    social_connection_id   BIGINT       NOT NULL PRIMARY KEY,
    member_id              BIGINT       NOT NULL,
    social_service         VARCHAR(20)  NOT NULL,
    social_service_user_id VARCHAR(100) NOT NULL,
    linked_at              DATETIME     NOT NULL
);

CREATE TABLE IF NOT EXISTS boards(
    board_id    BIGINT             NOT NULL PRIMARY KEY,
    name        VARCHAR(30) UNIQUE NOT NULL,
    description VARCHAR(100)       NOT NULL,
    manager_id  BIGINT             NOT NULL,
    slug        VARCHAR(30) UNIQUE NOT NULL,
    created_at  DATETIME           NOT NULL
);
