package banking.bankcard;

public class Card {
    private String cardNumber;
    private String PIN;

    public Card(String cardNumber, String PIN) {
        this.cardNumber = cardNumber;
        this.PIN = PIN;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPIN() {
        return PIN;
    }




}
