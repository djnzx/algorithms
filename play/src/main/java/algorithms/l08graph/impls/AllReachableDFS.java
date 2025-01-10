package algorithms.l08graph.impls;

import algorithms.l08graph.ops.AllReachable;
import algorithms.l08graph.rep.Graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static algorithms.l08graph.Tools.toLL;

public class AllReachableDFS implements AllReachable {

  private final Graph g;

  public AllReachableDFS(Graph g) {
    this.g = g;
  }

  @Override
  public Collection<Integer> allReachable(int src) {
    return dfs(src);
  }

  private Collection<Integer> dfs(int src) {
    boolean[] visited = new boolean[g.v()];
    LinkedList<Integer> process = new LinkedList<>();
    process.add(src);

    while (!process.isEmpty()) {
      int curr = process.poll();
      if (visited[curr]) continue;
      visited[curr] = true;
      Collection<Integer> children = g.children(curr);
      toLL(children).descendingIterator()
        .forEachRemaining(process::addFirst);
    }

    return IntStream.range(0, visited.length)
      .filter(x -> visited[x])
      .boxed()
      .collect(Collectors.toList());
  }

}
