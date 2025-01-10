package algorithms.l04linkedlist;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Dijkstra Eval
 * Two Stacks based implementation
 */
public class DijkstraEval {
  private final Stack<Double> values = new Stack<>();
  private final Stack<String> ops = new Stack<>();
  private final Collection<String> OPS = Arrays.asList("+", "*", "-", "/");

  private double doOp(double a, double b, String op) {
    switch (op) {
      case "+": return a + b;
      case "-": return a - b;
      case "*": return a * b;
      case "/": return a / b;
      default : throw new IllegalArgumentException("Operation isn't supported");
    }
  }

  public double eval(Stack<String> tokens) {
    while (!tokens.isEmpty()) {
      String tk = tokens.pop();
      if      (tk.equals("(")) ;
      else if (OPS.contains(tk)) {
        System.out.println("PUSH OP:");
        ops.push(tk);
        System.out.printf("OPS: %s\n", ops);
      }
      else if (tk.equals(")")) {
        System.out.println("DOING OP:");
        System.out.printf("OPS before: %s\n", ops);
        System.out.printf("VLS before: %s\n", values);
        String op = ops.pop();
        Double b = values.pop();
        Double a = values.pop();
        double c = doOp(a, b, op);
        values.push(c);
        System.out.printf("OPS after : %s\n", ops);
        System.out.printf("VLS after : %s\n", values);
      }
      else {
        System.out.println("PUSH NUM:");
        values.push(Double.parseDouble(tk));
        System.out.printf("VLS: %s\n", values);
      }
    }
    return values.pop();
  }

  public static void main(String[] args) {
    DijkstraEval de = new DijkstraEval();
    String input = "( ( ( 2 - 1 ) * 3 ) + ( ( 6 / 2 ) * 6 ) )";

    LinkedList<String> reversed = Arrays.stream(input.split("\\s+"))
        .collect(new Collector<String, LinkedList<String>, LinkedList<String>>() {
          @Override
          public Supplier<LinkedList<String>> supplier() {
            return LinkedList::new;
          }

          @Override
          public BiConsumer<LinkedList<String>, String> accumulator() {
            return LinkedList::addFirst;
          }

          @Override
          public BinaryOperator<LinkedList<String>> combiner() {
            return (a, b) -> {
              System.out.println("=> combiner is running");
              a.addAll(b);
              return a;
            };
          }

          @Override
          public Function<LinkedList<String>, LinkedList<String>> finisher() {
            return a -> {
              System.out.println("=> finisher is running");
              return a;
            };
          }

          @Override
          public Set<Characteristics> characteristics() {
            return new HashSet<Characteristics>() {{
              add(Characteristics.IDENTITY_FINISH);
            }};
          }
        });

    Stack<String> stack = new Stack<String>() {{ addAll(reversed); }};
    System.out.println(de.eval(stack));
  }

}
