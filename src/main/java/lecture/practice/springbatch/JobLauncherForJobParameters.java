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
 * ApplicationRunner : Bean 세팅 등 진행 후 SpringApplication.run() 수행하며 호출됨.
 * JobParameter 테스트를 위해 JobLauncher 직접 구현한다.
 * 생성한 JobParameters는 job(step) 수행 단계에서 참조될 수 있다.
  */
//@Component    // 2. jar 파일 수행 시 Program Arguments로 전달하여 JobParameters 생성할 수 있다. 테스트 시 해당 클래스는 수행하지 않도록 한다.+ spring.batch.job.enabled=false 주석!
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
