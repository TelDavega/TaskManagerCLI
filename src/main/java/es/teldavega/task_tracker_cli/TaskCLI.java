package es.teldavega.task_tracker_cli;

import java.io.IOException;
import java.text.ParseException;

public class TaskCLI {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: task-cli <command> [args]");
            return;
        }

        TaskManager taskManager;
        try {
            taskManager = new TaskManager();
        } catch (ParseException e) {
            System.err.println("Error reading tasks.json " + e.getMessage());
            return;
        }

        String command = args[0];

        switch (command) {
            case "add":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli add <task>");
                    return;
                }
                String taskName = args[1];
                Task task = taskManager.addTask(taskName);
                System.out.println("Task added successfully (ID: " + task.getId() + ")");
                break;
            case "delete":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli delete <id>");
                    return;
                }
                int id = Integer.parseInt(args[1]);
                taskManager.deleteTask(id);
                break;
            default:
                System.out.println("Unknown command: " + command);
                return;
        }

        JsonParser.writeJson(taskManager.getTasks());
    }
}
