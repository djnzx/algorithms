package algorithms.l04linkedlist;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class LinkedListJDKAPI {
    public static void main(String[] args) {
        Deque<Integer> ll = new LinkedList<>();
        // add
        ll.addFirst(1);
        ll.addLast(2);
        ll.add(3); // addLast
        ll.addAll(Arrays.asList(5,6,7));

        ll.offerFirst(3);
        ll.offerLast(4);
        ll.offer(5); // offerLast(4);

        // Retrieves, but does not remove - NULL if empty
        ll.peekFirst();
        ll.peekLast();
        ll.peek(); // peekFirst()

        // Retrieves, but does not remove - EXCEPTION
        ll.getFirst();
        ll.getLast();

        // Retrieves and removes - NULL if empty
        ll.pollFirst();
        ll.pollLast();
        ll.poll(); // pollFirst()

        // Retrieves and removes - EXCEPTION
        ll.removeFirst();
        ll.removeLast();

    }
}
