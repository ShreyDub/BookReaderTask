//Shreyansh Dubey & Rohan Patel
//12/4/25

//This is the new commit after Rohan forked it
import java.io.*;

public class BookReader{

  public static void main(String[] args) {

          // Input file paths
          String inputFile1 = "F.txt";
          String inputFile2 = "M.txt";

          // Output file paths
          String outputFile1 = "F_CAPS.txt";
          String outputFile2 = "M_CAPS.txt";

          long startTime = System.currentTimeMillis();  // start timer

          try {
              processFile(inputFile1, outputFile1);
              processFile(inputFile2, outputFile2);

          } catch (IOException e) {
              e.printStackTrace();
          }

          long endTime = System.currentTimeMillis();    // end timer
          long totalTime = endTime - startTime;

          System.out.println("Finished!");
          System.out.println("Output files:");
          System.out.println(" - " + outputFile1);
          System.out.println(" - " + outputFile2);
          System.out.println("Time taken: " + totalTime + " ms");
          System.out.println("Time taken: " + (totalTime / 1000.0) + " seconds");
      }

      private static void processFile(String inputPath, String outputPath) throws IOException {
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


	//This code takes two input text files, capitalizes every line, and saves the results into separate output files. It uses a single-threaded, memory-efficient approach that processes the files one line at a time. The program also records and prints how long the conversion took.
}
