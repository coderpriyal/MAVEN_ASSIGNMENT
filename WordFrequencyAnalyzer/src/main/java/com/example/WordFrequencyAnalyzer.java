package com.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class WordFrequencyAnalyzer {

    public void analyze(String inputFile, String outputFile) {
        try {
            // Read input file
            String text = Files.readString(Paths.get(inputFile));

            // Normalize text: lowercase + remove punctuation
            text = text.toLowerCase().replaceAll("[^a-z0-9\\s]", " ");

            // Split into words
            String[] words = text.split("\\s+");

            // Count frequencies
            Map<String, Long> frequencyMap = Arrays.stream(words)
                    .filter(w -> !w.isBlank())
                    .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

            // Sort by frequency desc, then alphabetically
            List<Map.Entry<String, Long>> sortedList = frequencyMap.entrySet()
                    .stream()
                    .sorted((e1, e2) -> {
                        int freqCompare = Long.compare(e2.getValue(), e1.getValue());
                        return freqCompare != 0 ? freqCompare : e1.getKey().compareTo(e2.getKey());
                    })
                    .toList();

            // Ensure output directory exists
            Path outputPath = Paths.get(outputFile);
            Files.createDirectories(outputPath.getParent());

            // Write results
            try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
                for (Map.Entry<String, Long> entry : sortedList) {
                    writer.write(entry.getKey() + " : " + entry.getValue());
                    writer.newLine();
                }
            }

            System.out.println("✅ Word frequency analysis complete. Results at: " + outputFile);

        } catch (IOException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }
}
