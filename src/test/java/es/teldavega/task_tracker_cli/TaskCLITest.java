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
    void testAddTask() throws IOException {
        String[] args = {"add", "Task 1"};
        TaskCLI.main(args);
        String expected = "Task added successfully (ID: 1)" + System.lineSeparator();
        assertEquals(expected,outContent.toString());
    }

    @Test
    void testAddMultipleTasks() throws IOException {
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
    void testUnknownCommand() throws IOException {
        String[] args = {"kk", "kk"};
        String expected = "Unknown command: kk" + System.lineSeparator();
        TaskCLI.main(args);
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testNoArgs() throws IOException {
        String expected = "Usage: task-cli <command> [args]" + System.lineSeparator();
        TaskCLI.main(new String[]{});
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testAddWithoutDescription() throws IOException {
        String[] args = {"add"};
        String expected = "Usage: task-cli add <task>" + System.lineSeparator();
        TaskCLI.main(args);
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testErrorReadingTask() throws IOException {
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

}