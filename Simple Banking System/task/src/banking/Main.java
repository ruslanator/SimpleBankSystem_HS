package banking;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String dataBaseName = "db.s3db";
        if ((args.length == 2) && (args[0].equals("-fileName"))) {
            dataBaseName = args[1];
        }
        new BankingSystem().start(dataBaseName);
    }
}

