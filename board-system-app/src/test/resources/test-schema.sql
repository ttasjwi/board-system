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

CREATE TABLE IF NOT EXISTS board_article_categories(
    board_article_category_id BIGINT      NOT NULL PRIMARY KEY,
    name                      VARCHAR(20) NOT NULL,
    slug                      VARCHAR(8)  NOT NULL,
    board_id                  BIGINT      NOT NULL,
    allow_self_delete         BOOLEAN     NOT NULL,
    allow_like                BOOLEAN     NOT NULL,
    allow_dislike             BOOLEAN     NOT NULL,
    created_at                DATETIME    NOT NULL,

    CONSTRAINT uq_board_id_and_name UNIQUE (board_id, name),
    CONSTRAINT uq_board_id_and_slug UNIQUE (board_id, slug)
);
