package es.teldavega.task_tracker_cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {

    private Map<Integer,Task> tasks;
    private int currentId;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.currentId = 1;
    }

    public Task addTask(String name) {
        Task task = new Task(currentId, name);
        tasks.put(currentId, task);
        currentId++;
        return task;
    }
}
