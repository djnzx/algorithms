package algorithms.linkedlist;

public class LList {

    class Node {
        private final int val;
        private Node next = null;

        Node(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private boolean contains(int val) {
        return false;
    }

    private void remove(int val) {

    }

    private void add(int val) {

    }

    private void print() {

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
        ll.print(); // <5,7,6>
        ll.remove(7);
        ll.print(); // <5,6>
        ll.remove(6);
        ll.print(); // <5>
        ll.remove(5);
        ll.print(); // <>
    }

}
