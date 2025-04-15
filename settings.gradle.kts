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

    // member ======================================================================================
    "board-system-member",
    "board-system-member:member-web-adapter",
    "board-system-member:member-application-input-port",
    "board-system-member:member-application-service",
    "board-system-member:member-application-output-port",
    "board-system-member:member-domain",
    "board-system-member:member-database-adapter",
    "board-system-member:member-redis-adapter",
    "board-system-member:member-password-adapter",
    "board-system-member:member-email-format-validate-adapter",

    // board =====================================================================================

    "board-system-board",
    "board-system-board:board-web-adapter",
    "board-system-board:board-application-input-port",
    "board-system-board:board-application-service",
    "board-system-board:board-application-output-port",
    "board-system-board:board-domain",
    "board-system-board:board-database-adapter",

    // article ===================================================================================

    "board-system-article",
    "board-system-article:article-web-adapter",
    "board-system-article:article-application-input-port",
    "board-system-article:article-application-service",
    "board-system-article:article-application-output-port",
    "board-system-article:article-domain",
    "board-system-article:article-database-adapter",

    // email-sender ==============================================================================
    "board-system-email-sender",
    "board-system-infrastructure",
    "board-system-infrastructure:database-support",
    "board-system-infrastructure:event-publisher",
    "board-system-infrastructure:jwt",
    "board-system-infrastructure:web-support",
)
