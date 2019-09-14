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
        Node one = ll1.getHead();
        Node two = ll2.getHead();

        while (one !=null && two !=null) {
            int v1 = one.getVal();
            int v2 = two.getVal();

            if (v1 <= v2) {
                ll3.add(v1);
                one = one.getNext();
            } else {
                ll3.add(v2);
                two = two.getNext();
            }
        }

        while (one != null) {
            ll3.add(one.getVal());
            one = one.getNext();
        }

        while (two != null) {
            ll3.add(two.getVal());
            two = two.getNext();
        }

        // ========
        ll3.print(); // <1, 3, 5, 6, 7, 10>

    }
}
