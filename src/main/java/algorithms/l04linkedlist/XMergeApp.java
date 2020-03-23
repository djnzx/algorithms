package algorithms.l04linkedlist;

public class XMergeApp {
  public static void main(String[] args) {
    XMerge app = new XMerge();
    XLinkedList xl1 = new XLinkedList();
    XLinkedList xl2 = new XLinkedList();
    xl1.add(1);
    xl1.add(4);
    xl1.add(5);
    xl1.add(7);
    xl1.add(9);
    xl1.add(100);
    xl1.add(200);
    xl2.add(2);
    xl2.add(3);
    xl2.add(6);
    xl2.add(8);
    System.out.println(xl1.toString());
    System.out.println(xl2.toString());
    XLinkedList.XItem merged = app.merge(xl1, xl2);
    System.out.println(xl1.toStringFrom(merged));
    System.out.println(xl1.toString());
    System.out.println(xl2.toString());
    xl1.remove(5);
    System.out.println(xl1.toString());
    System.out.println(xl2.toString());
    xl1.remove(1);
    System.out.println(xl1.toString());
    System.out.println(xl2.toString());
    System.out.println(xl1.toStringFrom(merged));
  }
}
