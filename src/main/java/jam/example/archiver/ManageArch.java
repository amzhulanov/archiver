package jam.example.archiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для проверки параметров и выбора действий
 */
public class ManageArch {
    /**
     *
     * @param args - массив строк, содержащих параметры, указанные при запуске программы
     * деархивация - присутствует ключ '-х' и после ключа указано имя архива
     * архивация - в предпоследнем элементе массива должно быть ':', в таком случае из последнего элемента получаю имя создаваемого архива
     */
    public void selectAction(String[] args) {

        final List<String> srcFiles = new ArrayList<>();
        if (args[args.length - 2].equals(":") && (!args[0].equals("-x"))) {

            getListParams(args, srcFiles);
            ArchFiles archFiles = new ArchFiles();
            archFiles.arch(srcFiles, args[args.length - 1],getUserDir());
        } else if (args[0].equals("-x") && !args[1].isEmpty()) {
            DeArchFiles deArchFiles = new DeArchFiles();
            deArchFiles.deArch(args[1],getUserDir());
        }
    }

    /**
     * @return - каталог из которого запущена программа
     */
    private String getUserDir() {
        return System.getProperty("user.dir");
    }

    /**
     *
     * @param args - массив параметров, указанных при запуске программы
     * @param srcFiles - список файлов и директорий, которые необходимо поместить в архив
     */
    private static void getListParams(String[] args, List<String> srcFiles) {
        //todo Добавить валидность данных, начинаться должны с ./ ? или не должны
        //todo Для Архивации должно быть указано имя архива. Ели нет, то выдать сообщение
        for (int n = 0; n < args.length - 2; n++) {
            srcFiles.add(args[n]);
        }
    }
}
