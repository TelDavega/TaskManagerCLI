package es.teldavega.task_tracker_cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskManagerTest {

    TaskManager taskManager;

    @BeforeEach
    void setUp() throws ParseException, IOException {
        Path path = Paths.get("tasks.json");
        Files.deleteIfExists(path);
        taskManager = new TaskManager();
    }

    @Test
    void addTaks() {
        List<String> tasks = List.of("Task 1", "Task 2", "Task 3");
        int id = 1;
        for (String task : tasks) {
            TestCreateTask(task, id);
            id++;
        }
    }

    private void TestCreateTask(String name, int id) {
        Task task = taskManager.addTask(name);
        assertNotNull(task);
        assertEquals(id, task.getId());
        assertEquals(name, task.getDescription());
        assertEquals(TaskStatus.TODO.getStatus(), task.getStatus());
        assertEquals(task.getCreatedAt(), task.getUpdatedAt());
    }
}