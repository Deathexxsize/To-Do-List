import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menu();
    }

    public static void menu() {

        System.out.println("\n\n==========================");
        System.out.println("      To-Do List App      ");
        System.out.println("       главное меню       ");
        System.out.println("==========================");

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n1. Добавить задачу\n2. Просмотреть все задачи\n3. Обновить задачу\n4. Удалить задачу");

        System.out.println("\n==========================");
        System.out.print("Выберите опцию (1-4): ");
        byte userChoose = scanner.nextByte();
        switch (userChoose) {
            case 1:
                addTask();
                break;
            case 2:
                seeTask();
                break;
        }
    }

    public static void addTask () { // Добавляет задачу
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

    public static void seeTask() {
        DataAccessObject.seeTask();
    }
}