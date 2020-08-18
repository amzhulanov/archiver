package jam.example.archiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageArch {
    private static String OUTPUT;

    public void selectAction(String[] args) throws IOException {
        final List<String> srcFiles = new ArrayList<String>();
        if (args[args.length - 2].equals(":") && (!args[0].equals("-x"))) {

            getListParams(args, srcFiles);
            ArchFiles archFiles = new ArchFiles();
            OUTPUT = System.getProperty("user.dir").concat("\\").concat(args[args.length - 1]).concat(".arch");
            System.out.println(OUTPUT);
            archFiles.arch(srcFiles, OUTPUT);
        } else if (args[0].equals("-x") && !args[1].isEmpty()) {
            DeArchFiles deArchFiles = new DeArchFiles();
            deArchFiles.deArch(args[1].toString());
        }
    }

    private static void getListParams(String[] args, List<String> srcFiles) {
        //todo Добавить валидность данных, начинаться должны с ./
        for (int n = 0; n < args.length - 2; n++) {
            srcFiles.add(args[n]);
        }
    }
}
