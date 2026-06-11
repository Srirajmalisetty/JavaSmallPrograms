package basic1;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutureCallableChallenge {

    public static void main(String[] args) throws Exception {
        List<Integer> numbers = IntStream.rangeClosed(1, 1000)
                                         .boxed()
                                         .collect(Collectors.toList());

        List<List<Integer>> chunks = chunkList(numbers, 4);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<Integer>> futures = new ArrayList<>();

        long start = System.nanoTime();

        for (int i = 0; i < chunks.size(); i++) {
            final int chunkIndex = i;
            final List<Integer> chunk = chunks.get(i);
            Callable<Integer> task = () -> {
                System.out.println(Thread.currentThread().getName() + 
                        " processing chunk " + (chunkIndex + 1) + " (size " + chunk.size() + ")");
                return chunk.stream().mapToInt(n -> n * n).sum();
            };
            futures.add(executor.submit(task));
        }

        int total = 0;
        for (Future<Integer> future : futures) {
            total += future.get(); // blocks until result ready
        }

        long end = System.nanoTime();
        double seconds = (end - start) / 1_000_000_000.0;

        executor.shutdown();

        System.out.println("Total sum of squares: " + total);
        System.out.printf("Time taken: %.3f seconds%n", seconds);
    }

    private static List<List<Integer>> chunkList(List<Integer> list, int numChunks) {
        List<List<Integer>> chunks = new ArrayList<>();
        int size = list.size();
        int chunkSize = (size + numChunks - 1) / numChunks; // ceiling division
        for (int i = 0; i < size; i += chunkSize) {
            chunks.add(list.subList(i, Math.min(i + chunkSize, size)));
        }
        return chunks;
    }
}