package algorithms.l07tree.step2;

import algorithms.l07tree.core.XTree;

/**
 * contains is implemented
 * add is implemented only for one base case
 * you can run some basic cases
 */
public class XTree2App {
  public static void main(String[] args) {
    XTree<Integer> tree = new XTree2<>();
    tree.add(1);
    System.out.println(tree.contains(1));
    System.out.println(tree.contains(2));
  }
}
