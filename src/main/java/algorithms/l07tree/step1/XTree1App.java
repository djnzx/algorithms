package algorithms.l07tree.step1;

import algorithms.l07tree.core.XTree;

/**
 * code is compilable
 * you can keep coding
 */
public class XTree1App {
  public static void main(String[] args) {
    XTree<Integer> tree = new XTree1<>();

    tree.add(1);
    tree.add(2);

    tree.contains(1);
    tree.contains(3);

    tree.remove(1);
  }
}
