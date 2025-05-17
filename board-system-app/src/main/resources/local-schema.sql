CREATE TABLE IF NOT EXISTS users(
    user_id       BIGINT       NOT NULL PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(68)  NOT NULL,
    username      VARCHAR(15)  NOT NULL UNIQUE,
    nickname      VARCHAR(15)  NOT NULL UNIQUE,
    role          VARCHAR(10)  NOT NULL,
    registered_at DATETIME     NOT NULL
);

CREATE TABLE IF NOT EXISTS social_connections(
    social_connection_id   BIGINT       NOT NULL PRIMARY KEY,
    user_id                BIGINT       NOT NULL,
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

CREATE TABLE IF NOT EXISTS article_categories(
    article_category_id BIGINT      NOT NULL PRIMARY KEY,
    name                VARCHAR(20) NOT NULL,
    slug                VARCHAR(8)  NOT NULL,
    board_id            BIGINT      NOT NULL,
    allow_self_delete   BOOLEAN     NOT NULL,
    allow_like          BOOLEAN     NOT NULL,
    allow_dislike       BOOLEAN     NOT NULL,
    created_at          DATETIME    NOT NULL,

    CONSTRAINT uq_board_id_and_name UNIQUE (board_id, name),
    CONSTRAINT uq_board_id_and_slug UNIQUE (board_id, slug)
);

CREATE TABLE IF NOT EXISTS articles(
    article_id          BIGINT        NOT NULL PRIMARY KEY,
    title               VARCHAR(50)   NOT NULL,
    content             VARCHAR(3000) NOT NULL,
    board_id            BIGINT        NOT NULL,
    article_category_id BIGINT        NOT NULL,
    writer_id           BIGINT        NOT NULL,
    writer_nickname     VARCHAR(15)   NOT NULL,
    created_at          DATETIME      NOT NULL,
    modified_at         DATETIME      NOT NULL
);

CREATE TABLE IF NOT EXISTS article_comments(
    article_comment_id             BIGINT        NOT NULL PRIMARY KEY,
    content                        VARCHAR(3000) NOT NULL,
    article_id                     BIGINT        NOT NULL,
    root_parent_comment_id         BIGINT        NOT NULL,
    writer_id                      BIGINT        NOT NULL,
    writer_nickname                VARCHAR(15)   NOT NULL,
    parent_comment_writer_id       BIGINT,
    parent_comment_writer_nickname VARCHAR(15),
    delete_status                  VARCHAR(20)   NOT NULL,
    created_at                     DATETIME      NOT NULL,
    modified_at                    DATETIME      NOT NULL
);

CREATE TABLE IF NOT EXISTS article_likes(
    article_like_id BIGINT   NOT NULL PRIMARY KEY,
    article_id      BIGINT   NOT NULL,
    user_id         BIGINT   NOT NULL,
    created_at      DATETIME NOT NULL,
    CONSTRAINT uq_article_id_and_user_id UNIQUE (article_id, user_id)
);

CREATE TABLE IF NOT EXISTS article_like_counts(
    article_id            BIGINT NOT NULL PRIMARY KEY,
    like_count            BIGINT NOT NULL
);
