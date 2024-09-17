public class Loan {
    private double loanAmount;
    private double interestRate;
    private double emi;

    public Loan() {
        this.interestRate = 0.05; // Example interest rate
    }

    public void applyLoan(double amount) {
        this.loanAmount = amount;
        this.emi = calculateEMI(amount, interestRate, 12); // Example for 12 months
    }

    private double calculateEMI(double principal, double rate, int months) {
        double monthlyRate = rate / 12;
        return (principal * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getEMI() {
        return emi;
    }
}
