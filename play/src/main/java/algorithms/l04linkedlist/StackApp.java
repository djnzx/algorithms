package algorithms.l04linkedlist;

import java.util.Stack;

public class StackApp {
  public static void main(String[] args) {
    Stack<Integer> s = new Stack<>();
    s.push(1);
    s.push(2);
    s.push(3);
    /*
     * prints internal implementation
     * the same story with the PriorityQueue
     */
    System.out.println(s);

    /*
     * prints in a way intended to use
     */
    while (!s.isEmpty()) System.out.print(s.pop());
    System.out.println();
  }
}
