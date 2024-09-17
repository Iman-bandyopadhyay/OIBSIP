import java.util.*;
import java.util.concurrent.*;

class User {
    String username;
    String password;
    String profile;

    User(String username, String password, String profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }
}

public class OnlineExamination {
    private static Map<String, User> users = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    private static int score = 0;

    public static void main(String[] args) {
        // Adding some users for testing
        users.put("user1", new User("user1", "pass1", "User One"));
        users.put("user2", new User("user2", "pass2", "User Two"));

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                login();
                if (currentUser != null) {
                    showMenu();
                }
            } else if (choice == 2) {
                signUp();
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).password.equals(password)) {
            currentUser = users.get(username);
            System.out.println("Login successful. Welcome, " + currentUser.profile + "!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void signUp() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Try a different one.");
            return;
        }
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();
        System.out.print("Enter profile name: ");
        String profile = scanner.nextLine();

        users.put(username, new User(username, password, profile));
        System.out.println("Sign up successful. You can now log in.");
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\n1. Update Profile");
            System.out.println("2. Update Password");
            System.out.println("3. Start Exam");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                updateProfile();
            } else if (choice == 2) {
                updatePassword();
            } else if (choice == 3) {
                startExam();
            } else if (choice == 4) {
                logout();
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void updateProfile() {
        System.out.print("Enter new profile name: ");
        String newProfile = scanner.nextLine();
        currentUser.profile = newProfile;
        System.out.println("Profile updated successfully.");
    }

    private static void updatePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        currentUser.password = newPassword;
        System.out.println("Password updated successfully.");
    }

    private static void startExam() {
        System.out.print("Per question only 20 seconds will be alloted !!\n");
        String[] questions = {
            "What is the capital of France?",
            "What is 2 + 2?",
            "What is the color of the sky?"
        };
        String[][] options = {
            {"1. Paris", "2. London", "3. Rome", "4. Berlin"},
            {"1. 3", "2. 4", "3. 5", "4. 6"},
            {"1. Blue", "2. Green", "3. Red", "4. Yellow"}
        };
        int[] answers = {1, 2, 1};

        score = 0;
        int questionTime = 20; // 20 seconds per question

        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            for (String option : options[i]) {
                System.out.println(option);
            }

            final int questionIndex = i;
            final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            final Future<?> questionFuture = scheduler.schedule(() -> {
                System.out.println("\nTime's up for this question! Auto-submitting your answer.");
                System.out.println("Moving to the next question...");
            }, questionTime, TimeUnit.SECONDS);

            long startTime = System.currentTimeMillis();
            boolean answered = false;

            while (!answered && !questionFuture.isDone()) {
                System.out.print("Enter your answer (1-4): ");
                if (scanner.hasNextInt()) {
                    int userAnswer = scanner.nextInt();
                    if (userAnswer >= 1 && userAnswer <= 4) {
                        if (userAnswer == answers[questionIndex]) {
                            score++;
                        }
                        answered = true;
                        questionFuture.cancel(true);
                    } else {
                        System.out.println("Invalid option. Please enter a number between 1 and 4.");
                    }
                } else {
                    scanner.next(); // Consume invalid input
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                }

                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                if(questionTime>elapsedTime){
                    System.out.println("Time remaining: " + (questionTime - elapsedTime) + " seconds");
                }
            }

            scheduler.shutdown();
        }

        System.out.println("Exam finished. Your score is: " + score + "/" + questions.length);
    }

    private static void logout() {
        currentUser = null;
        System.out.println("Logged out successfully.");
    }
}
