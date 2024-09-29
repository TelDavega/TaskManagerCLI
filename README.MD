# Task Tracker CLI

## Description
**Task Tracker CLI** ask tracker is a project used to track and manage your tasks. In this task, you will build a simple command line interface (CLI) to track what you need to do, what you have done, and what you are currently working on. The program generates a json file to store the tasks.

This project is part of the [Roadmap.sh](https://roadmap.sh/projects/task-tracker) backend projects.

## Requirements
Before compiling and running the program, make sure you have the following installed:

- **Java Development Kit (JDK)** 1.8 or higher. You can download it from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
- **Apache Maven** 3.6.0 or higher. You can download it from [here](https://maven.apache.org/download.cgi).


To check that you have Java and Maven installed, you can run the following commands in your terminal:
```bash
java -version
mvn -version

```

## Compilation

To compile the program, navigate to the project's root directory and run the following command:

```bash
mvn clean package
```

This command will compile the program and create a JAR file in the `target` directory.

## Running the Program

To run the program, navigate to the `target` directory and run the following command:

```bash
java -jar task-tracker-cli-1.0-SNAPSHOT.jar add "Task 1"
```

This command will add a new task with the name "Task 1" to the task list.

if you want to simplify execution, you can create a shell script file with the following content:

### Linux or MacOS

Create a file named `task-cli` with the following content:

```bash
#!/bin/bash
java -jar "/ruta/completa/a/TaskTrakerCLI-1.0-SNAPSHOT-jar-with-dependencies.jar" "$@"
```
Give the file execution permissions:

```bash
chmod +x task-cli
```

Move the file to a directory in your PATH, for example `/usr/local/bin`:

```bash
sudo mv task-cli /usr/local/bin
```

Now you can run the program by typing `task-cli` in your terminal:

```bash
task-cli add "Task 1"
```

Create a batch file with the following content:
### Windows
```bash
@echo off
java -jar "C:\path\to\TaskTrakerCLI-1.0-SNAPSHOT-jar-with-dependencies.jar" %*
```

Save the file as `task-cli.bat` and move it to a directory in your PATH.

Now you can run the program by typing `task-cli` in your terminal:

```bash
task-cli add "Task 1"
```

## Usage

The program supports the following commands:

- `add <task>`: Add a new task to the task list.
- `update <task-id> <new-task>`: Update the task with the given ID.
- `delete <task-id>`: Delete the task with the given ID.
- `mark-done <task-id>`: Mark a task as done.
- `mark-in-progress <task-id>`: Mark a task as in progress.
- `list`: List all tasks in the task list.
- `list done`: List all tasks that are done.
- `list in-progress`: List all tasks that are in progress.
- `list todo`: List all tasks that are not done or in progress.

## Example

Here is an example of how to use the program:

```bash
task-cli add "Task 1"
task-cli add "Task 2"
task-cli add "Task 3"

task-cli list
task-cli mark-done 1
task-cli mark-in-progress 2

task-cli list done
task-cli list in-progress

task-cli update 3 "Updated Task 3"
task-cli delete 1
```

