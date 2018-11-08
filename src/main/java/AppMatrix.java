import java.util.Scanner;

public class AppMatrix {
  public static void main(String[] args) {
    int ch,n,i,j,max=0,j_max,i_max;
    System.out.print("Please, enter a size of matrix\n");
    Scanner in = new Scanner(System.in);
    n = in.nextInt();
    int[][] A = new int[n][n];
    System.out.print("rand or not(1,0) \n");

    for (i = 0; i < n; i++) {
      for (j = 0; j < n; j++) {
        A[i][j] = (int)(Math.random()*100);
      }
    }

    System.out.print("Matrix before: \n");
    for (i = 0; i < n; i++) {
      for (j = 0; j < n; j++) {
        System.out.printf("%d ", A[i][j]);
      }
      System.out.println();
    }
  }
}
