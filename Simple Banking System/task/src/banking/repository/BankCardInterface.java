package banking.repository;

import banking.UserAccount;
import banking.bankcard.Card;

public interface BankCardInterface {

    void createAccount(UserAccount account);

    UserAccount loginAccount(Card userCard);

    void updateAccount (String cardNumber, int changeBalance);

    void deleteAccount (UserAccount account);

    boolean findAccount (String cardNumber);

    void close();
}
