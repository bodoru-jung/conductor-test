import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.task.WorkflowTaskCoordinator;
import com.netflix.conductor.client.worker.Worker;

public class WorkflowTaskCoordinatorMain {

    public static void main(String[] args){
        System.setProperty("conductor.worker.pollCount", "3");

        TaskClient taskClient = new TaskClient();
        taskClient.setRootURI("http://localhost:8080/api/");		//Point this to the server API

        int threadCount = 5;			//number of threads used to execute workers.  To avoid starvation, should be same or more than number of workers

        Worker worker1 = new BodoruWorker("ZERO", 0L);
        Worker worker2 = new BodoruWorker("FIVE", 5000L);

        System.out.println(worker1.getPollCount());
        System.out.println(worker2.getPollCount());

        //Create WorkflowTaskCoordinator
        WorkflowTaskCoordinator.Builder builder = new WorkflowTaskCoordinator.Builder();
        WorkflowTaskCoordinator coordinator = builder.withWorkers(worker1, worker2).withThreadCount(threadCount).withTaskClient(taskClient).build();

        //Start for polling and execution of the tasks
        coordinator.init();
    }

}
