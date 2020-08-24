package jam.example.archiver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс для проверки параметров и выбора действий
 */
public class ManageArch {
    /**
     * @param args - массив строк, содержащих параметры, указанные при запуске программы
     *             деархивация - присутствует ключ '-х' и после ключа указано имя архива
     *             архивация - в предпоследнем элементе массива должно быть ':', в таком случае из последнего элемента получаю имя создаваемого архива
     */
    public void selectAction(String[] args) {
        final List<String> srcFiles = new ArrayList<>();
        if (args[args.length - 2].equals(":") && (!args[0].equals("-x"))) {

            getListParams(args, srcFiles);
            ArchFiles archFiles = new ArchFiles();
            archFiles.arch(srcFiles, getOutFile(args[args.length - 1]));
        } else if (args[0].equals("-x") && !args[1].isEmpty()) {
            DeArchFiles deArchFiles = new DeArchFiles();
            deArchFiles.deArch(args[1], getUserDir());
        }
    }

    /**
     * @return - возвращает имя директории из которой запущена программа
     */
    private String getUserDir() {
        return System.getProperty("user.dir");
    }

    /**
     * @param name - имя файла без расширения, указанный в параметрах при запуске программы
     * @return - имя архива с расширением
     */
    private String appendExtension(String name) {
        return name.concat(".arch");

    }

    /**
     * @param fileFromArgs - имя файла, указанное при запуске программы
     * @return - строка содержит имя архива и его расположение
     */
    private String getOutFile(String fileFromArgs) {
        Path pathOut = Paths.get(getUserDir());
        Path pathFile = Paths.get(appendExtension(fileFromArgs));
        return pathOut.resolve(pathFile).toString();
    }

    /**
     * @param args     - массив параметров, указанных при запуске программы
     * @param srcFiles - список файлов и директорий, которые необходимо поместить в архив
     */
    private static void getListParams(String[] args, List<String> srcFiles) {
        srcFiles.addAll(Arrays.asList(args).subList(0, args.length - 2));
    }

}
