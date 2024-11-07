package sorting.sorter;

import java.util.*;
import java.util.stream.Collectors;

public class LineSorter implements Sorter {
    ArrayList<String> list;

    public LineSorter(String input) {
        String[] items = input.split("\\n");
        list = new ArrayList<>();
        list.addAll(Arrays.asList(items));
    }

    public String getMax() {
        int size = list.size();
        String max = list.stream()
                .max(Comparator.comparingInt(String::length).thenComparing(String::compareTo))
                .orElse("");
        long cntMax = list.stream()
                .filter(max::equals)
                .count();

        long percentageMax = 100 * cntMax / size;
        return String.format("Total words: %d.%nThe longest word: %s (%d time(s), %d%%)%n.",
                size, max, cntMax, percentageMax);
    }

    public String getNatural() {
        StringBuilder sorted = new StringBuilder();
        int size = list.size();
        list.stream()
                .sorted(String::compareTo)
                .forEach(word -> sorted.append(word).append("\n"));
        return String.format("Total lines: %d.%n" +
                "Sorted data:%n%s%n", size, sorted.toString());
    }

    public String getCount() {
        int size = list.size();
        Map<String,Long> occurrences = list.stream()
                .collect(Collectors.groupingBy(word -> word,Collectors.counting()));
        List<Map.Entry<String,Long>> sorted = new ArrayList<>(occurrences.entrySet());
        sorted.sort(Comparator
                .<Map.Entry<String,Long>>comparingLong(Map.Entry::getValue)
                .thenComparing(Map.Entry::getKey));
        StringBuilder response = new StringBuilder();
        for (Map.Entry<String,Long> entry : sorted) {
            response.append(String.format("%s: %d time(s), %d%%%n", entry.getKey(), entry.getValue(),
                    100*entry.getValue()/size));
        }
        return String.format("Total lines: %d%n%s%n", size, response);
    }

}
