package algorithms.linkedlist;

public class LLUsage02 {
    public static void main(String[] args) {
        LList ll1 = new LList();
        LList ll2 = new LList();
        LList ll3 = new LList();

        ll1.add(1);
        ll1.add(3);
        ll1.add(10);

        ll2.add(5);
        ll2.add(6);
        ll2.add(7);

        ll1.print();
        ll2.print();
        // ========
        Node head1 = ll1.getHead();
        Node head2 = ll2.getHead();

        // ========
        ll3.print(); // <1, 3, 5, 6, 7, 10>

    }
}
