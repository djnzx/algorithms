package algorithms.linkedlist_dirty;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListListIteratorExample {
    public static void main(String[] args) {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);

        Iterator<Integer> it = ll.iterator();           // ->
        it.forEachRemaining(v -> {});                   // -> -> ->
        ll.listIterator();
        Iterator<Integer> di = ll.descendingIterator(); // <-

        LinkedList<Integer> origin = new LinkedList<>();
        origin.add(10);
        origin.add(20);
        origin.add(30);

        di.forEachRemaining(v -> origin.addFirst(v));  // <- <- <-
        System.out.println(origin);

    }
}
