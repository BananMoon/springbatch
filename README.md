# springbatch
```
- 공부 목적으로 진행하기 때문에 이해 목적의 주석이 많습니다.
- 각 브랜치에 들어가면 작업을 확인할 수 있습니다.
- main 브랜치는 초기 셋팅 모습으로 1번 브랜치(template/basic_with_db_setting) 소스코드와 동일합니다.
```

- 기본 프로젝트 정보
  - Gradle
  - Java 11

- application.yml은 민감 정보가 포함되어 있어 보이지 않도록 하였습니다.

## 브랜치 설명
- 브랜치 별 소스코드가 상이합니다.<br>
##### 1. template/basic_with_db_setting 📖
`스프링 배치 프로젝트 진행을 위한 프로젝트 초기 셋팅 상태`
- 소스코드 정보 :
  - 의존성 추가 : spring-boot-starter-web, lombok, spring-boot-starter-batch, spring-batch-test, h2, spring-boot-starter-data-jpa, spring-boot-devtools
  - 기본적인 스프링 배치 환경설정 파일
##### 2. batch_from_bottom/JobInstance_3.1.2 📖
`동일한 JobParameters를 가지는 JobInstance 연속 수행 시 JobInstanceAlreadyCompleteException 예외가 발생한다.`
- 소스코드 정보 :
  - 스프링 부트가 초기화 진행 후 자동으로 `ApplicationRunner`를 호출하여 배치 작업을 진행하기 때문에 해당 클래스를 오버라이드하는 `JobRunner` 클래스 생성하여 실행 시 해당 클래스가 수행되도록 하였다.

##### 3. batch_from_bottom/JobParameters_3.1.3 📖
`JobParameters 생성 방법은 총 3가지 존재하고, 앞선 2개만 우선 실습한다.`

1. 소스코드 상에서 Job을 실행(run)시키기 전에 JobParameters 생성
2. jar 파일 이용해 실행 요청을 날릴 때 Program Arguments로 전달하여 JobParameters 생성<br>
Ex) `java -jar {~~-SNAPSHOT.jar} name=user1 seq(long)=2L tall(double)=159.9 birth(date)=2023/06/28`<br>

2번 방법 수행 시 아래 진행해줘야 한다.
1. spring.batch.job.enabled=true 설정
2. RunnerApplication 구현체의 `@Component` 제거하여 자동 스프링부트에 의한 Runner가 수행되도록 한다.

##### 4. batch_from_bottom/JobExecution_3.1.4 📖
Job을 통해 Step 수행 시, 어떤 오류로 인해 실패로 끝나면 동일 Job을 재실행할 수 있다.

##### 5. batch_from_bottom/Step_3.2.1 📖
Step이 수행하는 작업을 별도의 클래스로 생성할 수 있다. (커스텀 가능해짐)
- 참고 : `src/main/java/lecture/practice/springbatch/CustomTasklet.java`
