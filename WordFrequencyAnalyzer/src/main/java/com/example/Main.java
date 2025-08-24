package com.example;

public class Main {
    public static void main(String[] args) {
        WordFrequencyAnalyzer analyzer = new WordFrequencyAnalyzer();
        analyzer.analyze("src/main/resources/input.txt", "target/output/output.txt");
    }
}
