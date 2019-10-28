import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;
import org.joda.time.LocalDateTime;

public class BodoruWorker implements Worker {

    private String taskDefName;
    private Long sleepTime;

    public BodoruWorker(String taskDefName, Long sleepTime) {
        this.taskDefName = taskDefName;
        this.sleepTime = sleepTime;
    }

    public String getTaskDefName() {
        return taskDefName;
    }

    public TaskResult execute(Task task) {
        System.out.printf("Start Task %s(%s) - %s%n", task.getTaskDefName(), taskDefName, LocalDateTime.now());

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TaskResult result = new TaskResult(task);
        result.setStatus(Status.COMPLETED);
        System.out.printf("End Task %s - %s%n", task.getTaskDefName(),  LocalDateTime.now());
        return result;
    }
}
