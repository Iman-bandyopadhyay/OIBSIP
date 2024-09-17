import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String accountNumber;
    private double balance;
    private List<Transaction> transactionHistory;
    private Loan loan;

    public BankAccount(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
        this.loan = new Loan();
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdraw", amount));
            System.out.println("Withdrawal successful. New balance: " + balance);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
        System.out.println("Deposit successful. New balance: " + balance);
    }

    public void transfer(double amount, String recipientId) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Transfer to " + recipientId, amount));
            User recipient = User.getUserById(recipientId);
            if (recipient != null) {
                recipient.getBankAccount().receiveTransfer(amount, this.accountNumber);
            }
            System.out.println("Transfer successful. New balance: " + balance);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void receiveTransfer(double amount, String senderId) {
        balance += amount;
        transactionHistory.add(new Transaction("Transfer from " + senderId, amount));
    }

    public void applyLoan(double amount) {
        loan.applyLoan(amount);
        balance += amount; // Add loan amount to balance
        transactionHistory.add(new Transaction("Loan", amount));
        System.out.println("Loan applied successfully.");
        System.out.println("Loan Amount: " + loan.getLoanAmount());
        System.out.println("EMI: " + loan.getEMI());
        System.out.println("New balance: " + balance);
    }

    public void getTransactionHistory() {
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    public double getBalance() {
        return balance;
    }
}
