package jam.example.archiver;

import java.io.IOException;

public class AppArchiver {

    public static void main(String[] args) throws IOException {
        ManageArch manageArch=new ManageArch();
        manageArch.selectAction(args);
    }

}
