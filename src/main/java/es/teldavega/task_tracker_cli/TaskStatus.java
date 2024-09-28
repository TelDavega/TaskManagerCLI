package es.teldavega.task_tracker_cli;

public enum TaskStatus {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String status;


    TaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static TaskStatus fromStatus(String status) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.status.equals(status)) {
                return taskStatus;
            }
        }
        return null;
    }
}
