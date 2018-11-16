package marina;

import java.util.Scanner;

public class Shooting {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int[][] arr1 = new int[5][5];
    int x = 1;
    for (int i = 0; i < arr1.length; i++) {
      for (int j = 0; j < arr1[i].length; j++) {
        arr1[i][j] = x++;
      }
    }
    System.out.println("All set. Get ready to rumble!");
    int countTry = 0;
    int rand = (int)(Math.random()*26);
    System.out.println(rand);
    boolean isPlay = true;
    while (isPlay) {
      String s1 = in.nextLine();
      if(isNumber(s1)) {
        int s = Integer.parseInt(s1);
        int result = 0;
        for (int i = 0; i < arr1.length; i++) {
          for (int j = 0; j < arr1[i].length; j++) {
            if (s == rand && s == arr1[i][j]) {
              arr1[i][j] = result;
              System.out.printf("You won with %d hit!", countTry);
              System.out.println();
              isPlay = false;
            }
            if (s != rand && s == arr1[i][j]) {
              arr1[i][j] = 111;
            }
          }
        }
        printSquare(arr1);
        countTry++;
      }
    }
  }

  private static void printSquare(int[][] arr1) {
    for (int i = 0; i < arr1.length; i++) {
      for (int j = 0; j < arr1[i].length; j++) {
        System.out.print(arr1[i][j] + "  ");
      }
      System.out.println();
    }
  }

  private static boolean isNumber(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

}

