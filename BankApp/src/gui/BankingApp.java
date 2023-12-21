package gui;

import banking.BankAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BankingApp extends JFrame {
    private static Map<String, BankAccount> accounts = new HashMap<>();

    public BankingApp() {
        setTitle("Banking App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230)); // Light Blue color, you can change it to your desired bank-themed color
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Banking App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createAccountButton, gbc);

        JButton depositButton = new JButton("Deposit");
        depositButton.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(depositButton, gbc);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(withdrawButton, gbc);

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(checkBalanceButton, gbc);

        JButton showAllAccountsButton = new JButton("Show All Accounts");
        showAllAccountsButton.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(showAllAccountsButton, gbc);

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(exitButton, gbc);

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performTransaction("deposit");
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performTransaction("withdraw");
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        showAllAccountsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAllAccounts();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitApplication();
            }
        });
    }

    private void showAllAccounts() {
        if (AccountManager.getAllAccounts().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No accounts found.");
        } else {
            StringBuilder allAccountsInfo = new StringBuilder("All Accounts:\n");
            for (Map.Entry<String, BankAccount> entry : AccountManager.getAllAccounts().entrySet()) {
                allAccountsInfo.append("Account Number: ").append(entry.getKey()).append("\n");
                allAccountsInfo.append(entry.getValue()).append("\n---------------\n");
            }
            JOptionPane.showMessageDialog(this, allAccountsInfo.toString());
        }
    }

    private void createAccount() {
        try {
            String accountNumber = validateAccountNumber();
            if (accountNumber != null) {
                if (AccountManager.getAccount(accountNumber) != null) {
                    showErrorDialog("Account already exists.");
                } else {
                    String accountHolder = validateAccountHolder();
                    String initialBalanceStr = validateInitialBalance();
                    if (accountHolder != null && initialBalanceStr != null) {
                        double initialBalance = Double.parseDouble(initialBalanceStr);
                        if (initialBalance >= 0) {
                            if (AccountManager.createAccount(accountNumber, accountHolder, initialBalance)) {
                                showSuccessDialog("Account created successfully.");
                            } else {
                                showErrorDialog("Failed to create account.");
                            }
                        } else {
                            showErrorDialog("Initial balance should be greater than or equal to zero.");
                        }
                    }
                }
            }
        } catch (NumberFormatException ex) {
            showErrorDialog("Invalid initial balance. Please enter a valid number.");
        }
    }

private String validateAccountNumber() {
    String accountNumber;
    do {
        accountNumber = JOptionPane.showInputDialog(this, "Enter account number:");
        if (accountNumber == null) {
            return null;  // User clicked Cancel
        }
        if (!accountNumber.isEmpty() && accountNumber.matches("[a-zA-Z0-9]+")) {
            return accountNumber;
        } else {
            showErrorDialog("Invalid account number. Please enter a non-empty string containing letters or digits.");
        }
    } while (true);
}


    private String validateAccountHolder() {
    String accountHolder;
    do {
        accountHolder = JOptionPane.showInputDialog(this, "Enter account holder name:");
        if (accountHolder == null) {
            return null;  // User clicked Cancel
        }
        if (accountHolder.matches("[a-zA-Z]+")) {
            return accountHolder;
        } else {
            showErrorDialog("Invalid account holder name. Please enter a non-empty string containing only alphabets.");
        }
    } while (true);
}

private String validateInitialBalance() {
    do {
        String initialBalanceStr = JOptionPane.showInputDialog(this, "Enter initial balance:");
        if (initialBalanceStr == null) {
            return null;  // User clicked Cancel
        }
        try {
            double initialBalance = Double.parseDouble(initialBalanceStr);
            if (initialBalance >= 100 && initialBalance <= 1000000) {
                return initialBalanceStr;
            } else {
                showErrorDialog("Initial balance should be between $100 and $1,000,000.");
            }
        } catch (NumberFormatException ex) {
            showErrorDialog("Invalid initial balance. Please enter a valid number.");
        }
    } while (true);
}

private void performTransaction(String transactionType) {
    try {
        String accountNumber = validateAccountNumber();
        if (accountNumber != null) {
            BankAccount account = AccountManager.getAccount(accountNumber);
            if (account != null) {
                String amountStr = JOptionPane.showInputDialog(this, "Enter amount to " + transactionType + ":");
                if (amountStr == null) {
                    return;  // User clicked Cancel
                }
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    showErrorDialog("Invalid amount. Please enter a positive value.");
                } else {
                    if (transactionType.equals("deposit")) {
                        TransactionManager.deposit(account, amount);
                    } else {
                        TransactionManager.withdraw(account, amount);
                    }
                }
            } else {
                showErrorDialog("Account not found.");
            }
        }
    } catch (NumberFormatException ex) {
        showErrorDialog("Invalid amount. Please enter a valid number.");
    }
}

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void checkBalance() {
        String accountNumber = validateAccountNumber();
        if (accountNumber != null) {
            BankAccount account = AccountManager.getAccount(accountNumber);
            if (account != null) {
                JOptionPane.showMessageDialog(this,
                        "Account Holder: " + account.getAccountHolder() +
                                "\nAccount Balance: $" + account.getBalance());
            } else {
                showErrorDialog("Account not found.");
            }
        }
    }

    private void exitApplication() {
        System.out.println("Exiting the banking app. Goodbye!");
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BankingApp();
            }
        });
    }
}