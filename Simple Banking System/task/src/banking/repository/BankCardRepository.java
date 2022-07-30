package banking.repository;

import banking.UserAccount;
import banking.bankcard.Card;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class BankCardRepository implements BankCardInterface {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS card(" +
                                                    "id INTEGER PRIMARY KEY," +
                                                    "number TEXT," +
                                                    "pin TEXT," +
                                                    "balance INTEGER DEFAULT 0)";
    private static final String ADD_ACCOUNT = "INSERT INTO card (number, pin) VALUES (?, ?)";
    private static final String SIGN_IN = "SELECT number, pin, balance FROM CARD WHERE number = ? AND pin = ?";
    private static final String FIND_ACCOUNT = "SELECT number, pin, balance FROM CARD WHERE number = ?";
    private static final String UPDATE_ACCOUNT = "UPDATE card SET balance = balance + ? WHERE number = ?";
    private static final String DELETE_ACCOUNT = "DELETE FROM card WHERE number = ?";

    private SQLiteDataSource dataSource;
    private Connection connection;

    public BankCardRepository(String DBname) {
        String dataBaseURL = "jdbc:sqlite:" + DBname;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(dataBaseURL);
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createTable();
    }

    private void createTable() {
        try (Statement statement  = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createAccount(UserAccount account) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_ACCOUNT)) {
            preparedStatement.setString(1, account.getCard().getCardNumber());
            preparedStatement.setString(2, account.getCard().getPIN());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserAccount loginAccount(Card userCard) {
        UserAccount account = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SIGN_IN)) {
            preparedStatement.setString(1, userCard.getCardNumber());
            preparedStatement.setString(2, userCard.getPIN());
            ResultSet resultSet = preparedStatement.executeQuery();
            int balance = resultSet.getInt("balance");
            account = new UserAccount(userCard, balance);
            if(resultSet.next()) {
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public void updateAccount(String cardNumber, int changeBalance) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT)) {
            ps.setString(1, Integer.toString(changeBalance));
            ps.setString(2, cardNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(UserAccount account) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACCOUNT)) {
            preparedStatement.setString(1, account.getCard().getCardNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean findAccount(String cardNumber) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ACCOUNT)) {
            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
