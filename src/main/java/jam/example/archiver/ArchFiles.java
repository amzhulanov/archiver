package jam.example.archiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Класс для Архивации файла
 *
 * @author JAM
 */

public class ArchFiles {

    /**
     * Метод для архивации файла
     *
     * @param srcFiles - Лист файлов и директорий для архивации
     * @param outFile - имя архива, указанного при запуске программы
     * @param outDir   - каталог из которого была запущена программа
     */
    public void arch(List<String> srcFiles, String outFile, String outDir) {

        try (final FileOutputStream fos = new FileOutputStream(outDir.concat("//").concat(outFile).concat(".arch"));
             final ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            for (final String srcFile : srcFiles) {
                final File fileToZip = new File(srcFile);
                if (fileToZip.isFile() && fileToZip.exists()) {
                    try(final FileInputStream fis = new FileInputStream(fileToZip)){
                    final ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);

                    final byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }}
                } else if (fileToZip.isDirectory()) {
                    zipCatalog(fileToZip, fileToZip.getName(), zipOut);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private static void zipCatalog(final File fileToZip, final String fileName, final ZipOutputStream zipOut) throws
            IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            final File[] children = fileToZip.listFiles();
            for (final File childFile : children) {
                zipCatalog(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        final FileInputStream fis = new FileInputStream(fileToZip);
        final ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        final byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
