package banking;

import banking.bankcard.Card;

public class UserAccount {

    private Card card;
    private int balance;

    public UserAccount(Card card, int balance) {
        this.card = card;
        this.balance = balance;
    }

    public UserAccount(Card card) {
        this.card = card;
        balance = 0;
    }

    public Card getCard() {
        return card;
    }

    public int getBalance() {
        return balance;
    }

    public void updateBalance(int balance) {
        this.balance += balance;
    }
}
