package basic1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LongestLineFinder {
    public static void main(String[] args) {
        String filename = "sample.txt";
        String longestLine = "";
        int lineNumber = 0;
        int longestLineNumber = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.length() > longestLine.length()) {
                    longestLine = line;
                    longestLineNumber = lineNumber;
                }
            }
            
            System.out.println("Longest line (line " + longestLineNumber + "):");
            System.out.println(longestLine);
            System.out.println("Length: " + longestLine.length() + " characters");
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}