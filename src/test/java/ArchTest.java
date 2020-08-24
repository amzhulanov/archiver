import jam.example.archiver.ManageArch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Paths;

/**
 * Тест проверяет создание архивного файла
 */
class ArchTest {
    private final String PATH = "src/test/resources";
    private final String FILE_NAME = "archived";
    private final String EXTENSION = ".arch";
    private final ManageArch manageArch = new ManageArch();
    private File fileArch;

    @BeforeEach
    void generateArch() {
        fileArch = new File(FILE_NAME+EXTENSION);
        if (fileArch.exists()) {
            fileArch.delete();
        }
        String[] args = {Paths.get(PATH).resolve(Paths.get("test.file")).toString()
                , Paths.get(PATH).resolve(Paths.get("test2.file")).toString()
                , Paths.get(PATH).resolve(Paths.get("dir1")).toString()
                , Paths.get(PATH).resolve(Paths.get("dir2")).toString()
                ,":"
                , FILE_NAME};
        manageArch.selectAction(args);
    }

    @Test
    void existsFileTest() {
        assertThat(fileArch.exists()).isTrue();
    }

    @Test
    void extensionArchTest() {
        assertThat(fileArch.toString().endsWith(EXTENSION)).isTrue();

    }

    @AfterEach
    void deleteArch() {
        if (fileArch.exists()) {
            fileArch.delete();
        }
    }

}
