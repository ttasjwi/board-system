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
    "board-system-member:web-adapter",
    "board-system-member:application-input-port",
    "board-system-member:application-service",
    "board-system-member:application-output-port",
    "board-system-member:domain",
    "board-system-member:database-adapter",
    "board-system-member:redis-adapter",
    "board-system-member:password-adapter",
    "board-system-member:email-format-validate-adapter",

    // board =====================================================================================

    "board-system-board",
    "board-system-board:web-adapter",
    "board-system-board:application-input-port",
    "board-system-board:application-service",
    "board-system-board:application-output-port",
    "board-system-board:domain",
    "board-system-board:database-adapter",

    // email-sender ==============================================================================
    "board-system-email-sender",
    "board-system-infrastructure",
    "board-system-infrastructure:database-support",
    "board-system-infrastructure:event-publisher",
    "board-system-infrastructure:jwt",
    "board-system-infrastructure:web-support",
)
