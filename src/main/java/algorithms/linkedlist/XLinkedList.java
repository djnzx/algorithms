package algorithms.linkedlist;

import java.util.StringJoiner;

public class XLinkedList {

  class XItem {
    int value;
    XItem next;

    public XItem(int value) {
      this.value = value;
    }
  }

  private XItem head;

  public void add(int value) {
    XItem item = new XItem(value);
    if (head == null) {
      head = item;
    } else {
      XItem current = head;
      while (current.next != null) {
        current = current.next;
      }
      current.next = item;
    }
  }

  public boolean contains(int value) {
    XItem current = head;
    while (current != null) {
      if (current.value == value) return true;
      current = current.next;
    }
    return false;
  }

  public void remove(int value) {
    /**
     * here must be 4 different cases
     * 1. list empty
     * 2. our element is the first
     * 3. our element is the last
     * 4. our element in the middle
     */
  }

  public String toString() {
    StringJoiner sj = new StringJoiner(",");
    XItem current = head;
    while (current != null) {
      sj.add(String.valueOf(current.value));
      current = current.next;
    }
    return sj.toString();
  }

}
