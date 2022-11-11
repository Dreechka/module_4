package homework_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DBConnector {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "1973";
    public static Statement statement;

    public static Connection getConnectionToDB() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /* 1.1. Создать таблицу user - поля id, name (уникальный, не нулевой), password (не нулевой), created_at (дата время)
    1.2. Создать таблицу post - поля id, text (не нулевой), created_at (дата время), user_id (не нулевой)
    1.3. Создать таблицу comment - поля id, text, post_id (не нулевой), user_id (не нулевой), created_at (дата время)
    1.4. Создать таблицу like - поля id, user_id (не нулевой), post_id, comment_id,
    ограничение - при вставке нового значения, пустым может быть только одно из двух полей
    ВСЕ sql скрипты, которые не меняют свои значения, во время работы программы, должны храниться в файлах.*/

    public static void createDbTables() throws SQLException, IOException {
        String fileName = "create_tables.sql";
        InputStream resourceAsStream = DBConnector.class.getClassLoader().getResourceAsStream(fileName);
        assert resourceAsStream != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        String query = bufferedReader.lines().collect(Collectors.joining(""));
        statement.execute(query);
        bufferedReader.close();
        resourceAsStream.close();
    }

    /*Написать методы, которые будут добавлять новые значения в каждую из таблиц.*/
    public static void addUser(String name, String password) throws SQLException {
        String query = String.format("INSERT INTO public.user (name, created_at, password) VALUES ('%s', current_timestamp, '%s');",
                name, password);
        statement.execute(query);
    }

    public static void addPost(String text, int user_id) throws SQLException {
        String query = String.format("INSERT INTO post (text, created_at, user_id) VALUES ('%s', current_timestamp, '%d');",
                text, user_id);
        statement.execute(query);
    }

    public static void addComment(String text, int post_id, int user_id) throws SQLException {
        String query = String.format("INSERT INTO comment (text, post_id, user_id, created_at) VALUES ('%s', '%d', '%d', current_timestamp);",
                text, post_id, user_id);
        statement.execute(query);
    }

    public static void addLike(int user_id, int post_id, int comment_id) throws SQLException {
        if (post_id == 0 && comment_id != 0) {
            String query = String.format("INSERT INTO public.like (user_id, comment_id) VALUES ('%d', '%d');",
                    user_id, comment_id);
            statement.execute(query);
        }
        if (comment_id == 0 && post_id != 0) {
            String query = String.format("INSERT INTO public.like (user_id, post_id) VALUES ('%d', '%d');",
                    user_id, post_id);
            statement.execute(query);
        }
    }

    /*3. Написать метод, который выдает на экран статистику:
    Всего: количество пользователей - (число),  количество постов - (), количество комментариев - (),
    количество лайков  - ()*/
    public static void printStatistics() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT count(*)\n" +
                "FROM public.user\n" +
                "UNION all\n" +
                "SELECT count(*)\n" +
                "FROM post\n" +
                "UNION all\n" +
                "SELECT count(*)\n" +
                "FROM comment\n" +
                "UNION all\n" +
                "SELECT count(*)\n" +
                "FROM public.like;");
        ArrayList<Integer> count = new ArrayList<>();
        while (resultSet.next()) {
            count.add(resultSet.getInt(1));
        }
        System.out.printf("Всего: количество пользователей - %d, количество постов - %d, количество комментариев - %d, количество лайков  - %d \n",
                count.get(0), count.get(1), count.get(2), count.get(3));
    }

    /*  4. Написать метод, который выводит всю информацию по пользователю (передается id):
    Пользователь - (имя пользователя)
    Дата создания - ()
    Самый первый пост - ()
    Количество комментов - ()
            4.1 Если пользователь не найден то вывод:
    Пользователь не найден*/
    public static void userInfo(int id) throws SQLException {
        String query = String.format("SELECT u.name, u.created_at,\n" +
                "(SELECT created_at FROM post WHERE user_id = %d GROUP BY  created_at limit 1),\n" +
                "(SELECT count(*) FROM comment WHERE user_id = %d GROUP BY  user_id)\n" +
                "FROM public.user u\n" +
                "WHERE id = %d;", id, id, id);
        ResultSet resultSet = statement.executeQuery(query);
        if (!resultSet.next()) {
            System.out.println("Пользователь не найден");
        } else {
            String name = resultSet.getString(1);
            Timestamp created_at = resultSet.getTimestamp(2);
            Timestamp firstPost = resultSet.getTimestamp(3);
            int comentsCount = resultSet.getInt(4);
            System.out.printf("Пользователь - %s\n" +
                    "Дата создания - %s\n" +
                    "Самый первый пост - %s\n" +
                    "Количество комментов - %d", name, created_at, firstPost, comentsCount);
        }
    }

}
