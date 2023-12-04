package lecture.practice.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 목표 : Step 내 Tasklet 생성 시 인자로 받는 StepContribution에 대해 알아본다.
 * - StepContribution :
 * - ExitStatus(종료 상태), StepExecution의 Name(스텝명), StepExecution의 JobExecution의 JobInstance의 JobName 알 수 있다.
 * - 종료 상태 값을 StepContribution 통해 셋팅/수정할 수 있다.
 */
@RequiredArgsConstructor    // 의존성 주입받기 위해
@Configuration
public class StepContributionConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("Job")
                .start(step1())    // (필수) 기본적으로 가져야하는 속성 step
                .next(step2())     // start() 다음으로 수행할 step
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("Step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("========================");
                        System.out.println(" >> Hello Spring Batch!!");
                        System.out.println("========================");
                        return RepeatStatus.FINISHED;   // null 리턴해도 동일하긴 함.
                    }
                }).build();  // 실제 동작하는 구현체. Step에서는 기본적으로 tasklet을 무한 반복 시키기 때문에 특정 상태값을 반환해야 한번만 수행한다.
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("Step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("========================");
                    System.out.println(" >> Step2 was executed.");
                    System.out.println("========================");
                    System.out.println("contribution.getExitStatus(), 어떻게 끝났는지 상태 확인 : " + contribution.getExitStatus());
                    System.out.println(" contribution.getStepExecution().getStepName(), 스텝명 : " + contribution.getStepExecution().getStepName());
                    System.out.println(" contribution.getStepExecution().getJobExecution().getJobInstance().getJobName(), Step 통해 Job명 얻기 : " + contribution.getStepExecution().getJobExecution().getJobInstance().getJobName());
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
