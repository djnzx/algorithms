package algorithms.linkedlist;

public class LList {
    private Node head = null;
    private Node tail = null;

    public Node getHead() {
        return head;
    }

    public void add(int val) {
        Node node = new Node(val);

        if (head == null) {
            head = node;
        } else {
            tail.setNext(node);
        }
        tail = node;

    }

    public boolean contains(int val) {
        Node current = head;
        if (current == null) return false;

        while (current != null) {
            if (current.getVal() == val) return true;
            current = current.getNext();
        }

        return false;
    }

    public void remove(int val) {
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

    public void print() {
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

}
