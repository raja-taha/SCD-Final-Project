package gui;

import banking.BankAccount;

public class TransactionManager {
    public static void deposit(BankAccount account, double amount) {
        account.deposit(amount);
    }

    public static void withdraw(BankAccount account, double amount) {
        account.withdraw(amount);
    }
}
