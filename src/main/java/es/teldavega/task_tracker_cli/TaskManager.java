package es.teldavega.task_tracker_cli;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<Task> tasks;
    private int currentId;

    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.currentId = 1;
    }

    public Task addTask(String name) {
        Task task = new Task(currentId, name);
        tasks.add(task);
        currentId++;
        return task;
    }
}
