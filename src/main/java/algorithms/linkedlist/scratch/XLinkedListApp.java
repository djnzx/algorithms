package algorithms.linkedlist.scratch;

public class XLinkedListApp {
  public static void main(String[] args) {
    XLinkedList xl = new XLinkedList();
    xl.add(11);
    xl.add(33);
    boolean found11 = xl.contains(11);
    boolean found33 = xl.contains(33);
    boolean found44 = xl.contains(44);
    System.out.println(found11); // true
    System.out.println(found33); // true
    System.out.println(found44); // false
  }
}
