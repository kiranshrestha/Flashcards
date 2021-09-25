package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        final Scanner s = new Scanner(System.in);
        File export = null;
        CardHelper cardHelper = new CardHelper(s);
        switch (args.length) {
            case 2: {
                if (args[0].equals("-import")) {
                    cardHelper.importFile(new File(args[1]));
                }
                if (args[0].equals("-export")) {
                    export = new File(args[1]);
                }
                break;
            }
            case 4: {
                if (args[0].equals("-import")) {
                    cardHelper.importFile(new File(args[1]));
                }
                if (args[0].equals("-export")) {
                    export = new File(args[1]);
                }
                if (args[2].equals("-import")) {
                    cardHelper.importFile(new File(args[3]));
                }
                if (args[2].equals("-export")) {
                    export = new File(args[3]);
                }
            }
        }
        cardHelper.setExport(export);
        cardHelper.showMenu();

    }
}


