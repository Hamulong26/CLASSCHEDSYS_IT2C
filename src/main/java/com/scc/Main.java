
package com.scc;

import com.scc.dao.ScheduleDao;
import com.scc.dao.UserDao;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final UserDao userDao = new UserDao();
    private static final ScheduleDao scheduleDao = new ScheduleDao();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n================================");
            System.out.println("      CLASS SCHEDULE SYSTEM");
            System.out.println("================================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. View Class Schedules");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    login();
                    break;

                case "2":
                    register();
                    break;

                case "3":
                    scheduleDao.displaySchedules();
                    break;

                case "4":
                    System.out.println("Thank you for using the system!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid selection!");
            }
        }
    }

    private static void register() {

        System.out.println("\n========== REGISTER ==========");

        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password are required.");
            return;
        }

        System.out.println("Select Role:");
        System.out.println("1. STUDENT");
        System.out.println("2. ADMIN");
        System.out.print("Choose role: ");

        String roleChoice = sc.nextLine();

        String role;

        switch (roleChoice) {
            case "1":
                role = "STUDENT";
                break;

            case "2":
                role = "ADMIN";
                break;

            default:
                System.out.println("Invalid role selection!");
                return;
        }

        userDao.registerUser(username, password, role);
    }

    private static void login() {

        System.out.println("\n========== LOGIN ==========");

        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        String role = userDao.loginUser(username, password);

        if (role == null) {
            return;
        }

        System.out.println("Login successful!");
        System.out.println("Welcome, " + username + "!");
        System.out.println("Role: " + role);

        if (role.equalsIgnoreCase("ADMIN")) {
            adminMenu();
        } else if (role.equalsIgnoreCase("STUDENT")) {
            studentMenu();
        } else {
            System.out.println("Unrecognized user role.");
        }
    }

    private static void adminMenu() {

        while (true) {
            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1. View Class Schedules");
            System.out.println("2. Add Class Schedule");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    scheduleDao.displaySchedules();
                    break;

                case "2":
                    addSchedule();
                    break;

                case "3":
                    System.out.println("Admin logged out.");
                    return;

                default:
                    System.out.println("Invalid selection!");
            }
        }
    }

    private static void studentMenu() {

        while (true) {
            System.out.println("\n========== STUDENT MENU ==========");
            System.out.println("1. View Class Schedules");
            System.out.println("2. Logout");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    scheduleDao.displaySchedules();
                    break;

                case "2":
                    System.out.println("Student logged out.");
                    return;

                default:
                    System.out.println("Invalid selection!");
            }
        }
    }

    private static void addSchedule() {

        System.out.println("\n========== ADD CLASS SCHEDULE ==========");

        System.out.print("Subject Code: ");
        String code = sc.nextLine().trim();

        System.out.print("Subject Name: ");
        String subject = sc.nextLine().trim();

        System.out.print("Instructor Name: ");
        String instructor = sc.nextLine().trim();

        System.out.print("Day (e.g. Monday): ");
        String day = sc.nextLine().trim();

        System.out.print("Start Time (e.g. 08:00 AM): ");
        String start = sc.nextLine().trim();

        System.out.print("End Time (e.g. 10:00 AM): ");
        String end = sc.nextLine().trim();

        System.out.print("Room: ");
        String room = sc.nextLine().trim();

        System.out.print("Section: ");
        String section = sc.nextLine().trim();

        if (code.isEmpty() || subject.isEmpty()
                || instructor.isEmpty() || day.isEmpty()
                || start.isEmpty() || end.isEmpty()
                || room.isEmpty() || section.isEmpty()) {

            System.out.println("All fields are required.");
            return;
        }

        scheduleDao.addSchedule(
                code, subject, instructor, day,
                start, end, room, section
        );
    }
}
