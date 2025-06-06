rootProject.name = "board-system"

include(

    // 애플리케이션 구성
    "board-system-app",

    // 여기서부터 공통모듈 ========================================================
    "board-system-common",
    "board-system-common:core",
    "board-system-common:token",
    "board-system-common:data-serializer",
    "board-system-common:id-generator",
    "board-system-common:logger",

    // user ====================================================================
    "board-system-user",
    "board-system-user:user-web-adapter",
    "board-system-user:user-application-input-port",
    "board-system-user:user-application-service",
    "board-system-user:user-application-output-port",
    "board-system-user:user-domain",
    "board-system-user:user-password-adapter",
    "board-system-user:user-oauth2-client-adapter",
    "board-system-user:user-email-format-validate-adapter",

    // board ====================================================================
    "board-system-board",
    "board-system-board:board-web-adapter",
    "board-system-board:board-application-input-port",
    "board-system-board:board-application-service",
    "board-system-board:board-application-output-port",
    "board-system-board:board-domain",

    // article ===================================================================================
    "board-system-article",
    "board-system-article:article-web-adapter",
    "board-system-article:article-application-input-port",
    "board-system-article:article-application-service",
    "board-system-article:article-application-output-port",
    "board-system-article:article-domain",

    // article-comment ===================================================================================
    "board-system-article-comment",
    "board-system-article-comment:article-comment-web-adapter",
    "board-system-article-comment:article-comment-application-input-port",
    "board-system-article-comment:article-comment-application-service",
    "board-system-article-comment:article-comment-application-output-port",
    "board-system-article-comment:article-comment-domain",

    // article-like ===================================================================================
    "board-system-article-like",
    "board-system-article-like:article-like-web-adapter",
    "board-system-article-like:article-like-application-input-port",
    "board-system-article-like:article-like-application-service",
    "board-system-article-like:article-like-application-output-port",
    "board-system-article-like:article-like-domain",

    // article-view ===================================================================================
    "board-system-article-view",
    "board-system-article-view:article-view-web-adapter",
    "board-system-article-view:article-view-batch-adapter",
    "board-system-article-view:article-view-application-input-port",
    "board-system-article-view:article-view-application-service",
    "board-system-article-view:article-view-application-output-port",
    "board-system-article-view:article-view-domain",

    // article-read ===================================================================================
    "board-system-article-read",
    "board-system-article-read:article-read-web-adapter",
    "board-system-article-read:article-read-application-input-port",
    "board-system-article-read:article-read-application-service",
    "board-system-article-read:article-read-application-output-port",
    "board-system-article-read:article-read-domain",

    // email-sender ==============================================================================
    "board-system-email-sender",

    // infrastructure ============================================================================
    "board-system-infrastructure",
    "board-system-infrastructure:event-publisher",
    "board-system-infrastructure:jwt",
    "board-system-infrastructure:web-support",
    "board-system-infrastructure:database-adapter",
    "board-system-infrastructure:redis-adapter",

    // support
    "board-system-test:restdocs-support"
)
