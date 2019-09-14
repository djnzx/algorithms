package algorithms.linkedlist;

public class LList {
    private Node head = null;
    private Node tail = null;

    private void add(int val) {
        Node node = new Node(val);

        if (head == null) {
            head = node;
        } else {
            tail.setNext(node);
        }
        tail = node;

    }

    private boolean contains(int val) {
        Node current = head;
        if (current == null) return false;

        while (current != null) {
            if (current.getVal() == val) return true;
            current = current.getNext();
        }

        return false;
    }

    private void remove(int val) {
        Node current = head;
        // empty list
        if (current == null) return;

        // fist match
        if (current.getVal() == val) {
            head = current.getNext();
            // only one element
            if (tail == head) {
                tail = null;
            }
            return;
        }

        // all other cases
        while (current.hasNext()) {
            if (current.getNext().getVal() == val) {
                // check whether the last element
                if (tail == current.getNext()) {
                    tail = current;
                }
                current.setNext(current.getNext().getNext());
                return;
            }
            current = current.getNext();
        }
    }

    private void print_separator() {
        System.out.print(", ");
    }

    private void print() {
        Node current = head;
        System.out.print("<");
        if (current != null) {
            System.out.print(current.getVal());
            while (current.hasNext()) {
                print_separator();
                current = current.getNext();
                System.out.print(current.getVal());
            }
        }
        System.out.println(">");
    }

    public static void main(String[] args) {
        LList ll = new LList();
        ll.print(); // <>
        ll.add(5);
        ll.add(7);
        ll.print(); // <5,7>
        System.out.println(ll.contains(5)); // true
        System.out.println(ll.contains(6)); // false
        ll.add(6);
        System.out.println(ll.contains(6)); // true

//        for (Node el: ll) {
//            System.out.println(el);
//        }

//        int[] a = {1,2,3};
//        for (int i1: a) {
//            System.out.println(a);
//        }

        ll.print(); // <5,7,6>
        ll.remove(7);
        ll.print(); // <5,6>
        ll.remove(6);
        ll.print(); // <5>
        ll.remove(5);
        ll.print(); // <>
        ll.add(77);
        ll.add(88);
        ll.print(); // <77, 88>
    }

}
