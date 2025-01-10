package algorithms.l04linkedlist;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListListIterators {
  public static void main(String[] args) {
    LinkedList<Integer> list = new LinkedList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);

    // A. conventional Iterator
    Iterator<Integer> it1 = list.iterator();
    // 1. ONE step
    boolean b = it1.hasNext();
    Integer next = it1.next();                         // 1
    // 2. FOR EACH remaining
    it1.forEachRemaining(v -> System.out.println(v));  // 2, 3, 4, 5

    // B. list Iterator + backward iteration
    ListIterator<Integer> it2 = list.listIterator();
    // 1.
    boolean b1 = it2.hasNext();
    boolean b2 = it2.hasPrevious();
    // 2.
    int next1 = it2.next();
    int prev1 = it2.previous();
    // 3.
    int ind1 = it2.nextIndex();
    int ind2 = it2.previousIndex();
    // 4.
    it2.forEachRemaining(System.out::println);

    // C. descending iterator (linked list only)
    Iterator<Integer> li3 = list.descendingIterator(); // <-

    // interesting example how ro prepend the onne list to another, but in front of
    LinkedList<Integer> origin = new LinkedList<>();
    origin.add(10);
    origin.add(20);
    origin.add(30);

    li3.forEachRemaining(v -> origin.addFirst(v));  // add to start!
    System.out.println(origin);
  }
}
