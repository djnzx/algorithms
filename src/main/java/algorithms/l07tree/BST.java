package algorithms.l07tree;

import java.util.*;

public class BST<K extends Comparable<K>, V> {

  class Node {
    K key;
    V value;
    Node left;
    Node right;
    int n;

    public Node(K key, V value, int n) {
      this.key = key;
      this.value = value;
      this.n = n;
    }
  }

  Node root;

  int size() {
    return size(root);
  }

  int size(Node x) {
    if (x == null) return 0;
    return x.n;
  }

  Optional<V> get(K key) {
    return get(root, key);
  }

  void put(K key, V value) {
    root = put(root, key, value);
  }

  Optional<V> get(Node x, K key) {
    if (x == null) return Optional.empty();
    int cmp = key.compareTo(x.key);
    if      (cmp < 0) return get(x.left, key);
    else if (cmp > 0) return get(x.right, key);
    else return Optional.of(x.value);
  }

  Node put(Node x, K key, V value) {
    if (x == null) return new Node(key, value, 1);
    int cmp = key.compareTo(x.key);
    if      (cmp < 0) x.left = put(x.left, key, value);
    else if (cmp > 0) x.right = put(x.right, key, value);
    else x.value = value;
    x.n = size(x.left) + size(x.right) + 1;
    return x;
  }

  void remove(K key) {
    root = remove(root, key);
  }

  Node remove(Node x, K key) {
    // finish. not found
    if (x == null) return null;
    int cmp = key.compareTo(x.key);
    if      (cmp < 0) x.left = remove(x.left, key);
    else if (cmp > 0) x.right = remove(x.right, key);
    else {
      // left is empty. pull-up right
      if (x.left == null) return x.right;
      // right is empty. pull-up left
      if (x.right == null) return x.left;
      // both occupied, need more work.
      // don't touch the left. we need to find which one to pull-up
      Node tmp = x;
      // this will be the new node
      x = findLeftEmptyToLeft(tmp.right);
      // find the right
      x.right = findLeftEmptyToRight(tmp.right);
      // restore link to left sub-tree
      x.left = tmp.left;
    }
    return x;
  }

  Node findLeftEmptyToLeft(Node x) {
    if (x.left == null) return x;
    return findLeftEmptyToLeft(x.left);
  }

  Node findLeftEmptyToRight(Node x) {
    if (x.left == null) return x.right;
    x.left = findLeftEmptyToRight(x.left);
    return x;
  }

  public Optional<K> min() {
    return root == null ? Optional.empty() : min(root);
  }

  private Optional<K> min(Node x) {
    return x.left == null ? Optional.of(x.key) : min(x.left);
  }

  public Optional<K> max() {
    return root == null ? Optional.empty() : max(root);
  }

  private Optional<K> max(Node x) {
    return x.right == null ? Optional.of(x.key) : max(x.right);
  }

  public int height() {
    return height(root, 0);
  }

  private int height(Node x, int h) {
    if (x == null) return h;
    return Math.max(
        height(x.left, h+1),
        height(x.right, h+1)
    );
  }

  public int width() {
    int h = height();
    return h == 0 ? 0 : (int) Math.pow(2, h - 1);
  }

  public List<K> keys_traverse_breadth_it() {
    LinkedList<K> outcome = new LinkedList<>();
    LinkedList<Node> process = new LinkedList<>();
    LinkedList<Node> next = new LinkedList<>();
    process.add(root);

    while (!process.isEmpty() && root!=null) {
      while (!process.isEmpty()) {
        Node node = process.pollFirst();
        outcome.add(node.key);
        if (node.left != null) next.add(node.left);
        if (node.right != null) next.add(node.right);
      }
      process.addAll(next);
      next.clear();
    }

    return outcome;
  }

  public List<K> keys_traverse_breadth() {
    LinkedList<K> keys = new LinkedList<>();
    if (root!=null) keys_traverse_breadth(new LinkedList<Node>(){{ add(root); }}, keys);
    return keys;
  }

  private void keys_traverse_breadth(LinkedList<Node> process, LinkedList<K> acc) {
    if (process.isEmpty()) return;
    LinkedList<Node> next = new LinkedList<>();
    process.forEach(node -> {
      acc.add(node.key);
      if (node.left != null) next.add(node.left);
      if (node.right != null) next.add(node.right);
    });
    keys_traverse_breadth(next, acc);
  }

  Collection<K> keys() {
    List<K> keys = new LinkedList<>();
    addAllKeys(root, keys);
    return keys;
  }

  void addAllKeys(Node x, Collection<K> acc) {
    if (x == null) return;
    acc.add(x.key);
    addAllKeys(x.left, acc);
    addAllKeys(x.right, acc);
  }

  public List<K> keys_traverse_depth() {
    return keys_traverse_depth(root);
  }

  private List<K> keys_traverse_depth(Node x) {
    return (x == null) ? Collections.emptyList() : new LinkedList<K>() {{
      add(x.key);
      addAll(keys_traverse_depth(x.left));
      addAll(keys_traverse_depth(x.right));
    }};
  }

}
