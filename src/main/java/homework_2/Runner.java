package homework_2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Runner {
    public static void main(String[] args) {
        try (Connection connection = DBConnector.getConnectionToDB()) {
            DBConnector.statement = connection.createStatement();
            DBConnector.createDbTables();

            DBConnector.addUser("Andrew", "1111");
            DBConnector.addUser("James", "222");
            DBConnector.addUser("Alice", "333");

            DBConnector.addPost("Hello bro", 1);
            DBConnector.addPost("It is me", 3);
            DBConnector.addPost("Where am I", 2);
            DBConnector.addPost("New post by Alice", 3);

            DBConnector.addComment("HI", 1, 2);
            DBConnector.addComment("You are here", 3, 1);
            DBConnector.addComment("OK", 2, 3);
            DBConnector.addComment("Like it!", 3, 2);

            DBConnector.addLike(1, 3, 0);
            DBConnector.addLike(1, 0, 4);
            DBConnector.addLike(2, 0, 4);
            DBConnector.addLike(3, 3, 0);

            DBConnector.printStatistics();
            DBConnector.userInfo(1);

        } catch (IOException c) {
            c.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException("SQL error");
        }
    }
}
