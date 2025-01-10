package algorithms.l06hash;

public class XHashMapApp {
  public static void main1(String[] args) {
    XHashMap map = new XHashMap();
    map.put(1, "Dima");
    map.put(2, "Lena");
    System.out.println(map.get(1));
    System.out.println(map.get(2));
    System.out.println(map.get(3));
  }

  public static void main(String[] args) {
    XHashMap map = new XHashMap(100);
    map.put(1, "Dima");
    map.put(17, "Lena");
    map.put(33, "Ira");
    System.out.println(map.get(1));
    System.out.println(map.get(17));
    System.out.println(map.get(33));
//        System.out.println(map.get(65)); // element with key 65 not found(tail)
//        System.out.println(map.get(66)); // element with key 66 not found (1st)
  }
}
