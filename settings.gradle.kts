rootProject.name = "board-system"

include(

    // 설정 최종 구성 및 실행
    "board-system-container",

    // 공용 모듈
    "board-system-core",
    "board-system-logging",

    // api
    "board-system-api:api-deploy",
)
