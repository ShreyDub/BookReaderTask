//Shreyansh Dubey & Rohan Patel
//12/4/25

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.*;

public class BookReader {

    // Task that reads a file, capitalizes text, and writes it to an output file
    static class CapitalizeTask implements Callable<Void> {
        private final Path inputFile;
        private final Path outputFile;

        public CapitalizeTask(Path inputFile, Path outputFile) {
            this.inputFile = inputFile;
            this.outputFile = outputFile;
        }

        @Override
        public Void call() throws Exception {
            System.out.println("Processing file: " + inputFile);

            // Read file using ISO-8859-1 to avoid MalformedInputException
            String content = Files.readString(inputFile, java.nio.charset.StandardCharsets.ISO_8859_1);

            String capitalized = content.toUpperCase();

            // Overwrite output file
            Files.writeString(outputFile, capitalized);

            System.out.println("Output written to: " + outputFile);

            return null;
        }
    }

    public static void main(String[] args) {

        // Input files
        Path file1 = Path.of("MobyDick.txt");
        Path file2 = Path.of("Frankenstien.txt");

        // Output files (these get overwritten each time)
        Path output1 = Path.of("book1_capitalized.txt");
        Path output2 = Path.of("book2_capitalized.txt");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Start timer
        long startTime = System.nanoTime();

        // Submit tasks
        Future<Void> result1 = executor.submit(new CapitalizeTask(file1, output1));
        Future<Void> result2 = executor.submit(new CapitalizeTask(file2, output2));

        try {
            // Wait for both tasks to finish
            result1.get();
            result2.get();

            System.out.println("Both files processed successfully!");

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Stop timer
        long endTime = System.nanoTime();

        // Calculate duration
        long durationNs = endTime - startTime;
        double durationMs = durationNs / 1_000_000.0;
        double durationSeconds = durationNs / 1_000_000_000.0;

        System.out.printf("Total time: %.2f ms (%.3f seconds)%n", durationMs, durationSeconds);

        executor.shutdown();
    }
}
