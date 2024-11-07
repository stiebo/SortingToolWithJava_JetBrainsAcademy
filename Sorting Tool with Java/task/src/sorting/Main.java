package sorting;

import sorting.sorter.*;

import java.io.*;
import java.util.*;

public class Main {


    public static void main(final String[] args) {

        String type = "long";
        String sortingType = "natural";
        String inputFile = null;
        String outputFile = null;

        for (int i=0; i<args.length; i++) {
            switch (args[i]) {
                case "-sortingType":
                    if ((i+1 < args.length) && List.of("natural", "byCount", "maxInfo").contains(args[i+1])) {
                        sortingType = args[i+1];
                        i++;
                    }

                    else {
                        System.out.println("No sorting type defined!");
                        return;
                    }
                    break;
                case "-dataType":
                    if ((i+1 < args.length) && List.of("long", "word", "line").contains(args[i+1])) {
                        type = args[i+1];
                        i++;
                    }
                    else {
                        System.out.println("No data type defined!");
                        return;
                    }
                    break;
                case "-inputFile":
                    if (i+1 < args.length) {
                        inputFile = args[i+1];
                        i++;
                    }
                    else {
                        System.out.println("No inputFile defined!");
                        return;
                    }
                    break;
                case "-outputFile":
                    if (i+1 < args.length) {
                        outputFile = args[i+1];
                        i++;
                    }
                    else {
                        System.out.println("No outputFile defined!");
                        return;
                    }
                    break;
                default:
                    System.out.println("\"-arg\" is not a valid parameter. It will be skipped.");
                    break;
            }
        }

        Scanner scanner;
        if (inputFile!=null) {
            try {
                scanner = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            scanner = new Scanner(System.in);
        }

        StringBuilder input = new StringBuilder();
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("exit")) break;
                else input.append(line);
                input.append("\n");
            }
        }
        catch (Exception e) {

        }
        scanner.close();

        Sorter sorter;
        switch (type) {
            case "long" -> sorter = new LongSorter(input.toString());
            case "word" -> sorter = new WordSorter(input.toString());
            case "line" -> sorter = new LineSorter(input.toString());
            default -> { return; }
        }

        String result = "no results yet";
        switch (sortingType) {
            case "maxInfo" -> result = sorter.getMax();
            case "natural" -> result = sorter.getNatural();
            case "byCount" -> result = sorter.getCount();
            default -> { return; }
        }

        if (outputFile!=null) {
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.append(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else System.out.println(result);
    }
}
