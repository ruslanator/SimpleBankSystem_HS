package banking;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static int count = 0;
    private static String[] cards = new String[10];
    private static int[] PINS = new int[10];
    private static String cardNumber = "400000";
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        mainMenu();
    }
    public static String createCard() {
        int firstRandomNum = random.nextInt(99 - 10) + 10;
        int secondRandomNum = random.nextInt(9999 - 1000) + 1000;
        int thirdRandomNum =  random.nextInt(9999 - 1000) + 1000;
        String card = cardNumber + firstRandomNum + secondRandomNum + thirdRandomNum;
        return card;
    }
    public static int generatePIN() {
        int min = 1000;
        int max = 9999;
        return random.nextInt(max - min) + min;
    }
    public static void accountMenu() {
        System.out.println("1. Balance\n" +
                "2. Log out\n" +
                "0. Exit");
        switch (sc.nextInt()) {
            case 1:
                System.out.println("0");
                accountMenu();
                break;
            case 2:
                System.out.println("You have successfully logged out!");
                mainMenu();
                break;
            case 0:
                System.out.println("Bye!");
                break;
        }
    }
    public static void mainMenu() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");

        switch (sc.nextInt()){
            case 1:
                cards[count] = createCard();
                PINS[count] = generatePIN();
                System.out.println("Your card has been created\n" +
                        "Your card number: \n" + cards[count] + "\n" +
                        "Your card PIN: \n" + PINS[count]);
                System.out.println();
                count += 1;
                mainMenu();
                break;
            case 2:
                System.out.println("Enter your card number: ");
                String cardNumber = sc.nextLine();
                cardNumber = sc.nextLine();
                System.out.println("Enter your PIN: ");
                int PIN = sc.nextInt();
                boolean cardPresent = false;
                for (int i = 0; i < cards.length; i++) {
                    if(cardNumber.equals(cards[i]) & PIN == PINS[i]){
                        cardPresent = true;
                    }
                }
                if(cardPresent) {
                    System.out.println("You have successfully logged in!");
                    accountMenu();
                } else {
                    System.out.println("Wrong card number or PIN!");
                    mainMenu();
                }
                break;
            case 0:
                System.out.println("Bye!");
                break;
        }
    }
}