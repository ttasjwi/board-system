rootProject.name = "board-system"

include(

    // 설정 최종 구성 및 실행
    "board-system-container",

    // 공용 모듈
    "board-system-core",
    "board-system-logging",

    // api
    "board-system-api:api-core",
    "board-system-api:api-member",
    "board-system-api:api-auth",
    "board-system-api:api-board",
    "board-system-api:api-deploy",

    // application
    "board-system-application:application-core",
    "board-system-application:application-member",
    "board-system-application:application-board",
    "board-system-application:application-auth",

    // domain
    "board-system-domain:domain-core",
    "board-system-domain:domain-member",
    "board-system-domain:domain-board",
    "board-system-domain:domain-auth",

    // event
    "board-system-event:event-publisher-member",
    "board-system-event:event-consumer-member",

    // external : 외부 기술 의존성
    "board-system-external:external-message",
    "board-system-external:external-db",
    "board-system-external:external-redis",
    "board-system-external:external-security",
    "board-system-external:external-exception-handle",
    "board-system-external:external-email-sender",
    "board-system-external:external-email-format-checker",
)
