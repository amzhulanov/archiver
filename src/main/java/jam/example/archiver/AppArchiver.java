package jam.example.archiver;

/**
 * Основной класс для запуска программы
 */
public class AppArchiver {
    /**
     * Метод main принимает параметры и передаёт их дальше для обработки
     *
     * @param args - параметры из командной строки
     */
    public static void main(String[] args) {
        ManageArch manageArch = new ManageArch();
        manageArch.selectAction(args);
    }

}
