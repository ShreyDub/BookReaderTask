// Shreyansh Dubey & Rohan Patel
// 12/4/25

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;

public class BookReader {

    public static void main(String[] args) {

        System.out.println("\n===== RUNNING SINGLE THREADED VERSION =====");
        runSingleThreaded();

        System.out.println("\n===== RUNNING MULTI THREADED VERSION =====");
        runMultiThreaded();

        System.out.println("\nAll processing complete!");
    }


    // ---------------------------------------------------------
    //  SINGLE-THREADED VERSION
    // ---------------------------------------------------------
    public static void runSingleThreaded() {

        String inputFile1 = "F.txt";   // Frankenstein
        String inputFile2 = "M.txt";   // Moby Dick

        String outputFile1 = "F_CAPS.txt";
        String outputFile2 = "M_CAPS.txt";

        long startTime = System.currentTimeMillis();

        try {
            processFileSingle(inputFile1, outputFile1);
            processFileSingle(inputFile2, outputFile2);

            System.out.println("Single-threaded: both files processed.");

        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long total = endTime - startTime;

        System.out.printf("Single-threaded time: %d ms (%.3f seconds)%n",
                total, total / 1000.0);
    }


    private static void processFileSingle(String inputPath, String outputPath) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputPath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line.toUpperCase());
                writer.newLine();
            }
        }
    }



    // ---------------------------------------------------------
    //  MULTI-THREADED VERSION
    // ---------------------------------------------------------
    public static void runMultiThreaded() {

        Path file1 = Path.of("MobyDick.txt");
        Path file2 = Path.of("Frankenstien.txt");

        Path output1 = Path.of("book1_capitalized.txt");
        Path output2 = Path.of("book2_capitalized.txt");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        long startTime = System.nanoTime();

        Future<Void> result1 = executor.submit(new CapitalizeTask(file1, output1));
        Future<Void> result2 = executor.submit(new CapitalizeTask(file2, output2));

        try {
            result1.get();
            result2.get();
            System.out.println("Multi-threaded: both files processed.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        long durationNs = endTime - startTime;

        System.out.printf("Multi-threaded time: %.2f ms (%.3f seconds)%n",
                durationNs / 1_000_000.0,
                durationNs / 1_000_000_000.0);

        executor.shutdown();
    }


    // Task used by multithreaded version
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

            String content = Files.readString(inputFile,
                    java.nio.charset.StandardCharsets.ISO_8859_1);

            Files.writeString(outputFile, content.toUpperCase());

            System.out.println("Output written to: " + outputFile);

            return null;
        }
    }
}
