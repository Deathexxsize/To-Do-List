import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================");
        System.out.println("      To-Do List App      ");
        System.out.println("==========================");

        System.out.println("\n1. Добавить задачу\n2. Просмотреть все задачи\n3. Обновить задачу\n4. Удалить задачу");

        System.out.println("\n==========================");
        System.out.print("Выберите опцию (1-4): ");
        byte userChoose = scanner.nextByte();
        switch (userChoose) {
            case 1:
                addTask();
                break;
        }

    }

    public static void addTask () {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\n\n===========================");
        System.out.println("      Добавить задачу      ");
        System.out.println("===========================");

        System.out.print("\nВведите название задачи: ");
        String task = scanner.nextLine();

        System.out.println("Введите описание задачи:");
        String description = scanner.nextLine();

        DataAccessObject.addTask(task, description);
    }
}