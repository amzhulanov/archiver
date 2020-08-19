package jam.example.archiver;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *Класс для ДеАрхивации файла
 *
 * @author JAM
 */

public class DeArchFiles {
    /**
     * Метод для деархивации файла
     * На вход deArch принимает имя архива
     * Определяю расположение каталога, откуда вызвана программа
     * Распаковваю архив в каталог из которого запущена программа
     *
     * @param fileArch - имя архива
     * @param outDir - путь для распаковки архива
     */
    public void deArch(String fileArch, String outDir) {
        File destDir = new File(outDir);
        byte[] buffer = new byte[1024];

        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(fileArch))) {

            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                if (!zipEntry.toString().endsWith("/")) {
                    try(FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }

                    }
                } else if (!newFile.exists()) {
                    if (!newFile.mkdir()){
                        throw new SecurityException();
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Метод для формирования файла с указанием месторасположения
     *
     * @param destinationDir - расположение файла
     * @param zipEntry - объект архива
     * @return
     * @throws IOException
     */
    public File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
