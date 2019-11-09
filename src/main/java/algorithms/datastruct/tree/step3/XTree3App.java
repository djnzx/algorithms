package algorithms.datastruct.tree.step3;

import algorithms.datastruct.tree.core.XTree;

/**
 * contains is implemented
 * add is implemented only for one base case
 * you can run some basic cases
 */
public class XTree3App {
  public static void main(String[] args) {
    XTree<Integer> tree = new XTree3<>();
    tree.add(10);
    tree.add(1);
    System.out.println(tree.contains(1));
    System.out.println(tree.contains(2));
  }
}
