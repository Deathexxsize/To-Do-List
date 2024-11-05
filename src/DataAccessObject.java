import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataAccessObject {
    private static final String url = "jdbc:postgresql://localhost:6969/ToDoList";
    private static final String user = "postgres";
    private static final String password = "7482040607";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            // System.out.println("Успешно подключено!");
        } catch (SQLException error) {
            System.out.println("Ошибка при подлючении :(");
            error.printStackTrace();
        }
        return connection;
    }

    public static void addTask(String title, String description) { // Добавляеть задачу
        String sql = "INSERT INTO tasks (title, description, status) VALUES (?, ?, false)";

        try (Connection connection = connect();
             PreparedStatement pstmp = connection.prepareStatement(sql)) {

            pstmp.setString(1, title);
            pstmp.setString(2, description);
            pstmp.executeUpdate();

            System.out.println("\n== Задача успешно создано! ==");
        } catch (SQLException error) {
            System.out.println("Ошибка при подключений к базе данных");
            error.printStackTrace();
        }
    }
}
