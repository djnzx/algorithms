package algorithms.l04linkedlist;

public class XLinkedListApp {
  public static void main(String[] args) {
    XLinkedList xl = new XLinkedList();
    xl.add(11);
    xl.add(22);
    xl.add(33);
    xl.add(44);
    boolean found11 = xl.contains(11);
    boolean found33 = xl.contains(33);
    boolean found55 = xl.contains(55);
    System.out.println(found11); // true
    System.out.println(found33); // true
    System.out.println(found55); // false
    System.out.println(xl.toString()); // 11,22,33,44
    xl.remove(22);
    System.out.println(xl.toString()); // 11,33,44
    xl.remove(33);
    System.out.println(xl.toString()); // 11,44
    xl.remove(44);
    System.out.println(xl.toString()); // 11
    xl.remove(11);
    System.out.println(xl.toString()); // []
  }
}
