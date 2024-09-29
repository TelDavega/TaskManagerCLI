package es.teldavega.task_tracker_cli;

public enum Commands {
    ADD("add"),
    UPDATE("update"),
    DELETE("delete"),
    MARK_IN_PROGRESS("mark-in-progress"),
    MARK_DONE("mark-done"),
    LIST("list");

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Commands fromString(String command) {
        for (Commands c : Commands.values()) {
            if (c.command.equals(command)) {
                return c;
            }
        }
        return null;
    }
}
