package es.teldavega.task_tracker_cli;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskManager {

    private Map<Integer,Task> tasks;
    private int currentId;

    public TaskManager() throws ParseException {
        this.tasks = JsonParser.readJson("tasks.json");
        this.currentId = tasks.size() + 1;
    }

    public Task addTask(String name) {
        Task task = new Task(currentId, name);
        tasks.put(currentId, task);
        currentId++;
        return task;
    }

    public void updateTask(int id, String name) {
        Task task = tasks.get(id);
        if (task == null) {
            System.out.println("Task with ID " + id + " not found");
            return;
        }
        task.setDescription(name);
        tasks.put(id, task);
        System.out.println("Task updated successfully (ID: " + id + ")");
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteTask(int id) {

        if (!tasks.containsKey(id)) {
            System.out.println("Task not found (ID: " + id + ")");
            return;
        }
        tasks.remove(id);
        System.out.println("Task deleted successfully (ID: " + id + ")");
    }

    public void changeTaskStatus(int id, TaskStatus newStatus) {
        Task task = tasks.get(id);
        if (task == null) {
            System.out.println("Task with ID " + id + " not found");
            return;
        }
        task.setStatus(newStatus);
        tasks.put(id, task);
        System.out.println("Task marked as " + newStatus.getStatus() + " (ID: " + id + ")");
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found");
            return;
        }
        System.out.println(JsonParser.getStringJson(getTasks()));

    }
}
