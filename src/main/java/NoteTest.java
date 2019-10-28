import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskDef.RetryLogic;
import com.netflix.conductor.common.metadata.tasks.TaskDef.TimeoutPolicy;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteTest {

    public static WorkflowDef defineNoteWorkflowDef() {
        TaskDef defaultTaskDef = new TaskDef();
        defaultTaskDef.setName("default_task_def");
        defaultTaskDef.setRetryLogic(RetryLogic.FIXED);
        defaultTaskDef.setTimeoutPolicy(TimeoutPolicy.TIME_OUT_WF);

        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName("bodoru_note_workflow");
        workflowDef.setDescription("bodoru note workflow");
        workflowDef.setVersion(1);
        List<WorkflowTask> rootWorkflowTaskList = new ArrayList<WorkflowTask>();


        WorkflowTask task1 = new WorkflowTask();
        task1.setName("task1");
        task1.setTaskReferenceName("task1");
        task1.setType("NOTE");
        task1.setTaskDefinition(defaultTaskDef);
        Map<String, Object> inpurParameters = new HashMap<String, Object>();
        inpurParameters.put("noteId", "3");
        task1.setInputParameters(inpurParameters);

        //-----------------------------

        WorkflowTask fork1 = new WorkflowTask();
        fork1.setName("fork1");
        fork1.setTaskReferenceName("fork1");
        fork1.setType("FORK_JOIN");
        fork1.setTaskDefinition(defaultTaskDef);

        List<WorkflowTask> forkTask1_1 = new ArrayList<WorkflowTask>();

        WorkflowTask task2 = new WorkflowTask();
        task2.setName("task2");
        task2.setTaskReferenceName("task2");
        task2.setType("NOTE");
        task2.setTaskDefinition(defaultTaskDef);
        inpurParameters = new HashMap<String, Object>();
        inpurParameters.put("noteId", "3");
        task2.setInputParameters(inpurParameters);

        WorkflowTask fork2 = new WorkflowTask();
        fork2.setName("fork2");
        fork2.setTaskReferenceName("fork2");
        fork2.setType("FORK_JOIN");
        fork2.setTaskDefinition(defaultTaskDef);

        WorkflowTask task3 = new WorkflowTask();
        task3.setName("task3");
        task3.setTaskReferenceName("task3");
        task3.setType("NOTE");
        task3.setTaskDefinition(defaultTaskDef);
        inpurParameters = new HashMap<String, Object>();
        inpurParameters.put("noteId", "3");
        task3.setInputParameters(inpurParameters);


        WorkflowTask task4 = new WorkflowTask();
        task4.setName("task4");
        task4.setTaskReferenceName("task4");
        task4.setType("NOTE");
        task4.setTaskDefinition(defaultTaskDef);
        inpurParameters = new HashMap<String, Object>();
        inpurParameters.put("noteId", "3");
        task4.setInputParameters(inpurParameters);


        List<WorkflowTask> forkTask2_1 = new ArrayList<WorkflowTask>();
        forkTask2_1.add(task3);

        List<WorkflowTask> forkTask2_2 = new ArrayList<WorkflowTask>();
        forkTask2_2.add(task4);

        List<List<WorkflowTask>> forkTasks2 = new ArrayList<List<WorkflowTask>>();
        forkTasks2.add(forkTask2_1);
        forkTasks2.add(forkTask2_2);
        fork2.setForkTasks(forkTasks2);

        WorkflowTask join = new WorkflowTask();
        join.setName("join");
        join.setTaskReferenceName("join");
        join.setType("JOIN");
        join.setJoinOn(Arrays.asList(task3.getName(), task4.getName()));
        join.setTaskDefinition(defaultTaskDef);

        WorkflowTask task5 = new WorkflowTask();
        task5.setName("task5");
        task5.setTaskReferenceName("task5");
        task5.setType("NOTE");
        task5.setTaskDefinition(defaultTaskDef);
        inpurParameters = new HashMap<String, Object>();
        inpurParameters.put("noteId", "3");
        task5.setInputParameters(inpurParameters);


        WorkflowTask event = new WorkflowTask();
        event.setName("bodoru_event");
        event.setTaskReferenceName("bodoru_event");
        event.setType("EVENT");
        event.setSink("conductor");
        event.setTaskDefinition(defaultTaskDef);

        forkTask1_1.add(task2);
        forkTask1_1.add(fork2);
        forkTask1_1.add(join);
        forkTask1_1.add(task5);
        forkTask1_1.add(event);

        //-----------------------------
        List<WorkflowTask> forkTask1_2 = new ArrayList<WorkflowTask>();

        WorkflowTask wait = new WorkflowTask();
        wait.setName("wait");
        wait.setTaskReferenceName("wait");
        wait.setType("WAIT");
        wait.setTaskDefinition(defaultTaskDef);

        WorkflowTask task6 = new WorkflowTask();
        task6.setName("task6");
        task6.setTaskReferenceName("task6");
        task6.setType("NOTE");
        task6.setTaskDefinition(defaultTaskDef);
        inpurParameters = new HashMap<String, Object>();
        inpurParameters.put("noteId", "3");
        task6.setInputParameters(inpurParameters);


        forkTask1_2.add(wait);
        forkTask1_2.add(task6);

        //-----------------------------

        List<List<WorkflowTask>> forkTasks1 = new ArrayList<List<WorkflowTask>>();
        forkTasks1.add(forkTask1_1);
        forkTasks1.add(forkTask1_2);
        fork1.setForkTasks(forkTasks1);

        rootWorkflowTaskList.add(task1);
        rootWorkflowTaskList.add(fork1);

        WorkflowTask join2 = new WorkflowTask();
        join2.setName("join2");
        join2.setTaskReferenceName("join2");
        join2.setType("JOIN");
        join2.setJoinOn(Arrays.asList(event.getName(), task6.getName()));
        join2.setTaskDefinition(defaultTaskDef);

        rootWorkflowTaskList.add(join2);

        WorkflowTask task7 = new WorkflowTask();
        task7.setName("task7");
        task7.setTaskReferenceName("task7");
        task7.setType("NOTE");
        task7.setTaskDefinition(defaultTaskDef);
        inpurParameters = new HashMap<String, Object>();
        inpurParameters.put("noteId", "3");
        task7.setInputParameters(inpurParameters);


        rootWorkflowTaskList.add(task7);

        workflowDef.setTasks(rootWorkflowTaskList);

        return workflowDef;
    }
}
