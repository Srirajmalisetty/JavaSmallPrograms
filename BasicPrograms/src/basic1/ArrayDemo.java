package basic1;
import java.util.*;
public class ArrayDemo {
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.println("Enter the row size");
    int row = sc.nextInt();

    System.out.println("Enter the column size"); // fixed
    int col = sc.nextInt();

    int[][] arr = new int[row][col];

    System.out.println("Enter elements:");
    for(int i = 0; i < row; i++) {
        for(int j = 0; j < col; j++) {
            arr[i][j] = sc.nextInt();
        }
    }

    System.out.println("Matrix:");
    for(int i = 0; i < row; i++) {
        for(int j = 0; j < col; j++) {
            System.out.print(arr[i][j] + " ");
        }
        System.out.println(); // new line
    }
int sum=0;
for(int i=0;i<arr.length;i++) {
	sum=sum+arr[i][i];
}
System.out.println("Matrix Diagonal Sum "+sum);
for(int i = 0; i < row; i++) {
    for(int j = 0; j < col; j++) {
        System.out.print(arr[i][j] +arr[i][j]+ " ");
    }
    System.out.println(); 
}

    sc.close();

}
}
