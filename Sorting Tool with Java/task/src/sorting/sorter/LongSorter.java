package sorting.sorter;

import java.util.*;
import java.util.stream.Collectors;

public class LongSorter implements Sorter {
    ArrayList<Long> list;
    public LongSorter(String input) {
        String[] items = input.split("[\\s\\n]+");
        list = new ArrayList<>();
        for (String item : items) {
            try {
                list.add(Long.parseLong(item));
            }
            catch (NumberFormatException e) {
                System.out.printf("%s is not a long. It will be skipped.%n", item);
            }
        }
    }

    public String getMax() {
        int size = list.size();
        long max = list.stream()
                .max(Long::compareTo)
                .orElse(0L);
        long cntMax = list.stream()
                .filter(n -> n == max)
                .count();

        long percentageMax = 100 * cntMax / size;
        return String.format("Total numbers: %d.%nThe greatest number: %d (%d time(s), %d%%)%n.",
                size, max, cntMax, percentageMax);
    }

    public String getNatural() {
        StringBuilder sorted = new StringBuilder();
        int size = list.size();
        list.stream()
                .sorted()
                .forEach(word -> sorted.append(word).append(" "));
        return String.format("Total numbers: %d.%n" +
                "Sorted data:%s%n", size, sorted.toString());
    }

    public String getCount() {
        int size = list.size();
        Map<Long,Long> occurrences = list.stream()
                .collect(Collectors.groupingBy(number -> number,Collectors.counting()));
        List<Map.Entry<Long,Long>> sorted = new ArrayList<>(occurrences.entrySet());
        sorted.sort(Comparator
                .<Map.Entry<Long,Long>>comparingLong(Map.Entry::getValue)
                .thenComparing(Map.Entry::getKey));
        StringBuilder response = new StringBuilder();
        for (Map.Entry<Long,Long> entry : sorted) {
            response.append(String.format("%d: %d time(s), %d%%%n", entry.getKey(), entry.getValue(),
                    100*entry.getValue()/size));
        }
        return String.format("Total number: %d%n%s%n", size, response);
    }
}
