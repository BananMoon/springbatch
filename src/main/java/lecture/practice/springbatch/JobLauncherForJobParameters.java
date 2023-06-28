package lecture.practice.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ApplicationRunner : Bean 세팅 등 진행 후 SpringApplication.run() 수행하며 호출됨.<br>
 * JobParameter 테스트를 위해 JobLauncher 직접 구현한다.<br>
 * 생성한 JobParameters는 job(step) 수행 단계에서 참조될 수 있다.<br>
 * - JobParameters 생성하는 방법<br>
 * 1. 소스코드 상에서 Job을 실행(run)시키기 전에 JobParameters 생성<br>
 * 2. jar 파일 이용해 실행 요청을 날릴 때 Program Arguments로 전달하여 JobParameters 생성.<br>
 * Ex) java -jar {~~-SNAPSHOT.jar} name=user1 seq(long)=2L tall(double)=159.9 birth(date)=2023/06/28 <br>
 * 이때 1. spring.batch.job.enabled=true 설정<br>
 * 2. 해당 컴포넌트 애노테이션 제거하여 자동 스프링부트에 의한 Runner가 수행되도록 한다.
  */
@Component
@RequiredArgsConstructor
public class JobLauncherForJobParameters implements ApplicationRunner {
    private final JobLauncher jobLauncher;
    private final Job job;
    // 1. JobParameter를 코드로 생성하는 방법
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Job을 실행 시키기 전 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user1")
                .addLong("seq", 2L)
                .addDate("birth", new Date())
                .addDouble("tall", 158.9)
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
