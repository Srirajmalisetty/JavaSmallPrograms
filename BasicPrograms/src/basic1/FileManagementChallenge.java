package basic1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class FileManagementChallenge {
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                 FILE MANAGEMENT SYSTEM                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        
        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    createFile();
                    break;
                case 2:
                    deleteFile();
                    break;
                case 3:
                    renameFile();
                    break;
                case 4:
                    copyFile();
                    break;
                case 5:
                    moveFile();
                    break;
                case 6:
                    getFileInfo();
                    break;
                case 7:
                    createDirectory();
                    break;
                case 8:
                    deleteDirectory();
                    break;
                case 9:
                    listDirectory();
                    break;
                case 10:
                    listFilesByExtension();
                    break;
                case 11:
                    searchFiles();
                    break;
                case 12:
                    calculateDirectorySize();
                    break;
                case 13:
                    displayDirectoryTree();
                    break;
                case 14:
                    findLargestFile();
                    break;
                case 15:
                    findMostRecentFile();
                    break;
                case 16:
                    System.out.println("Thank you for using File Management System!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("FILE MANAGEMENT SYSTEM - MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println(" 1. Create new file");
        System.out.println(" 2. Delete file");
        System.out.println(" 3. Rename file");
        System.out.println(" 4. Copy file");
        System.out.println(" 5. Move file");
        System.out.println(" 6. Get file information");
        System.out.println(" 7. Create directory");
        System.out.println(" 8. Delete directory (including contents)");
        System.out.println(" 9. List directory contents");
        System.out.println("10. List files by extension");
        System.out.println("11. Search files by name");
        System.out.println("12. Calculate directory size");
        System.out.println("13. Display directory tree");
        System.out.println("14. Find largest file");
        System.out.println("15. Find most recent file");
        System.out.println("16. Exit");
        System.out.println("=".repeat(50));
    }
    
    // ========== File Operations ==========
    
    private static void createFile() {
        System.out.print("Enter file path to create: ");
        String path = sc.nextLine();
        File file = new File(path);
        
        try {
            if (file.createNewFile()) {
                System.out.println("✅ File created successfully: " + file.getName());
                System.out.println("   Path: " + file.getAbsolutePath());
                System.out.println("   Size: " + formatFileSize(file.length()));
            } else {
                System.out.println("❌ File already exists: " + path);
            }
        } catch (IOException e) {
            System.out.println("❌ Error creating file: " + e.getMessage());
        }
    }
    
    private static void deleteFile() {
        System.out.print("Enter file path to delete: ");
        String path = sc.nextLine();
        File file = new File(path);
        
        if (!file.exists()) {
            System.out.println("❌ File does not exist: " + path);
            return;
        }
        
        System.out.print("Are you sure you want to delete '" + path + "'? (yes/no): ");
        String confirm = sc.nextLine();
        
        if (confirm.equalsIgnoreCase("yes")) {
            if (file.delete()) {
                System.out.println("✅ File deleted successfully: " + path);
            } else {
                System.out.println("❌ Failed to delete file: " + path);
            }
        } else {
            System.out.println("❌ Deletion cancelled.");
        }
    }
    
    private static void renameFile() {
        System.out.print("Enter current file path: ");
        String oldPath = sc.nextLine();
        System.out.print("Enter new file path/name: ");
        String newPath = sc.nextLine();
        
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        
        if (!oldFile.exists()) {
            System.out.println("❌ File does not exist: " + oldPath);
            return;
        }
        
        if (oldFile.renameTo(newFile)) {
            System.out.println("✅ File renamed successfully!");
            System.out.println("   Old: " + oldPath);
            System.out.println("   New: " + newPath);
        } else {
            System.out.println("❌ Failed to rename file. Check permissions or if destination exists.");
        }
    }
    
    private static void copyFile() {
        System.out.print("Enter source file path: ");
        String sourcePath = sc.nextLine();
        System.out.print("Enter destination file path: ");
        String destPath = sc.nextLine();
        
        File source = new File(sourcePath);
        File dest = new File(destPath);
        
        if (!source.exists()) {
            System.out.println("❌ Source file does not exist: " + sourcePath);
            return;
        }
        
        if (dest.exists()) {
            System.out.print("Destination file already exists. Overwrite? (yes/no): ");
            String confirm = sc.nextLine();
            if (!confirm.equalsIgnoreCase("yes")) {
                System.out.println("❌ Copy cancelled.");
                return;
            }
        }
        
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(dest)) {
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalBytes = 0;
            
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
            }
            
            System.out.println("✅ File copied successfully!");
            System.out.println("   From: " + sourcePath);
            System.out.println("   To: " + destPath);
            System.out.println("   Size: " + formatFileSize(totalBytes));
            
        } catch (IOException e) {
            System.out.println("❌ Error copying file: " + e.getMessage());
        }
    }
    
    private static void moveFile() {
        System.out.print("Enter source file path: ");
        String sourcePath = sc.nextLine();
        System.out.print("Enter destination file path: ");
        String destPath = sc.nextLine();
        
        File source = new File(sourcePath);
        File dest = new File(destPath);
        
        if (!source.exists()) {
            System.out.println("❌ Source file does not exist: " + sourcePath);
            return;
        }
        
        if (source.renameTo(dest)) {
            System.out.println("✅ File moved successfully!");
            System.out.println("   From: " + sourcePath);
            System.out.println("   To: " + destPath);
        } else {
            System.out.println("❌ Failed to move file. Trying copy + delete...");
            
            // Try copy + delete as fallback
            copyFile(); // This will ask for overwrite confirmation
            if (dest.exists()) {
                source.delete();
                System.out.println("✅ Original file deleted.");
            }
        }
    }
    
    private static void getFileInfo() {
        System.out.print("Enter file/directory path: ");
        String path = sc.nextLine();
        File file = new File(path);
        
        if (!file.exists()) {
            System.out.println("❌ File/Directory does not exist: " + path);
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                    FILE INFORMATION                    ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-15s: %-38s ║%n", "Name", file.getName());
        System.out.printf("║ %-15s: %-38s ║%n", "Path", file.getPath());
        System.out.printf("║ %-15s: %-38s ║%n", "Absolute Path", file.getAbsolutePath());
        System.out.printf("║ %-15s: %-38s ║%n", "Parent", file.getParent() == null ? "N/A" : file.getParent());
        System.out.printf("║ %-15s: %-38s ║%n", "Type", file.isDirectory() ? "Directory" : "File");
        
        if (!file.isDirectory()) {
            System.out.printf("║ %-15s: %-38s ║%n", "Size", formatFileSize(file.length()));
        }
        
        System.out.printf("║ %-15s: %-38s ║%n", "Last Modified", formatDate(file.lastModified()));
        System.out.printf("║ %-15s: r=%b, w=%b, x=%b %-24s ║%n", "Permissions", 
                         file.canRead(), file.canWrite(), file.canExecute(), "");
        System.out.printf("║ %-15s: %-38s ║%n", "Hidden", file.isHidden() ? "Yes" : "No");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
    
    // ========== Directory Operations ==========
    
    private static void createDirectory() {
        System.out.print("Enter directory path to create: ");
        String path = sc.nextLine();
        System.out.print("Create nested directories? (yes/no): ");
        String nested = sc.nextLine();
        
        File dir = new File(path);
        boolean success;
        
        if (nested.equalsIgnoreCase("yes")) {
            success = dir.mkdirs();
        } else {
            success = dir.mkdir();
        }
        
        if (success) {
            System.out.println("✅ Directory created successfully: " + path);
        } else {
            System.out.println("❌ Failed to create directory. Parent may not exist or invalid path.");
        }
    }
    
    private static void deleteDirectory() {
        System.out.print("Enter directory path to delete: ");
        String path = sc.nextLine();
        File dir = new File(path);
        
        if (!dir.exists()) {
            System.out.println("❌ Directory does not exist: " + path);
            return;
        }
        
        if (!dir.isDirectory()) {
            System.out.println("❌ Path is not a directory: " + path);
            return;
        }
        
        System.out.print("⚠️  WARNING: This will delete ALL contents! Continue? (yes/no): ");
        String confirm = sc.nextLine();
        
        if (confirm.equalsIgnoreCase("yes")) {
            deleteRecursively(dir);
            System.out.println("✅ Directory deleted successfully: " + path);
        } else {
            System.out.println("❌ Deletion cancelled.");
        }
    }
    
    private static void listDirectory() {
        System.out.print("Enter directory path: ");
        String path = sc.nextLine();
        File dir = new File(path);
        
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("❌ Invalid directory: " + path);
            return;
        }
        
        System.out.print("Show recursively? (yes/no): ");
        boolean recursive = sc.nextLine().equalsIgnoreCase("yes");
        
        if (recursive) {
            System.out.println("\n📁 Directory tree for: " + dir.getAbsolutePath());
            listTree(dir, "");
        } else {
            System.out.println("\n📁 Contents of: " + dir.getAbsolutePath());
            System.out.println("-".repeat(50));
            
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                System.out.println("   (empty directory)");
            } else {
                for (File f : files) {
                    String icon = f.isDirectory() ? "📁" : "📄";
                    String size = f.isDirectory() ? "" : " (" + formatFileSize(f.length()) + ")";
                    System.out.printf("   %s %s%s%n", icon, f.getName(), size);
                }
                System.out.println("-".repeat(50));
                System.out.println("Total items: " + files.length);
            }
        }
    }
    
    private static void listFilesByExtension() {
        System.out.print("Enter directory path: ");
        String path = sc.nextLine();
        System.out.print("Enter extension (e.g., .txt, .java): ");
        String ext = sc.nextLine();
        
        if (!ext.startsWith(".")) {
            ext = "." + ext;
        }
        
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("❌ Invalid directory: " + path);
            return;
        }
        
        System.out.println("\n🔍 Searching for files with extension: " + ext);
        searchByExtension(dir, ext, "");
    }
    
    private static void searchFiles() {
        System.out.print("Enter directory to search in: ");
        String path = sc.nextLine();
        System.out.print("Enter filename (or partial name): ");
        String searchTerm = sc.nextLine();
        
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("❌ Invalid directory: " + path);
            return;
        }
        
        System.out.println("\n🔍 Searching for: '" + searchTerm + "'");
        searchRecursively(dir, searchTerm.toLowerCase());
    }
    
    private static void calculateDirectorySize() {
        System.out.print("Enter directory path: ");
        String path = sc.nextLine();
        File dir = new File(path);
        
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("❌ Invalid directory: " + path);
            return;
        }
        
        long size = getDirectorySize(dir);
        System.out.println("📊 Directory size: " + formatFileSize(size));
        System.out.println("   Total files: " + getFileCount(dir));
    }
    
    private static void displayDirectoryTree() {
        System.out.print("Enter directory path: ");
        String path = sc.nextLine();
        File dir = new File(path);
        
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("❌ Invalid directory: " + path);
            return;
        }
        
        System.out.println("\n🌳 Directory tree for: " + dir.getAbsolutePath());
        displayTree(dir, "");
    }
    
    private static void findLargestFile() {
        System.out.print("Enter directory path: ");
        String path = sc.nextLine();
        File dir = new File(path);
        
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("❌ Invalid directory: " + path);
            return;
        }
        
        LargestFileResult result = findLargestFile(dir);
        if (result.file != null) {
            System.out.println("\n🏆 Largest file found:");
            System.out.println("   Name: " + result.file.getName());
            System.out.println("   Path: " + result.file.getAbsolutePath());
            System.out.println("   Size: " + formatFileSize(result.size));
        } else {
            System.out.println("No files found in directory.");
        }
    }
    
    private static void findMostRecentFile() {
        System.out.print("Enter directory path: ");
        String path = sc.nextLine();
        File dir = new File(path);
        
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("❌ Invalid directory: " + path);
            return;
        }
        
        MostRecentResult result = findMostRecentFile(dir);
        if (result.file != null) {
            System.out.println("\n🕐 Most recent file:");
            System.out.println("   Name: " + result.file.getName());
            System.out.println("   Path: " + result.file.getAbsolutePath());
            System.out.println("   Modified: " + formatDate(result.lastModified));
        } else {
            System.out.println("No files found in directory.");
        }
    }
    
    // ========== Helper Methods ==========
    
    private static String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.2f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.2f MB", bytes / (1024.0 * 1024));
        return String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024));
    }
    
    private static String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }
    
    private static void deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteRecursively(f);
                }
            }
        }
        file.delete();
    }
    
    private static long getDirectorySize(File dir) {
        long size = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    size += f.length();
                } else if (f.isDirectory()) {
                    size += getDirectorySize(f);
                }
            }
        }
        return size;
    }
    
    private static int getFileCount(File dir) {
        int count = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    count++;
                } else if (f.isDirectory()) {
                    count += getFileCount(f);
                }
            }
        }
        return count;
    }
    
    private static void listTree(File dir, String indent) {
        File[] files = dir.listFiles();
        if (files == null) return;
        
        for (File f : files) {
            if (f.isDirectory()) {
                System.out.println(indent + "📁 " + f.getName() + "/");
                listTree(f, indent + "  ");
            } else {
                System.out.println(indent + "📄 " + f.getName() + " (" + formatFileSize(f.length()) + ")");
            }
        }
    }
    
    private static void displayTree(File dir, String indent) {
        File[] files = dir.listFiles();
        if (files == null) return;
        
        for (File f : files) {
            if (f.isDirectory()) {
                System.out.println(indent + "📁 " + f.getName() + "/");
                displayTree(f, indent + "  ");
            } else {
                System.out.println(indent + "📄 " + f.getName() + " (" + formatFileSize(f.length()) + ")");
            }
        }
    }
    
    private static void searchByExtension(File dir, String extension, String indent) {
        File[] files = dir.listFiles();
        if (files == null) return;
        
        for (File f : files) {
            if (f.isDirectory()) {
                searchByExtension(f, extension, indent + "  ");
            } else if (f.getName().endsWith(extension)) {
                System.out.println(indent + "📄 " + f.getAbsolutePath() + " (" + formatFileSize(f.length()) + ")");
            }
        }
    }
    
    private static void searchRecursively(File dir, String searchTerm) {
        File[] files = dir.listFiles();
        if (files == null) return;
        
        for (File f : files) {
            if (f.isDirectory()) {
                searchRecursively(f, searchTerm);
            } else if (f.getName().toLowerCase().contains(searchTerm)) {
                System.out.println("📄 " + f.getAbsolutePath() + " (" + formatFileSize(f.length()) + ")");
            }
        }
    }
    
    private static class LargestFileResult {
        File file;
        long size;
        
        LargestFileResult(File file, long size) {
            this.file = file;
            this.size = size;
        }
    }
    
    private static LargestFileResult findLargestFile(File dir) {
        File largestFile = null;
        long largestSize = 0;
        
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile() && f.length() > largestSize) {
                    largestSize = f.length();
                    largestFile = f;
                } else if (f.isDirectory()) {
                    LargestFileResult result = findLargestFile(f);
                    if (result.size > largestSize) {
                        largestSize = result.size;
                        largestFile = result.file;
                    }
                }
            }
        }
        
        return new LargestFileResult(largestFile, largestSize);
    }
    
    private static class MostRecentResult {
        File file;
        long lastModified;
        
        MostRecentResult(File file, long lastModified) {
            this.file = file;
            this.lastModified = lastModified;
        }
    }
    
    private static MostRecentResult findMostRecentFile(File dir) {
        File mostRecent = null;
        long mostRecentTime = 0;
        
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile() && f.lastModified() > mostRecentTime) {
                    mostRecentTime = f.lastModified();
                    mostRecent = f;
                } else if (f.isDirectory()) {
                    MostRecentResult result = findMostRecentFile(f);
                    if (result.lastModified > mostRecentTime) {
                        mostRecentTime = result.lastModified;
                        mostRecent = result.file;
                    }
                }
            }
        }
        
        return new MostRecentResult(mostRecent, mostRecentTime);
    }
}