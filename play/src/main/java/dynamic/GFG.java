package dynamic;

public class GFG {
  static int minCoins(int coins[], int m, int V) {
    // table[i] will be storing
    // the minimum number of coins
    // required for i value. So
    // table[V] will have result
    int[] table = new int[V + 1];

    table[0] = 0;

    for (int i = 1; i <= V; i++) {
      table[i] = Integer.MAX_VALUE;
    }

    for (int i = 1; i <= V; i++) {
      // Go through all coins smaller than i
      for (int j = 0; j < m; j++)
        if (coins[j] <= i) {
          int sub_res = table[i - coins[j]];
          if (sub_res != Integer.MAX_VALUE
              && sub_res + 1 < table[i]) {
            table[i] = sub_res + 1;
          }
        }
    }
    return table[V];
  }

  public static void main(String[] args) {
    int[] coins = {9, 6, 5, 1};
    int m = coins.length;
    int V = 11;
    System.out.printf("Minimum coins required is %d\n", minCoins(coins, m, V));
  }
}
