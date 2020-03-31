package algorithms.l07tree;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

  Set<K> keys() {
    HashSet<K> keys = new HashSet<>();
    addAllKeys(root, keys);
    return keys;
  }

  void addAllKeys(Node x, Set<K> acc) {
    if (x == null) return;
    acc.add(x.key);
    addAllKeys(x.left, acc);
    addAllKeys(x.right, acc);
  }




}
