package algorithms.invertlist;

public class Node {
  final int value;
  Node next;

  Node(int value) {
    this(value, null);
  }

  Node(int value, Node next) {
    this.value = value;
    this.next = next;
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }

  String toStringWithTail() {
    StringBuilder sb = new StringBuilder();
    Node current = this;
    while (current != null) {
      sb.append(current.toString());
      current = current.next;
    }
    return sb.toString();
  }
}

