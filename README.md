# springbatch
공부 목적으로 진행하기 때문에 주석 많습니다.

- 기본 프로젝트 정보
  - Gradle
  - Java 11

- 숨김 파일<br>
민감 정보가 포함되어 있는 파일은 새로 작성 필요합니다.(위치 : src/main/resources/)

- 브랜치 별 소스코드가 상이합니다.<br>
1. template/basic_with_db_setting<br>
스프링 배치 프로젝트 진행을 위한 프로젝트 초기 셋팅 상태입니다.
- 셋팅 정보 :
  - 의존성 추가 : spring-boot-starter-web, lombok, spring-boot-starter-batch, spring-batch-test, h2, spring-boot-starter-data-jpa, spring-boot-devtools
  - 기본적인 스프링 배치 환경설정 파일
