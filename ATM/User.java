import java.util.HashMap;
import java.util.Map;

public class User {
    private static Map<String, User> users = new HashMap<>();
    private String userId;
    private String userPin;
    private BankAccount bankAccount;

    public User(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.bankAccount = new BankAccount(userId);
        users.put(userId, this);
    }

    public static User validateUser(String userId, String userPin) {
        User user = users.get(userId);
        if (user != null && user.userPin.equals(userPin)) {
            return user;
        }
        return null;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public static User createUser(String userId, String userPin) {
        if (users.containsKey(userId)) {
            return null; // User already exists
        }
        return new User(userId, userPin);
    }

    public static User getUserById(String userId) {
        return users.get(userId);
    }
}
