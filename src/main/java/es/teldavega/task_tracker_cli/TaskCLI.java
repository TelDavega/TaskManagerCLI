package es.teldavega.task_tracker_cli;

public class TaskCLI {

    private static TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: task-cli <command> [args]");
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
            default:
                System.out.println("Unknown command: " + command);
        }
    }
}
