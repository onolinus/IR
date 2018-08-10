package main;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

public class AssignmentMainTest {

    private static ArrayList<WordCollection> tokens;
    private static ArrayList<WordCollection> removedTokens;
    private static ArrayList<String> stepWords;
    private static ArrayList<String> filePath;
    //            public static final String delimeter = "\"[,\\s]+|\\[!\\s]+|\\[?\\s]+|\\[.\\s]+|\\[_\\s]+|\\[_\\s]+|\\['\\s]+|\\[@\\s]+|\\!|\\,|\\?|\\.|\\_|\\'|\\@\";";
    public static final String delimeter = "\\s+|,\\s*|\\.\\s*|-|\\@|\\'";
    public static final String spaceDelimeter = "\\s+";
    //    public static final String specialc = ",\\\\'\\\\#/;\\.[]{}()<>*+-=?^$\\|";
//    public static final String specialc = "[,\\'\\#/;\\.{}()<>*+-=?^$\\|]";
    //    public static final String;
    public static final String specialc = "[-{}()<>+*=?^$/',#;.]";
    public static final int COUNTER = 1;

    //    Initial
    public AssignmentMainTest() {
        tokens = new ArrayList<>();
        filePath = new ArrayList<>();
        removedTokens = new ArrayList<>();
        stepWords = new ArrayList<>();

    }

    public static void main(String[] args) {

        AssignmentMainTest assignmentMainTest = new AssignmentMainTest();
        assignmentMainTest.readFile();

    }

    public void readFile() {

        try {

            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("/Users/onolinus/Documents/Data/Study/UQ/S1/IR/Practical/assignment/1/notes/dataset/cranfieldDocs"));
            //DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("/Users/onolinus/Documents/Data/Study/UQ/S1/IR/Practical/assignment/1/notes/dataset/cranfieldDocs"));
            for (Path path : directoryStream) {
                filePath.add(path.toString());
            }
            System.out.println("Total of files : " + filePath.size());

            int breakcounter = 1;

            for (String file : filePath) {
//                System.out.println(file);

//                break;
                try (Scanner reader = new Scanner(new File(file))) {

                    while (reader.hasNext()) {
                        tokenizing(reader.next());
                    }

                }
//                breakcounter++;
//                if (breakcounter > 1) {
//                    break;
//                }

            }

////            //statistic
//            System.out.println("####################");
//            System.out.println("Before StepWords ");
//
//            for (int i = 0; i < tokens.size(); i++) {
//                System.out.println(tokens.get(i));
//            }

            readStepWords();

//            //statistic
            System.out.println("####################");
            System.out.println("After StepWords List");
            for (int i = 0; i < removedTokens.size(); i++) {
                System.out.println(removedTokens.get(i));
            }

            System.out.println("Collection Size " + tokens.size());
            System.out.println("Vocabulary Size " + removedTokens.size());

            Comparator comparator = Collections.reverseOrder(new SortbyWordCount());

            Collections.sort(removedTokens, comparator);

            System.out.println("Sorting by Total Counts");

            for (int i = 0; i < 49; i++) {
                System.out.println(removedTokens.get(i));
            }


        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

//        public boolean isStopWord (String _word){
//            boolean stopword;
//            try (BufferedReader reader = new BufferedReader(new FileReader())) {
//
//
//            }
//
//
//            retun stopword;
//        }


//        try (BufferedReader reader = new BufferedReader(new FileReader())) {
//
//        }
        System.out.println();
    }

    public void readStepWords() {

        try {

            String stepWordFile = "/Users/onolinus/Documents/Data/Study/UQ/S1/IR/Practical/assignment/1/notes/dataset/common_words.txt";


            try (Scanner reader = new Scanner(new File(stepWordFile))) {

                while (reader.hasNext()) {
                    stepWords.add(reader.next());
                }

            }

            int i = 0;
            while (i < tokens.size()) {
                boolean remove = false;
                for (int j = 0; j < stepWords.size(); j++) {
                    if (tokens.get(i).getWord().equalsIgnoreCase(stepWords.get(j))) {
                        remove = true;
                    }
                }

                if (remove == false) {
                    removedTokens.add(tokens.get(i));
                }
                i++;
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }


    public void tokenizing(String line) {
        //saveWord(line);
        //eliminating SGML
        if (!line.matches("<(\"[^\"]*\"|'[^']*'|[^'\">])*>")) {

            if (line.matches("^[a-zA-Z0-9]+$")) {
                if(line.matches("^\\s*$")) {
                    saveWord(line);
                }
            } else {
                line = line.replaceAll(specialc," ");
                String[] cleanSymbol = line.split(" ");
                for (int i = 0; i < cleanSymbol.length; i++) {
                    if(!cleanSymbol[i].matches("^\\s*$")) {
                        saveWord(cleanSymbol[i]);
                    }

                }
            }
        }
    }


    public void saveWord(String xword) {
        boolean firstTime = true;
        if (tokens.size() > 0) {
            int i = 0;
            while (i < tokens.size()) {
                if (tokens.get(i).getWord().equals(xword)) {
                    int tsize = tokens.get(i).getTotalWord();
                    tokens.get(i).setTotalWord(tsize + COUNTER);
                    firstTime = false;
                    break;
                }

                i++;
            }

            if (firstTime) {
                WordCollection word = new WordCollection();
                word.setTotalWord(COUNTER);
                word.setWord(xword);
                tokens.add(word);
            }

        } else {
            WordCollection word = new WordCollection();
            word.setTotalWord(COUNTER);
            word.setWord(xword);
            tokens.add(word);
        }

    }
}
