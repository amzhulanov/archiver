import jam.example.archiver.ManageArch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Класс для тестирования ДеАрхиватора
 * Перед запуском тестов создаю архивный файл и выполняю его разархивирование
 * Проверяю, что список файлов в директории совпадает со списком файлов в архиве.
 * Проверяю расширение архива
 * Проверяю установленный метод компрессии
 */
class DeArchTest {
    private final String PATH = "src/test/resources";
    private final String FILE_NAME = "archived";
    private final String EXTENSION = ".arch";
    private final ManageArch manageArch = new ManageArch();
    private final File fileArch=new File(FILE_NAME+EXTENSION);;
    private final ArrayList<String> listFiles=new ArrayList<>();
    private final String[] fileSource={"test.file","test2.file","dir1","dir2"};


    @BeforeEach
    void generateDeArch() throws IOException {
        if (fileArch.exists()) {
            fileArch.delete();
        }
        createArch();

        if (fileArch.exists()) {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileArch));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                listFiles.add(entry.getName());
            }
            String[] args = {"-x", FILE_NAME+EXTENSION};
            manageArch.selectAction(args);
        }
    }

    private void createArch() {

        String[] args=new String[fileSource.length+2];
        for (int i=0;i<fileSource.length;i++) {
            args[i]=Paths.get(PATH).resolve(Paths.get(fileSource[i])).toString();
        }
        args[fileSource.length]=":";
        args[fileSource.length+1]=FILE_NAME;
        manageArch.selectAction(args);
    }

    @Test
    void existsArchTest() {
        assertThat(fileArch.exists()).isTrue();
    }

    /**
     * Проверяю, что все файлы из архива находятся
     */
    @Test
    void existsDeArchFilesTest() {
        for (String item:listFiles) {
            File deArchFile=new File(item);
            assertThat(deArchFile.exists()).isTrue();
        }
    }

    /**
     * Тест расширения у созданного архива
     */
    @Test
    void extensionArchTest() {
        assertThat(fileArch.toString().endsWith(EXTENSION)).isTrue();

    }

    /**
     * Тест установленного метода компрессии
     * @throws IOException
     */
    @Test
    void compressedMethodTest() throws IOException {
        if (fileArch.exists()) {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileArch));
            ZipEntry entry;
            entry=zis.getNextEntry();
            assertThat(entry.getMethod()).isEqualTo(ZipEntry.DEFLATED);

        }
    }

    @AfterEach
    void deleteTempFiles() {
        if (fileArch.exists()) {
            fileArch.delete();
        }
        for (String item: fileSource) {
            File file=new File(item);
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
