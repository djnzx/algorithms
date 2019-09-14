package algorithms.linkedlist;

public class LLUsage03 {

    public static Node invert(Node item) {
        Node prev = null;
        Node current = item;

        while (current != null) {
            Node saved = current.getNext();
            current.setNext(prev);
            prev = current;
            current = saved;
        }

        return prev;
    }

    public static void main(String[] args) {
        LList ll = new LList();
        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.print();

        // invert
        Node newhead = invert(ll.getHead());

        // print
        while (newhead != null) {
            System.out.printf("%d ", newhead.getVal());
            newhead = newhead.getNext();
        }
    }
}
