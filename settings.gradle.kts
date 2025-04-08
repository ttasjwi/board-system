rootProject.name = "board-system"

include(

    // 애플리케이션 구성
    "board-system-app",

    // 여기서부터 공통모듈 (선택적으로 각 모듈에서 사용) ========================================================
    "board-system-common",

    "board-system-common:core",

    // JSON 직렬화, 역직렬화
    "board-system-common:data-serializer",

    // Snowflake 알고리즘 기반 Id 생성기
    "board-system-common:id-generator",

    // 향후 서비스 분리시 여러 서비스에서 공통으로 사용할 수 있는 미들웨어 위치
    // 예외처리(RestControllerAdvice, 최전방 예외 처리 필터), 메시지-국제화, 액세스토큰 인증 필터
    // 자동구성으로 할까 생각 중
    "board-system-common:web-support",

    // kotlin-logging 유틸리티를 한단계 추상화하여 서비스 전역에서 logger 인스턴스 획득을 사용을 목적
    "board-system-common:logger",
)
