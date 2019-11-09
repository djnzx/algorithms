package algorithms.datastruct.tree.step3;

import algorithms.datastruct.tree.core.XAbstractTree;
import algorithms.datastruct.tree.core.XNode;
import algorithms.datastruct.tree.core.XTree;

public class XTree3<T extends Integer> extends XAbstractTree<T> implements XTree<T> {

  private void addr(T value, XNode<T> curr) {
    if (value.compareTo(curr.value) < 0) {
      System.out.printf("adding %s to the left of %s\n", value, curr.value);
      if (curr.left == null) {
        System.out.println("left is null. assigning and creating");
        curr.left = new XNode<>(value);
      } else {
        System.out.println("left is not null. gonna deeper");
        addr(value, curr.left);
      }
    } else if (value.compareTo(curr.value) > 0) {
      System.out.printf("adding %s to the right of %s\n", value, curr.value);
      if (curr.right == null) {
        System.out.println("right is null. assigning and creating");
        curr.right = new XNode<>(value);
      } else {
        System.out.println("right is not null. gonna deeper");
        addr(value, curr.right);
      }
    }
    else throw new IllegalArgumentException("XTree:add:duplicate element");
  }

  @Override
  public void add(T value) {
    if (root == null) {
      System.out.printf("adding %s to the root\n", value);
      root = new XNode<>(value);
    } else {
      addr(value, root);
    }
  }

  private boolean containsr(T value, XNode<T> curr) {
    // we reached the empty node
    if (curr == null) return false;
    // we found the target value
    if (curr.value.equals(value)) return true;
    // check children recursively
    return containsr(value, curr.left) || containsr(value, curr.right);
  }

  @Override
  public boolean contains(T value) {
    // running the recursion from the root
    return containsr(value, root);
  }

  @Override
  public void remove(T value) {
    throw new IllegalArgumentException("XTree:remove:Hasn't implemented yet");
  }


  public void add_data_r(XNode<T> curr, StringBuilder sb) {
    if (curr == null) return;
    sb.append(curr).append("\n");
    add_data_r(curr.left, sb);
    add_data_r(curr.right, sb);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    add_data_r(root, sb);
    return sb.toString();
  }
}
