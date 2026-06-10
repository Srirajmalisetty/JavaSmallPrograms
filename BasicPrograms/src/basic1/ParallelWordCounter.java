package basic1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelWordCounter {

    public static void main(String[] args) {
        String directoryPath = "D:\\Antigravity\\civic-sense-app\\client"; // change as needed
        File dir = new File(directoryPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println("No .txt files found in " + directoryPath);
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Integer>> futures = new ArrayList<>();

        long start = System.nanoTime();

        // Submit tasks
        for (File file : files) {
            futures.add(executor.submit(new WordCountTask(file)));
        }

        // Aggregate results
        int totalWords = 0;
        for (Future<Integer> future : futures) {
            try {
                totalWords += future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Task failed: " + e.getMessage());
            }
        }

        long end = System.nanoTime();
        double seconds = (end - start) / 1_000_000_000.0;

        executor.shutdown();

        System.out.println("Total words: " + totalWords);
        System.out.printf("Time taken: %.3f seconds%n", seconds);
    }

    static class WordCountTask implements Callable<Integer> {
        private final File file;

        WordCountTask(File file) {
            this.file = file;
        }

        @Override
        public Integer call() {
            int wordCount = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.trim().split("\\s+");
                    if (line.trim().isEmpty()) continue;
                    wordCount += words.length;
                }
            } catch (IOException e) {
                System.err.println("Skipping file " + file.getName() + ": " + e.getMessage());
                return 0;
            }
            System.out.println(Thread.currentThread().getName() + " processed " + file.getName() + " – " + wordCount + " words");
            return wordCount;
        }
    }
}