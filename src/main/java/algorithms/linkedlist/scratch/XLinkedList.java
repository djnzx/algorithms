package algorithms.linkedlist.scratch;

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
    return false;
  }

  public String toString() {
    StringJoiner sj = new StringJoiner(",");
    sj.add();
    return sj.toString();
  }

}
