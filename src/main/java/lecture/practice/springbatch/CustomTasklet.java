package lecture.practice.springbatch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

// Bean 기능을 수행하려면 @Component 를 붙이고, DI 사용할 수 있음.
//@Component
public class CustomTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        System.out.println("========================");
        System.out.println(" >> CustomTasklet 클래스로 수행 : Step2 has executed.");
        System.out.println("========================");
        return RepeatStatus.FINISHED;
    }
}
