package jam.example.archiver;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ArchFiles {
    public void arch(List<String> srcFiles, String output) throws IOException {


        final FileOutputStream fos = new FileOutputStream(output);
        final ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (final String srcFile : srcFiles) {
            final File fileToZip = new File(srcFile);
            if (fileToZip.isFile() && fileToZip.exists()) {
                final FileInputStream fis = new FileInputStream(fileToZip);
                final ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);

                final byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
            else if (fileToZip.isDirectory()){
                System.out.println("find catalog. start archived catalog");
                zipCatalog(fileToZip,fileToZip.getName(),zipOut);
            }
            else{
                System.out.println("the specified file is not found");
            }
        }
        zipOut.close();
        fos.close();
    }

    private static void zipCatalog(final File fileToZip, final String fileName, final ZipOutputStream zipOut) throws IOException {
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
