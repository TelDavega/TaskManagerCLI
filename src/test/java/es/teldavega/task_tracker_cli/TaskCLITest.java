package es.teldavega.task_tracker_cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskCLITest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUp() throws IOException {
        Path path = Paths.get("tasks.json");
        Files.deleteIfExists(path);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testAddTask() {
        String[] args = {"add", "Task 1"};
        TaskCLI.main(args);
        String expected = "Task added successfully (ID: 1)" + System.lineSeparator();
        assertEquals(expected,outContent.toString());
    }

    @Test
    void testAddMultipleTasks() {
        List<String[]> listOfArgs = Arrays.asList(new String[]{"add", "Task 1"},
                new String[]{"add", "Task 2"},
                new String[]{"add", "Task 3"});
        StringBuilder expected = new StringBuilder();
        int i = 1;
        for (String[] args : listOfArgs) {
            TaskCLI.main(args);
            expected.append("Task added successfully (ID: ").append(i).append(")").append(System.lineSeparator());
            i++;
        }
        assertEquals(expected.toString(),outContent.toString());
    }

    @Test
    void testUnknownCommand() {
        String[] args = {"kk", "kk"};
        String expected = "Unknown command: kk" + System.lineSeparator();
        TaskCLI.main(args);
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testNoArgs() {
        String expected = "Usage: task-cli <command> [args]" + System.lineSeparator();
        TaskCLI.main(new String[]{});
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testAddWithoutDescription() {
        String[] args = {"add"};
        String expected = "Usage: task-cli add <task>" + System.lineSeparator();
        TaskCLI.main(args);
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testErrorReadingTask() {
        try {
            Path path = Paths.get("tasks.json");
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (Exception e) {
            System.err.println("Error creating file: " + e.getMessage());
        }

        File file = new File("tasks.json");
        StringBuilder json = new StringBuilder();
        json.append("[").append(System.lineSeparator());
        json.append("{\"id\":1,\"description\":\"Task 1\",\"status\":\"todo\",\"createdAt\":\"kk\"," +
                "\"updatedAt\":\"29-9-2024 01:08:11\"}");
        json.append(System.lineSeparator()).append("]");
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(json.toString());
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
        }

        String[] args = {"add", "Task 2"};
        String expected = "";
        TaskCLI.main(args);
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testUpdateTask() {
        String[] args = {"add", "Task 1"};
        TaskCLI.main(args);
        outContent.reset();
        args = new String[]{"update", "1", "Task 1 buy groceries"};
        TaskCLI.main(args);
        String expected = "Task updated successfully (ID: 1)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testUpdateTaskNotFound() {
        String[] args = {"update", "1", "Task 1 buy groceries"};
        TaskCLI.main(args);
        String expected = "Task with ID 1 not found" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testUpdateTaskWithoutDescription() {
        String[] args = {"update", "1"};
        String expected = "Usage: task-cli update <id> <task>" + System.lineSeparator();
        TaskCLI.main(args);
        assertEquals(expected, outContent.toString());
    }
    @Test
    void testDeleteTask() {
        String[] args = {"add", "Task 1"};
        TaskCLI.main(args);
        outContent.reset();
        args = new String[]{"delete", "1"};
        TaskCLI.main(args);
        String expected = "Task deleted successfully (ID: 1)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testDeleteTaskNotFound() {
        String[] args = {"delete", "1"};
        TaskCLI.main(args);
        String expected = "Task not found (ID: 1)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testDeleteWithoutId() {
        String[] args = {"delete"};
        String expected = "Usage: task-cli delete <id>" + System.lineSeparator();
        TaskCLI.main(args);
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testMarkInProgress() {
        String[] args = {"add", "Task 1"};
        TaskCLI.main(args);
        outContent.reset();
        args = new String[]{"mark-in-progress", "1"};
        TaskCLI.main(args);
        String expected = "Task marked as in-progress (ID: 1)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testMarkInProgressNotFound() {
        String[] args = {"mark-in-progress", "1"};
        TaskCLI.main(args);
        String expected = "Task with ID 1 not found" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testMarkInProgressWithoutId() {
        String[] args = {"mark-in-progress"};
        String expected = "Usage: task-cli mark-in-progress <id>" + System.lineSeparator();
        TaskCLI.main(args);
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testMarkDone() {
        String[] args = {"add", "Task 1"};
        TaskCLI.main(args);
        outContent.reset();
        args = new String[]{"mark-done", "1"};
        TaskCLI.main(args);
        String expected = "Task marked as done (ID: 1)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testMarkDoneNotFound() {
        String[] args = {"mark-done", "1"};
        TaskCLI.main(args);
        String expected = "Task with ID 1 not found" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testMarkDoneWithoutId() {
        String[] args = {"mark-done"};
        String expected = "Usage: task-cli mark-done <id>" + System.lineSeparator();
        TaskCLI.main(args);
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testListTasks() {
        setupListOfTasks();
        String[] args;
        args = new String[]{"list"};
        TaskCLI.main(args);
        assertTrue(outContent.toString().contains("\"id\":1,\"description\":\"Task 1\",\"status\":\"in-progress\""));
        assertTrue(outContent.toString().contains("\"id\":2,\"description\":\"Task 2\",\"status\":\"in-progress\""));
        assertTrue(outContent.toString().contains("\"id\":3,\"description\":\"Task 3\",\"status\":\"done\""));
        assertTrue(outContent.toString().contains("\"id\":4,\"description\":\"Task 4\",\"status\":\"done\""));
        assertTrue(outContent.toString().contains("\"id\":5,\"description\":\"Task 5\",\"status\":\"todo\""));
    }

    private void setupListOfTasks() {
        List<String[]> listOfArgs = Arrays.asList(new String[]{"add", "Task 1"},
                new String[]{"add", "Task 2"},
                new String[]{"add", "Task 3"},
                new String[]{"add", "Task 4"},
                new String[]{"add", "Task 5"},
                new String[]{"mark-in-progress", "1"},
                new String[]{"mark-in-progress", "2"},
                new String[]{"mark-done", "3"},
                new String[]{"mark-done", "4"});
        executeCommands(listOfArgs);
        outContent.reset();
    }

    private void executeCommands(List<String[]> listOfArgs) {
        for (String[] args : listOfArgs) {
            TaskCLI.main(args);
        }
    }

    @Test
    void testListTasksEmpty() {
        String[] args = {"list"};
        TaskCLI.main(args);
        String expected = "No tasks found" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

}