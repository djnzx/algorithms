package algorithms.l07tree;

/**
 * RED link should always be at the left side
 * @param <K>
 * @param <V>
 */
public class RedBlackTree<K extends Comparable<K>, V> {
  public static final boolean RED = true;
  public static final boolean BLACK = false;

  private Node root;

  private class Node {
    final K key;
    V value;
    Node left;
    Node right;
    boolean color; // color of the link to parent

    private Node(K key, V value, boolean color) {
      this.key = key;
      this.value = value;
      this.color = color;
    }
  }

  private boolean isRed(Node x) {
    if (x == null) return false; // nulls are Black
    return x.color == RED;
  }

  private Node rotateLeft(Node h) {
    assert isRed(h.right);

    Node x = h.right;
    h.right = x.left;
    x.left = h;

    x.color = h.color;
    h.color = RED;
    return x;
  }

  private Node rotateRight(Node h) {
    assert isRed(h.left);

    Node x = h.left;
    h.left = x.right;
    x.right = h;

    x.color = h.color;
    h.color = RED;
    return x;
  }

  private void flipColor(Node h) {
    assert !isRed(h);      // incoming is BLACK
    assert isRed(h.left);  // left  is RED
    assert isRed(h.right); // right is RED

    h.color = RED;
    h.left.color  = BLACK;
    h.right.color = BLACK;
  }

  public void put(K key, V value) {
    root = put(root, key, value);
    root.color = BLACK;
  }

  /**
   * if we insert a value into sub-tree with 1 node
   * we convert it from 2-Node to 3-Node,
   * actually, we add a node with a color=RED
   * if we added to the left => OK
   * if we added to the right => rotateLeft
   * if two red in a row => rotateRight
   * if both red => flipColors
   */
  private Node put(Node h, K key, V value) {
    if (h == null) return new Node(key, value, RED);
    int cmp = key.compareTo(h.key);
    if      (cmp < 0) h.left = put(h.left, key, value);
    else if (cmp > 0) h.right = put(h.right, key, value);
    else              h.value = value; // replace with the same key

    if (isRed(h.right) && !isRed(h.left))     h = rotateLeft(h);
    if (isRed(h.left)  && isRed(h.left.left)) h = rotateRight(h);
    if (isRed(h.left)  && isRed(h.right))     flipColor(h);

    return h;
  }

}
