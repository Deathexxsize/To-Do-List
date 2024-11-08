import java.sql.*;
import java.util.Scanner;

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
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();

            System.out.println("\n== Задача успешно создано! ==");
            Main.menu();
        } catch (SQLException error) {
            System.out.println("Ошибка при подключений к базе данных");
            error.printStackTrace();
        }
    }

    public static void seeTask () { // Просмотр всех задач

        String query = "SELECT title, description, status FROM tasks";

        try (Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        ResultSet resultSet = preparedStatement.executeQuery();

            Scanner scanner = new Scanner(System.in);

            int num = 1;
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String status = resultSet.getString("status");

                String progress = "";

                if (status.equals("false")) {
                    progress = "[❌] Не выполнено";
                } else if (status.equals("true")){
                    progress = "[✅] Выполнено";
                }

                System.out.println("\n========================================");
                System.out.println("|            "+num+" Задача               |");
                System.out.println("========================================");
                System.out.println("| Название    : "+title+"               ");
                System.out.println("| Описание    : "+description+"         ");
                System.out.println("|                                       ");
                System.out.println("| Статус      : "+progress+"            ");
                System.out.println("========================================");

                num++;
            }

            if (Main.isCalledFrom("deleteTask")) {
                System.out.print("\nВведите название задачи которую нужно удалить: ");
                String choose = scanner.nextLine();

                deleteTask(choose);
            } else {
                continueSeeTask();
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    private static void continueSeeTask() { // продложение выше стоящего метода
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\n0. Вернуться назад: ");
            byte check = scanner.nextByte();

            switch (check) {
                case 0:
                    Main.menu();
                    break;
                default:
                    System.out.println("⚠ Введите корректное значение ⚠");
                    break;
            }
        }

    }

    public static void updateTask(String choose) { // Метод для обновления задачи
        Scanner scanner = new Scanner(System.in);

        System.out.print("1. Изменить название \n2. Изменить описание \n3. Изменить статус");
        System.out.print("\nВыбрать: ");
        byte num = scanner.nextByte();
        scanner.nextLine(); // Добавляем после nextByte, чтобы избежать пропуска

        String update = "";
        String newTask = "";
        boolean status = false; // Для статуса

        try (Connection connection = connect()) {

            if (num == 1) {
                update = "UPDATE tasks SET title = ? WHERE title = ?";
                System.out.print("Введите новое название: ");
                newTask = scanner.nextLine();

            } else if (num == 2) {
                update = "UPDATE tasks SET description = ? WHERE title = ?";
                System.out.print("Введите новое описание: ");
                newTask = scanner.nextLine();

            } else if (num == 3) {
                update = "UPDATE tasks SET status = ? WHERE title = ?";
                System.out.print("Введите новый статус (Выполнено / Не выполнено): ");
                String statusInput = scanner.nextLine();

                if (statusInput.equalsIgnoreCase("Выполнено")) {
                    status = true;
                } else if (statusInput.equalsIgnoreCase("Не выполнено")) {
                    status = false;
                } else {
                    System.out.println("Неверный ввод для статуса. Укажите 'Выполнено' или 'Не выполнено'.");
                    return;
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(update)) {

                if (num == 3) {
                    preparedStatement.setBoolean(1, status);
                } else {
                    preparedStatement.setString(1, newTask);
                }

                preparedStatement.setString(2, choose);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Задача успешно обновлена!");
                } else {
                    System.out.println("Задача не найдена.");
                }

            }

        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    public static void deleteTask(String choose) { // Удаление задачи

        String delete = "DELETE FROM tasks WHERE title = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {

            preparedStatement.setString(1, choose);

            int rowAffected = preparedStatement.executeUpdate();
            System.out.println("\nЗадача \"" + choose + "\" успешно удалено");

            continueSeeTask();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
