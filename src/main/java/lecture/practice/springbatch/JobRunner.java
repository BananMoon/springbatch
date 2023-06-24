package lecture.practice.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * AS-IS : 스프링 부트가 구동되면서 자동으로 ApplicationRunner를 호출하여 (Batch Job을 구동시키는) JobLauncher를 이용해 Job 실행
 * TO-BE : 수동으로 작성한 JobLauncher를 구동시키도록 ApplicationRunner 구현 및 컴포넌트 등록
 * TODO 목적. 동일한 JobInstance를 2번 수행할 때 문제가 발생하는 것을 확인한다.
 * - Failed to execute ApplicationRunner
 * - JobInstanceAlreadyCompleteException : A job instance already exists and is complete for parameters={name=moonz}.  If you want to run this job again, change the parameters.
 * => JobParameters를 바꾸고 다시 실행해보면, 정상 수행하고 'BATCH_JOB_EXECUTION_PARAMS'에 추가된 것도 확인할 수 있다.
 * 참고)
 * - JobParametersBuilder : jobParameters 만드는 빌더 클래스
 */
// ApplicationRunner : 스프링 부트 초기화 후 호출하는 클래스로, 이를 오버라이드한다.
@RequiredArgsConstructor
@Component
public class JobRunner implements ApplicationRunner {
    private final JobLauncher jobLauncher;     // job 실행하는 클래스
    private final Job job;                     // JobInstanceConfiguration에서 생성한 bean 의존성 주입

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "moonz2")    // moonz로 연속 진행 시 오류. moonz2로 수정 후 진행 시 정상.
                .toJobParameters();
        // job 실행
        jobLauncher.run(job, jobParameters);
    }
}
