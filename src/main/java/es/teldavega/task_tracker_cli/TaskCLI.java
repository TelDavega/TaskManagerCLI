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
            case "update":
                if (args.length < 3) {
                    System.out.println("Usage: task-cli update <id> <task>");
                    return;
                }
                int id = Integer.parseInt(args[1]);
                String newTaskName = args[2];
                taskManager.updateTask(id, newTaskName);
                break;
            case "delete":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli delete <id>");
                    return;
                }
                id = Integer.parseInt(args[1]);
                taskManager.deleteTask(id);
                break;
            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli mark-in-progress <id>");
                    return;
                }
                id = Integer.parseInt(args[1]);
                taskManager.markInProgress(id);
                break;

            default:
                System.out.println("Unknown command: " + command);
                return;
        }

        JsonParser.writeJson(taskManager.getTasks());
    }
}
