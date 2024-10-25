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

    // external : 외부 기술 의존성
    "board-system-external:external-message",
    "board-system-external:external-exception-handle"
)
