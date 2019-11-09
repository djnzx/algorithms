package warmup;

import java.util.HashMap;
import java.util.Map;

public class ShortestAndLongestString {
  public static void main(String[] args) {
    ShortestAndLongestString app = new ShortestAndLongestString();

    HashMap<Integer, String> map = new HashMap<>();
    int k=1;
    while (map.size() < 20) {
      map.put(k++, app.randomString(10,30));
    }
    int len_min = Integer.MAX_VALUE;
    int len_max = Integer.MIN_VALUE;
    String shortest = null;
    String longest = null;
    for (Map.Entry<Integer, String> item : map.entrySet()) {
//      System.out.printf("k:%d, v:%s\n", item.getKey(), item.getValue());
      // the longest
      if(item.getValue().length() > len_max) {
        len_max = item.getValue().length();
        longest = item.getValue();
      }
      // the shortest
      if(item.getValue().length() < len_min){
        len_min = item.getValue().length();
        shortest = item.getValue();
      }
    }
    System.out.printf("Shortest : length %d string %s\n",len_min,shortest);
    System.out.printf("Longest : length %d string %s\n",len_max,longest);
  }

  private String randomString(int min, int max) {
    int len = (int) (Math.random()*(max-min+1))+min;
    String outcome = "";
    while (outcome.length()<len)
      outcome = outcome + (char)(Math.random()*('z'-'a'+1)+'a');
    return outcome;
  }
}
