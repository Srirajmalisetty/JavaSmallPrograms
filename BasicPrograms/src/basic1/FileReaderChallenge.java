package basic1;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class AnalysisResult {
    int totalChars = 0;
    int totalWords = 0;
    int totalLines = 1; // Start at 1 (even empty file has 1 line)
    int vowels = 0;
    int consonants = 0;
    int digits = 0;
    int specialChars = 0;
    char mostFrequentChar = ' ';
    int maxFrequency = 0;
    String first100Chars = "";
}

public class FileReaderChallenge {
	
    public static AnalysisResult analyzeFile(String filename) throws IOException {
        AnalysisResult result = new AnalysisResult();
        int[] charFrequency = new int[256]; // For ASCII characters
        
        try (FileReader fr = new FileReader(filename)) {
            int ch;
            StringBuilder content = new StringBuilder();
            boolean inWord = false;
            
            while ((ch = fr.read()) != -1) {
                char c = (char) ch;
                content.append(c);
                result.totalChars++;
                
                // Track character frequency (ignore case for letters)
                if (Character.isLetter(c)) {
                    char lower = Character.toLowerCase(c);
                    charFrequency[lower]++;
                } else if (c != ' ' && c != '\n' && c != '\r' && c != '\t') {
                    if (c < 256) charFrequency[c]++;
                }
                
                // Count lines
                if (c == '\n') {
                    result.totalLines++;
                }
                
                // Count words
                if (Character.isWhitespace(c)) {
                    if (inWord) {
                        result.totalWords++;
                        inWord = false;
                    }
                } else {
                    inWord = true;
                }
                
                // Classify characters
                if (Character.isLetter(c)) {
                    char lower = Character.toLowerCase(c);
                    if (lower == 'a' || lower == 'e' || lower == 'i' || lower == 'o' || lower == 'u') {
                        result.vowels++;
                    } else {
                        result.consonants++;
                    }
                } else if (Character.isDigit(c)) {
                    result.digits++;
                } else if (!Character.isWhitespace(c)) {
                    result.specialChars++;
                }
            }
            
            // Handle last word if file doesn't end with whitespace
            if (inWord) {
                result.totalWords++;
            }
            
            // Handle empty file (still has 1 line)
            if (result.totalChars == 0) {
                result.totalLines = 0;
            }
            
            // Store content for first 100 chars
            result.first100Chars = content.length() > 100 ? 
                                   content.substring(0, 100) : content.toString();
            
            // Find most frequent character
            result.mostFrequentChar = ' ';
            result.maxFrequency = 0;
            for (int i = 0; i < charFrequency.length; i++) {
                if (charFrequency[i] > result.maxFrequency) {
                    result.maxFrequency = charFrequency[i];
                    result.mostFrequentChar = (char) i;
                }
            }
            
        } catch (IOException e) {
            throw e;
        }
        
        return result;
    }
    
    public static void generateReport(AnalysisResult result, String inputFile, String outputFile) 
            throws IOException {
        
        File file = new File(inputFile);
        long fileSize = file.length();
        
        try (FileWriter fw = new FileWriter(outputFile)) {
            // Top border
            fw.write("╔═══════════════════════════════════════════════════════════╗\n");
            fw.write("║                   TEXT FILE ANALYSIS REPORT               ║\n");
            fw.write("╠═══════════════════════════════════════════════════════════╣\n");
            
            // File info
            fw.write(String.format("║ File Name:     %-39s ║%n", inputFile));
            fw.write(String.format("║ File Size:     %-39s ║%n", formatFileSize(fileSize)));
            fw.write("╠═══════════════════════════════════════════════════════════╣\n");
            
            // Statistics
            fw.write("║ STATISTICS:                                               ║\n");
            fw.write(String.format("║   Total Characters:     %-30d ║%n", result.totalChars));
            fw.write(String.format("║   Total Words:          %-30d ║%n", result.totalWords));
            fw.write(String.format("║   Total Lines:          %-30d ║%n", result.totalLines));
            fw.write(String.format("║   Vowels:               %-30d ║%n", result.vowels));
            fw.write(String.format("║   Consonants:           %-30d ║%n", result.consonants));
            fw.write(String.format("║   Digits:               %-30d ║%n", result.digits));
            fw.write(String.format("║   Special Characters:   %-30d ║%n", result.specialChars));
            
            if (result.mostFrequentChar != ' ') {
                fw.write(String.format("║   Most Frequent Char:   '%c' (%d times) %-15s ║%n", 
                         result.mostFrequentChar, result.maxFrequency, ""));
            } else {
                fw.write("║   Most Frequent Char:   N/A                            ║\n");
            }
            
            fw.write("╠═══════════════════════════════════════════════════════════╣\n");
            
            // First 100 characters
            fw.write("║ FIRST 100 CHARACTERS:                                     ║\n");
            fw.write("║ " + wrapText(result.first100Chars, 47) + " ║\n");
            
            // Bottom border
            fw.write("╚═══════════════════════════════════════════════════════════╝\n");
        }
    }
    
    private static String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " bytes";
        if (bytes < 1024 * 1024) return String.format("%.2f KB", bytes / 1024.0);
        return String.format("%.2f MB", bytes / (1024.0 * 1024));
    }
    
    private static String wrapText(String text, int maxWidth) {
        if (text.length() <= maxWidth) {
            return String.format("%-" + maxWidth + "s", text);
        }
        return text.substring(0, maxWidth - 3) + "...";
    }
    
    private static void displayReport(String filename) throws IOException {
        try (FileReader fr = new FileReader(filename)) {
            int ch;
            while ((ch = fr.read()) != -1) {
                System.out.print((char) ch);
            }
        }
    }
    
    private static void createSampleFile(String filename) throws IOException {
        File file = new File(filename);
        if (file.exists()) {
            return; // File already exists
        }
        
        String sampleContent = "Hello World! This is a sample text file for testing.\n" +
                               "It contains numbers like 123 and special chars like @#$%.\n" +
                               "The quick brown fox jumps over the lazy dog.\n" +
                               "Java FileReader and FileWriter are useful classes.\n" +
                               "This line has UPPERCASE and lowercase letters.\n" +
                               "Email: test@example.com, Phone: 555-1234!\n" +
                               "The end. Goodbye!";
        
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(sampleContent);
        }
        
        System.out.println("✅ Sample file created: " + filename);
    }
    
    public static void main(String[] args) {
        String inputFile = "sample.txt";
        String outputFile = "analysis_report.txt";
        
        try {
            // First, create a sample file if it doesn't exist
            createSampleFile(inputFile);
            
            // Analyze the file
            AnalysisResult result = analyzeFile(inputFile);
            
            // Generate report
            generateReport(result, inputFile, outputFile);
            
            System.out.println("✅ Analysis complete! Report saved to: " + outputFile);
            
            // Display report content
            System.out.println("\n=== REPORT PREVIEW ===");
            displayReport(outputFile);
            
        } catch (IOException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    

}