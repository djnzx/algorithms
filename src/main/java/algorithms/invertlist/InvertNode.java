package algorithms.invertlist;

public class InvertNode {

  static Node invert(Node item) {
    Node prev = null;
    Node curr = item;
    while (curr != null) {
      Node temp = curr.next;
      curr.next = prev;
      prev = curr;
      curr = temp;
    }
    return prev;
  }

  public static void main(String[] args) {
    Node list_original =
        new Node(1,
            new Node(2,
                new Node(3,
                    new Node(4)
                )
            )
        );

    System.out.printf("orig:%s\n",
        list_original.toStringWithTail()
    );
    Node list_inverted = invert(list_original);
    System.out.printf("invt:%s\n",
        list_inverted.toStringWithTail()
    );
  }
}
