package algorithms.linkedlist;

public class Node {
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

    public boolean hasNext() {
        return next != null;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
