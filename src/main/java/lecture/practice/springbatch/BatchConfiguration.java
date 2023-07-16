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
 * FAILED 상태로 job 실행이 마무리됐을 때는 동일 job이더라도 다시 실행한다.
 * - BATCH_JOB_EXECUTION 테이블의 STATUS, EXIT_CODE 칼럼 참고
 */
@RequiredArgsConstructor
@Configuration
public class BatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
                .start(helloStep1())    // (필수) 기본적으로 가져야하는 속성 step
                .next(helloStep2())     // start() 다음으로 수행할 step
                .build();
    }

    @Bean
    public Step helloStep1() {
        return stepBuilderFactory.get("helloStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("========================");
                        System.out.println(" >> Hello Spring Batch!!");
                        System.out.println("========================");
//                        throw new RuntimeException("step 2 has failed."); // error를 발생시키면 FAILED 상태가 되어 재실행할 수 있게 된다.
                        return RepeatStatus.FINISHED;   // null 리턴해도 동일하긴 함.
                    }
                }).build();  // 실제 동작하는 구현체. Step에서는 기본적으로 tasklet을 무한 반복 시키기 때문에 특정 상태값을 반환해야 한번만 수행한다.
    }

    @Bean
    public Step helloStep2() {
        return stepBuilderFactory.get("helloStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("========================");
                    System.out.println(" >> Step2 was executed.");
                    System.out.println("========================");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
