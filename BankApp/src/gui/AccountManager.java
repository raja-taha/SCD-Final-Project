package gui;

import banking.BankAccount;

import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private static Map<String, BankAccount> accounts = new HashMap<>();

    public static boolean createAccount(String accountNumber, String accountHolder, double initialBalance) {
        if (!accounts.containsKey(accountNumber)) {
            BankAccount account = new BankAccount(accountNumber, accountHolder, initialBalance);
            accounts.put(accountNumber, account);
            return true;
        }
        return false;
    }

    public static BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public static Map<String, BankAccount> getAllAccounts() {
        return accounts;
    }
}

