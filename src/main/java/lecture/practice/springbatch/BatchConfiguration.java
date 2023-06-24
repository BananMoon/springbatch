package lecture.practice.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@RequiredArgsConstructor    // 의존성 주입받기 위해
@Configuration
public class BatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        // helloStep1 실행 직전에 ApplicationRunner 호출하여 JobParameters 생성 후 실행.
        return jobBuilderFactory.get("helloJob")
                .start(helloStep1())
                .next(helloStep2())
                .build();
    }

     /*
     Tasklet의 execute()에 인자로 들어오는
     StepContribution(StepContribution>StepExecution>JobExecution),
     ChunkContext(ChunkContext>StepContext>StepExecution>JobExecution) 에서
     JobLauncherForJobParameters 클래스 통해 생성한 JobParameters를 참조할 수 있다.
     */
    @Bean
    public Step helloStep1() {
        return stepBuilderFactory.get("helloStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        // jobParameters 참조 방법
                        // 1. StepContribution 이용
                        JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
                        System.out.printf("jobParameters의 name: %s, seq: %d, tall: %f, "
                                , jobParameters.getString("name")
                        ,jobParameters.getLong("seq")
                        ,jobParameters.getDouble("tall"));
                        System.out.println("birth: " + jobParameters.getDate("birth"));

                        // 2. ChunkContext 이용
                        Map<String, Object> jobParametersFromChuckContext = chunkContext.getStepContext().getJobParameters();
                        jobParametersFromChuckContext.forEach((key, value) -> {
                            System.out.println( key +":"+ value );
                        });

                        System.out.println("========================");
                        System.out.println(" >> Step1 was executed.");
                        System.out.println("========================");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
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
