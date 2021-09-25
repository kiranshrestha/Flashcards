package flashcards;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class CardHelper {

    final LinkedHashMap<String, String > storage = new LinkedHashMap<>();
    final HashMap<String,Integer>termErrorCount = new HashMap<>();
    final String PATH = "D:\\JetBrain Acadmy\\Flashcards\\FlashCardFiles\\";
    private static final StringJoiner logs = new StringJoiner("\n");

    Scanner s;
    File export;

    public void setExport(File export) {
        System.out.println("export = " + export);
        this.export = export;
    }

    public CardHelper(Scanner s) {
        this.s = s;
    }

    void showMenu() {
        logOutputTracker("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
        var selection = logInputTracker(s.nextLine());
        switch (selection) {
            case "add" : {
                logOutputTracker("The card:");
                verifyInputAndAddCard();
                showMenu();
                break;
            }
            case "remove" : {
                logOutputTracker("Which card?");
                var removeCard = logInputTracker(s.nextLine());
                if (storage.containsKey(removeCard)) {
                   storage.remove(removeCard);
                    logOutputTracker("The card has been removed.");
                } else {
                    logOutputTracker(String.format("Can't remove \"%s\": there is no such card.\n", removeCard));
                }
                showMenu();
                break;
            }

            case "import" : {
                File file1 = getFile();
                importFile(file1);
                showMenu();

                break;
            }

            case "export" : {
                File file1 = getFile();
                exportFile(file1);
                showMenu();
                break;
            }

            case "ask" : {
                logOutputTracker("How many times to ask?");
                var times = Integer.parseInt(logInputTracker(s.nextLine()));
                Random generator = new Random();
                var entries =  storage.entrySet().toArray();

                for (int i = 0; i < times; i++) {
                    var randomValue = entries[generator.nextInt(entries.length)];
                    final Map.Entry entry = (Map.Entry) randomValue;
                    askForDefinitionFor(entry);
                }
                showMenu();
                break;
            }

            case "exit" : {
                logOutputTracker("bye bye!");
                if(export!=null)
                    exportFile(export);
                break;
            }

            case "log" : {
                logOutputTracker("File name:\n");
                String filename = logInputTracker(s.nextLine());
                logOutputTracker("The log has been saved.\n");
                File file = new File(filename);
                try {
                    Files.write(file.toPath(), List.of(logs.toString().split("\n")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showMenu();
                break;
            }

            case "hardest card" : {
                if(termErrorCount.isEmpty()) {
                    logOutputTracker("There are no cards with errors.\n");
                } else {
                    var keySet = termErrorCount.entrySet();
                    var valSet = termErrorCount.values();
                    int max = valSet.stream().collect(Collectors.summarizingInt(Integer::intValue)).getMax();

                    ArrayList<Map.Entry<String,Integer>> errorTerm = new ArrayList<>();
                    for (var entry :
                            keySet) {

                        if(entry.getValue() == max) {
                            errorTerm.add(entry);
                        }
                    }
                    StringBuilder sb = new StringBuilder();
                    if(errorTerm.size() == 1) {
                        sb.append("card is ").append(String.format("\"%s\"",errorTerm.get(0).getKey()));
                    } else {
                        sb.append("cards are ");
                        for (Map.Entry entry :
                                errorTerm) {
                            sb.append(String.format("\"%s\", ",entry.getKey()));
                        }
                        sb.deleteCharAt(sb.length()-2);
                    }

                    logOutputTracker(String.format("The hardest %s. You have %d errors answering %s.\n",
                            sb,
                            errorTerm.get(0).getValue(),
                            errorTerm.size() == 1 ? "it": "Them"));

                }
                showMenu();
                break;
            }

            case "reset stats" : {
                termErrorCount.clear();
                logOutputTracker("Card statistics have been reset.");
                showMenu();
                break;
            }

        }
    }

    void exportFile(File file1) {
        try(PrintWriter fileWriter = new PrintWriter(file1)) {
            //Empty file writer before use.
            fileWriter.write("");
            Set<String> keySets = storage.keySet();
            for (String key :
                    keySets) {
                fileWriter.append(String.format("%s:%s:%d\n", key, storage.get(key), termErrorCount.getOrDefault(key,0)));
            }
            logOutputTracker(String.format("%d cards have been saved.\n\n", keySets.size()));
        } catch (IOException e) {
            e.printStackTrace();
            logOutputTracker("File not found.");
        }
    }

    void importFile(File file1) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file1))){
            var line = "";
            var count = 0;
            do {
                line = bufferedReader.readLine();
                if(line== null) {
                    break;
                }
                count++;
                var keyVal = line.split(":");
                //System.out.println("keyVal = " + Arrays.toString(keyVal));
                storage.put(keyVal[0], keyVal[1]);
                final int value = Integer.parseInt(keyVal[2]);
                if(value != 0)
                    termErrorCount.put(keyVal[0], value);
            } while (true);
            logOutputTracker(String.format("%d cards have been loaded.\n\n", count));
        } catch (IOException e) {
            logOutputTracker("File not found.");
        }
    }

    private File getFile() {
        logOutputTracker("File name:");
        var file = logInputTracker(s.nextLine());
        return new File(/*PATH +*/ file);
    }

    private void askForDefinitionFor(Map.Entry<String, String> entry) {
        logOutputTracker(String.format("Print the definition of \"%s\":\n", entry.getKey()));
        var definition = logInputTracker(s.nextLine());
        /*System.out.println("definition = " + definition);
        System.out.println("entry = " + entry.getValue());*/
        if (definition.equals(entry.getValue())) {
            logOutputTracker("Correct!");
        } else {
            boolean valueExists = false;
            // iterate each entry of hashmap
            for (Map.Entry<String, String> valEntry: storage.entrySet()) {
                if(Objects.equals(valEntry.getValue(), definition)) {
                    logOutputTracker(String.format("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\" .\n\n", entry.getValue(), valEntry.getKey()));
                    updateWrongAnswerTerm(valEntry.getKey());
                    valueExists = true;
                    break;
                }
            }
            if (!valueExists) {
                {
                    updateWrongAnswerTerm(entry.getKey());
                    logOutputTracker(String.format("Wrong. The right answer is \"%s\".\n", entry.getValue()));
                }
            }
        }
    }

    private void updateWrongAnswerTerm(String key) {
        termErrorCount.put(key,termErrorCount.getOrDefault(key,0) + 1);
    }

    public void verifyInputAndAddCard() {
        String term;
        String definition;
        if(storage.isEmpty()) {
            term = logInputTracker(s.nextLine());
            logOutputTracker("The definition of the card:");
            definition = logInputTracker(s.nextLine());
            storage.put(term, definition);
            logOutputTracker(String.format("The pair (\"%s\":\"%s\") has been added.\n\n", term, definition));

        }else {
            term = s.nextLine();
            if(storage.containsKey(term)) {
                logOutputTracker(String.format("The card \"%s\" already exists.\n",term));
            }else {
                logOutputTracker("The definition of the card:");
                verifyDefinitionInput(s, term);
            }
        }

    }

    private void verifyDefinitionInput(Scanner s, String term) {
        String definition;
        definition = logInputTracker(s.nextLine());
        if(storage.containsValue(definition)) {
            logOutputTracker(String.format("The definition \"%s\" already exists.\n", definition));
            return;
        }
        storage.put(term, definition);
        logOutputTracker(String.format("The pair (\"%s\":\"%s\") has been added.\n\n", term, definition));
    }

    String logInputTracker(String log) {
        logs.add(log);
        return log;
    }

    static void logOutputTracker(String log) {
        logs.add(log);
        System.out.println(log);
    }

}
