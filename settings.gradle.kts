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
    "board-system-api:api-deploy",

    // application
    "board-system-application:application-core",
    "board-system-application:application-member",

    // domain
    "board-system-domain:domain-core",
    "board-system-domain:domain-email",
    "board-system-domain:domain-member",

    // external : 외부 기술 의존성
    "board-system-external:external-message",
    "board-system-external:external-db",
    "board-system-external:external-exception-handle"
)
