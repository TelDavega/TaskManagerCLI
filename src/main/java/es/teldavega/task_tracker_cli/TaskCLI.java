package es.teldavega.task_tracker_cli;

import java.text.ParseException;

public class TaskCLI {

    public static void main(String[] args) {
        if (argsAreEmpty(args)) {
            return;
        }

        TaskManager taskManager;
        taskManager = setupTaskManager();
        if (taskManager == null) return;

        Commands command = Commands.fromString(args[0]);

        if (executeCommands(args, command, taskManager)) {
            return;
        }

        JsonParser.writeJson(taskManager.getTasks());
    }

    private static boolean executeCommands(String[] args, Commands command, TaskManager taskManager) {
        if (commandIsNull(args, command)) {
            return true;
        }

        switch (command) {
            case ADD:
                if (executeAddCommand(args, taskManager)) {
                    return true;
                }
                break;
            case UPDATE:
                if (executeUpdateCommand(args, taskManager)) {
                    return true;
                }
                break;
            case DELETE:
                if (executeDeleteCommand(args, taskManager)) {
                    return true;
                }
                break;
            case MARK_IN_PROGRESS:
                if (executeMarkInProgressCommand(args, taskManager)) {
                    return true;
                }
                break;
            case MARK_DONE:
                if (executeMarkDoneCommand(args, taskManager)) {
                    return true;
                }
                break;
            case LIST:
                executeListCommand(args, taskManager);
                break;
        }
        return false;
    }

    private static void executeListCommand(String[] args, TaskManager taskManager) {
        if (args.length < 2) {
            taskManager.listTasks();
            return;
        }
        String status = args[1];
        TaskStatus taskStatus = TaskStatus.fromStatus(status);
        if (taskStatus == null) {
            System.out.println("Unknown status: " + status);
            return;
        }
        taskManager.listTasks(taskStatus);
    }

    private static boolean executeMarkDoneCommand(String[] args, TaskManager taskManager) {
        if (args.length < 2) {
            System.out.println("Usage: task-cli mark-done <id>");
            return true;
        }
        int id = Integer.parseInt(args[1]);
        taskManager.changeTaskStatus(id, TaskStatus.DONE);
        return false;
    }

    private static boolean executeMarkInProgressCommand(String[] args, TaskManager taskManager) {
        if (args.length < 2) {
            System.out.println("Usage: task-cli mark-in-progress <id>");
            return true;
        }
        int id = Integer.parseInt(args[1]);
        taskManager.changeTaskStatus(id, TaskStatus.IN_PROGRESS);
        return false;
    }

    private static boolean executeDeleteCommand(String[] args, TaskManager taskManager) {
        if (args.length < 2) {
            System.out.println("Usage: task-cli delete <id>");
            return true;
        }
        int id = Integer.parseInt(args[1]);
        taskManager.deleteTask(id);
        return false;
    }

    private static boolean executeUpdateCommand(String[] args, TaskManager taskManager) {
        if (args.length < 3) {
            System.out.println("Usage: task-cli update <id> <task>");
            return true;
        }
        int id = Integer.parseInt(args[1]);
        String newTaskName = args[2];
        taskManager.updateTask(id, newTaskName);
        return false;
    }

    private static boolean executeAddCommand(String[] args, TaskManager taskManager) {
        if (args.length < 2) {
            System.out.println("Usage: task-cli add <task>");
            return true;
        }
        String taskName = args[1];
        Task task = taskManager.addTask(taskName);
        System.out.println("Task added successfully (ID: " + task.getId() + ")");
        return false;
    }

    private static boolean commandIsNull(String[] args, Commands command) {
        if (command == null) {
            System.out.println("Unknown command: " + args[0]);
            return true;
        }
        return false;
    }

    private static TaskManager setupTaskManager() {
        TaskManager taskManager;
        try {
            taskManager = new TaskManager();
        } catch (ParseException e) {
            System.err.println("Error reading tasks.json " + e.getMessage());
            return null;
        }
        return taskManager;
    }

    private static boolean argsAreEmpty(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: task-cli <command> [args]");
            return true;
        }
        return false;
    }
}
