package es.teldavega.task_tracker_cli;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Task Tracker CLI");
        System.out.println("Please enter your name:");
        String name = scanner.nextLine();
        System.out.println("Hello " + name + "!");
    }
}
