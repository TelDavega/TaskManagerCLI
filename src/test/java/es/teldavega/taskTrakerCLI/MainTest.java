package es.teldavega.taskTrakerCLI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void addTaks() {
        List<String> tasks = List.of("Task 1", "Task 2", "Task 3");
        for (String task : tasks) {
            TestCreateTask(task);
        }
    }

    private void TestCreateTask(String name) {
        Task task = taskManager.addTask(name);
        assertNotNull(task);
        assertEquals(1, task.getId());
        assertEquals(name, task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals(task.getCreatedDate(), task.getUpdatedDate());
    }
}