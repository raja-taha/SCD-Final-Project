package banking;

import javax.swing.JOptionPane;

public class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private static final double MAX_DEPOSIT_AMOUNT = 1000000;

    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            showErrorDialog("Invalid deposit amount. Please enter a positive value.");
            return;
        }
        
        if (amount + balance > MAX_DEPOSIT_AMOUNT) {
            showErrorDialog("Deposit amount exceeds the maximum limit of $1,000,000. Deposit failed.");
            return;
        }
        
        balance += amount;
        showSuccessDialog("Deposit successful. Current balance: $" + balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            showErrorDialog("Invalid withdrawal amount. Please enter a positive value.");
            return;
        }
        if (amount > balance) {
            showErrorDialog("Insufficient funds. Withdrawal failed.");
        } else {
            balance -= amount;
            showSuccessDialog("Withdrawal successful. Current balance: $" + balance);
        }
    }

    @Override
    public String toString() {
        return "Account Holder: " + accountHolder + "\nAccount Balance: $" + balance;
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
