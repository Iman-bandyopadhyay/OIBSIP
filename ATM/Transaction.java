import java.util.Date;

public class Transaction {
    private String transactionId;
    private String type;
    private double amount;
    private Date date;

    public Transaction(String type, double amount) {
        this.transactionId = generateTransactionId();
        this.type = type;
        this.amount = amount;
        this.date = new Date();
    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId + ", Type: " + type + ", Amount: " + amount + ", Date: " + date;
    }
}
