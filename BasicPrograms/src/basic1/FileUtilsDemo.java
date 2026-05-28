package basic1;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

class FileUtils {
    
    // Get human-readable file size
    public static String getFileSize(File file) {
        long bytes = file.length();
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.2f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.2f MB", bytes / (1024.0 * 1024));
        return String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024));
    }
    
    // Get formatted last modified date
    public static String getLastModified(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(file.lastModified()));
    }
    
    // Get file extension
    public static String getExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return (lastDot == -1) ? "" : name.substring(lastDot + 1);
    }
    
    // Display detailed file info
    public static void displayDetailedInfo(File file) {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.printf("║ %-49s ║%n", file.getName());
        System.out.println("╠═══════════════════════════════════════════════════╣");
        System.out.printf("║ Path:        %-34s ║%n", file.getPath());
        System.out.printf("║ Absolute:    %-34s ║%n", file.getAbsolutePath());
        System.out.printf("║ Type:        %-34s ║%n", file.isDirectory() ? "Directory" : "File");
        System.out.printf("║ Size:        %-34s ║%n", getFileSize(file));
        System.out.printf("║ Modified:    %-34s ║%n", getLastModified(file));
        System.out.printf("║ Permissions: r=%b, w=%b, x=%b %-19s ║%n", 
                         file.canRead(), file.canWrite(), file.canExecute(), "");
        System.out.println("╚═══════════════════════════════════════════════════╝");
    }
}

public class FileUtilsDemo {
    public static void main(String[] args) {
        File file = new File("test.txt");
        
        if (file.exists()) {
            FileUtils.displayDetailedInfo(file);
        } else {
            System.out.println("File does not exist. Creating one...");
            try {
                if (file.createNewFile()) {
                    FileUtils.displayDetailedInfo(file);
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
    }
}