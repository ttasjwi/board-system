rootProject.name = "board-system"

include(

    // 애플리케이션 구성
    "board-system-app",

    // 여기서부터 공통모듈 ========================================================
    "board-system-common",
    "board-system-common:core",
    "board-system-common:data-serializer",
    "board-system-common:event-publisher",
    "board-system-common:id-generator",
    "board-system-common:web-support",
    "board-system-common:database-support",
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

    // email-sender ==============================================================================
    "board-system-email-sender",
)
