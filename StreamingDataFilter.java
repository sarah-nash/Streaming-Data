import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.util.Arrays.sort;

/**
 * Compilation: javac StreamingDataFilter.java
 * Execution: java StreamingDataFilter fileName windowWidth column
 *
 * fileName is a csv file containing the data we wish to sort.
 * windowWidth is the number of data points we want in the sliding window.
 * column in the column choice that we should use.
 *
 * This program filters a stream of data by calculating mean and median averages over a sliding window.
 * Averages write to the file "output.csv"
 *
 * Object Oriented Programming
 * Dr. Eaton
 * @author Sarah Nash
 */
public class StreamingDataFilter {
    /**
     * Calculates the mean value of the current sliding window.
     * @param dataPoints (double[]) the current values in the sliding window.
     * @return mean (double) returns the mean value of the sliding window.
     */
    public static double calculateMean(double[] dataPoints){
        int numPoints = dataPoints.length;
        double total=0;
        for (double point:dataPoints){
            total += point;
        }
        double mean = total/numPoints;
        return mean;
    }

    /**
     * Calculates the median value of the current sliding window.
     * @param dataPoints (double[]) the current values in the sliding window.
     * @return median (double) returns the median value of the sliding window.
     */
    public static double calculateMedian(double[] dataPoints){
        sort(dataPoints);
        int middle = dataPoints.length / 2;
        double median = (dataPoints)[middle];
        return median;
    }

    /**
     * Reads the desired file, puts values from the desired column into a buffer ring, and writes median and mean averages to a new file.
     * Currently, only writes to the file "output.csv"
     *
     * @param file (Path) the name of the .csv file
     * @param windowSize (int) the size of the sliding window
     * @param columnNumber (int) the column number selected (1,2,3....; not index of the column)
     */
    public static void filterFileColumn(Path file, int windowSize, int columnNumber){
        double[] bufferRing = new double[windowSize];
        int readCount = 0;
        Path newFile = Paths.get("output.csv");
        try(BufferedReader reader = Files.newBufferedReader(file)){
            String header = reader.readLine();
            String headerCol = header.split(",")[columnNumber-1];
            try(BufferedWriter writer = Files.newBufferedWriter(newFile)){
                writer.write(headerCol + " Raw, " + headerCol + " Mean, " + headerCol + " Median \n"); //prints header
                String line = reader.readLine();
                while (line != null) {
                    String[] lineSplit = line.split(","); //splits line into columns
                    try{
                        double value = Double.parseDouble(lineSplit[columnNumber - 1]);
                        int writeCount = readCount%windowSize;
                        bufferRing[writeCount] = value;
                        double mean = calculateMean(bufferRing);
                        double median = calculateMedian(bufferRing);
                        writer.write(value + "," + mean + "," + median + "\n");

                    }catch (NumberFormatException e){
                        System.out.println("Error: No value in this column! (" + e + ")");
                    }
                    readCount++;
                    line = reader.readLine();     // advance to the next line in the file
                }
            }catch (IOException e) {
                System.out.println("Error: Cannot find file to write to! (" + e + ")");
            }
        }catch (IOException e) {
            System.out.println("Error: Cannot find file to read! (" + e + ")");
        }
    }

    /**
     * Main parses the terminal arguments and passes them to the file filter.
     * @param args (String[]) args from the terminal: filepath, windowSize, and column
     */
    public static void main(String[] args) {
        if (args.length == 3){
            Path file = Paths.get(args[0]);
            int windowSize=-1;
            int column=-1;

            try{ //get the windowSize from args
                windowSize = Integer.parseInt(args[1]);
                try { //get column number from args
                    column = Integer.parseInt(args[2]);
                    filterFileColumn(file, windowSize, column);
                }catch(NumberFormatException e){
                    System.out.println("Non-integer column" + e);
                }
            }
            catch(NumberFormatException e) {
                System.out.println("Non-integer windowSize" + e);
            }
        }
        else{
            System.out.println("Argument missing!  Make sure to run the file as: ");
            System.out.println("java StreamingDataFilter filename windowWidth column");
        }
    }
}
