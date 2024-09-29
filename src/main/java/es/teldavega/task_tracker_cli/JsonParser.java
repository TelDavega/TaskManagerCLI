package es.teldavega.task_tracker_cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JsonParser {
    public static String toJson(Task task) {
        return "{"
                + "\"id\":" + task.getId() + ","
                + "\"description\":\"" + task.getDescription() + "\","
                + "\"status\":\"" + task.getStatus() + "\","
                + "\"createdAt\":\"" + DateUtils.fromDate(task.getCreatedAt()) + "\","
                + "\"updatedAt\":\"" + DateUtils.fromDate(task.getUpdatedAt()) + "\""
                + "}";
    }

    public static Task fromJson(String json) throws ParseException {
        String[] parts = json.split(",");
        int id = Integer.parseInt(parts[0].split(":")[1]);
        String description = parts[1].split(":")[1].replace("\"", "");
        TaskStatus status = TaskStatus.fromStatus(parts[2].split(":")[1].replace("\"", ""));

        String createdAt = getDateFromJson("\"createdAt\":\"", parts[3]);
        String updatedAt = getDateFromJson("\"updatedAt\":\"", parts[4]);
        return new Task(id, description, status, DateUtils.fromString(createdAt), DateUtils.fromString(updatedAt));
    }

    private static String getDateFromJson(String key, String part) {
        int startIndex = part.indexOf(key) + key.length();
        int endIndex = part.indexOf("\"", startIndex);
        return part.substring(startIndex, endIndex);
    }

    public static void writeJson(Collection<Task> tasks) {
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
        int i = 1;
        for (Task task : tasks) {
            json.append(toJson(task));
            if (tasks.size() > 1 && i < tasks.size()) {
                json.append(",").append(System.lineSeparator());
            }
            i++;
        }
        json.append(System.lineSeparator()).append("]");
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(json.toString());
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
        }
    }

    public static Map<Integer, Task> readJson(String filename) throws ParseException {
        try {
            Path path = Paths.get(filename);
            if (!Files.exists(path)) {
                return new HashMap<>();
            }
            String json = Files.readString(path);
            String[] parts = json.split("},");
            Map<Integer, Task> tasks = new HashMap<>();
            for (String part : parts) {
                Task task = fromJson(part);
                tasks.put(task.getId(), task);
            }
            return tasks;
        } catch (IOException e) {
            System.err.println("Error reading JSON from file: " + e.getMessage());
        }
        return new HashMap<>();
    }
}
