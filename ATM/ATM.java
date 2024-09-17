import java.util.Scanner;

public class ATM {
    private static final Scanner sc = new Scanner(System.in);
    private static User currentUser;

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the ATM");
            System.out.println("1. Login");
            System.out.println("2. Create New Account");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (option) {
                case 1 -> login();
                case 2 -> createNewAccount();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void login() {
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();
        System.out.print("Enter PIN: ");
        String userPin = sc.nextLine();

        currentUser = User.validateUser(userId, userPin);
        if (currentUser != null) {
            showMenu();
            System.out.println();
        } else {
            System.out.println("Invalid User ID or PIN.\n");
            System.out.println();
        }
    }

    private static void createNewAccount() {
        System.out.print("Enter New User ID: ");
        String newUserId = sc.nextLine();
        String newUserPin;

        while (true) {
            System.out.print("Enter New PIN (4 digits): ");
            newUserPin = sc.nextLine();
            if (newUserPin.matches("\\d{4}")) {
                break;
            } else {
                System.out.println("Invalid PIN. Please enter a 4-digit PIN.");
            }
        }

        currentUser = User.createUser(newUserId, newUserPin);
        if (currentUser != null) {
            System.out.println("Account created successfully.\n");
            System.out.println();
        } else {
            System.out.println("Failed to create account. User ID may already exist.\n");
            System.out.println();
        }
    }

    private static void showMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println();
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Loan");
            System.out.println("6. Display Balance");
            System.out.println("7. Quit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> currentUser.getBankAccount().getTransactionHistory();
                case 2 -> {
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = sc.nextDouble();
                    currentUser.getBankAccount().withdraw(withdrawAmount);
                }
                case 3 -> {
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = sc.nextDouble();
                    currentUser.getBankAccount().deposit(depositAmount);
                }
                case 4 -> {
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = sc.nextDouble();
                    System.out.print("Enter recipient User ID: ");
                    String recipientId = sc.next();
                    currentUser.getBankAccount().transfer(transferAmount, recipientId);
                }
                case 5 -> {
                    System.out.print("Enter loan amount: ");
                    double loanAmount = sc.nextDouble();
                    currentUser.getBankAccount().applyLoan(loanAmount);
                }
                case 6 -> System.out.println("Current Balance: " + currentUser.getBankAccount().getBalance());
                case 7 -> exit = true;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
