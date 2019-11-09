package algorithms.tree.step3;

import algorithms.tree.core.XTree;

import java.util.Arrays;

/**
 * contains is implemented
 * add is implemented only for one base case
 * you can run some basic cases
 */
public class XTree3App {
  public static void main(String[] args) {
    XTree<Integer> tree = new XTree3<>();
    Arrays.asList(20,10,30,5,15,25,35).forEach(tree::add);
    System.out.println(tree.contains(10)); // t
    System.out.println(tree.contains(20)); // t
    System.out.println(tree.contains(21)); // f
    System.out.println(tree);
  }
}
