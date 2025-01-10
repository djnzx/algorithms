package algorithms.l04linkedlist;

public class XMerge {
  XLinkedList.XItem merge(XLinkedList xl1,
                          XLinkedList xl2) {
    XLinkedList.XItem current1 = xl1.getHead();
    XLinkedList.XItem current2 = xl2.getHead();
    XLinkedList.XItem head;
    XLinkedList.XItem current;

    if (current1.value <= current2.value) {
      head = current1;
      current1 = current1.next;
    } else {
      head = current2;
      current2 = current2.next;
    }
    current = head;

    while (current1 != null && current2 != null) {
      if (current1.value <= current2.value) {
        current.next = current1;
        current1 = current1.next;
      } else {
        current.next = current2;
        current2 = current2.next;
      }
      current = current.next;
    }

    if (current1 != null) {
      current.next = current1;
    }

    if (current2 != null) {
      current.next = current2;
    }
    return head;
  }
}
