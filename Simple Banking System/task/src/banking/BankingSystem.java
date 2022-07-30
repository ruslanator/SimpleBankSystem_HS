package banking;

import banking.bankcard.Card;
import banking.bankcard.CardGenerator;
import banking.repository.BankCardInterface;
import banking.repository.BankCardRepository;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BankingSystem {

    private static final String cardNumber = "400000";
    private boolean isExit = false;
    private static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();
    private static Connection connection = null;
    private static Statement statement = null;
    private BankCardInterface dataBase;

    public void start(String dataBaseName) {
        dataBase = new BankCardRepository(dataBaseName);

        while (!isExit) {
            mainConsoleMenu();
            int action = sc.nextInt();

            switch (action) {
                case 1:
                    registerCard();
                    break;
                case 2:
                    loginInBankSystem();
                    break;
                case 0:
                    System.out.println("Bye!");
                    isExit = true;
                    break;
                default:
            }
            if (isExit) {
                dataBase.close();
            }
        }


    }

    private void loginInBankSystem() {

        System.out.println("Enter your card number:");
        String cardNumber = sc.nextLine();
        cardNumber = sc.nextLine();
        System.out.println("Enter your PIN:");
        String PIN = sc.nextLine();

        UserAccount account = dataBase.loginAccount(new Card(cardNumber, PIN));

        if(account != null) {
            System.out.println("You have successfully logged in!");
            boolean isLogout = false;
            while (!isLogout) {
                loginConsoleMenu();
                int loginAction = sc.nextInt();
                sc.nextLine();

                switch (loginAction) {
                    case 1:
                        System.out.println("Balance: " + account.getBalance());
                        break;
                    case 2:
                        addIncome(account);
                        break;
                    case 3:
                        doTransfer(account);
                        break;
                    case 4:
                        dataBase.deleteAccount(account);
                        isLogout = true;
                        break;
                    case 5:
                        System.out.println("You have successfully logged out!");
                        isLogout = true;
                        break;
                    case 0:
                        System.out.println("\nBye!");
                        isExit = true;
                        return;
                    default:
                }
            }
        } else {
            System.out.println("Wrong card number or PIN!");
        }
    }

    private void doTransfer(UserAccount account) {
        System.out.println("Enter card number:");
        String recipientCard = sc.nextLine();
        if(recipientCard.equals(account.getCard().getCardNumber())) {
            System.out.println("You can't transfer money to the same account!");
        }
        if(!CardGenerator.checkCardNumber(recipientCard)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        }
        if(!dataBase.findAccount(recipientCard)) {
            System.out.println("Such a card does not exist.");
        } else {
            System.out.println("Enter how much money you want to transfer:");
            int transferMoney = sc.nextInt();
            if(transferMoney > account.getBalance()) {
                System.out.println("Not enough money!");
            } else {
                dataBase.updateAccount(account.getCard().getCardNumber(), -transferMoney);
                account.updateBalance(-transferMoney);
                dataBase.updateAccount(recipientCard, transferMoney);
            }
        }
    }

    private void registerCard() {
        UserAccount account = new UserAccount(CardGenerator.createCard());
        dataBase.createAccount(account);
        System.out.println("Your card has been created\n" +
                "Your card number:\n" + account.getCard().getCardNumber() +"\n" +
                "Your card PIN:\n" + account.getCard().getPIN());
    }

    private void addIncome(UserAccount account) {
        System.out.println("Enter income:");
        int income = sc.nextInt();
        dataBase.updateAccount(account.getCard().getCardNumber(),income);
        account.updateBalance(income);
    }

    public void mainConsoleMenu() {
        System.out.println(
                "1. Create account\n" +
                "2. Log into account\n" +
                "0. Exit\n");
    }

    public void loginConsoleMenu() {
        System.out.println(
                "1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit\n");
    }
}
