package algorithms.l08graph.impls;

import algorithms.l08graph.ops.TopologicalSorting;
import algorithms.l08graph.rep.Graph;
import algorithms.l08graph.rep.dir.GraphAL;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static algorithms.l08graph.Tools.copyGraph;

public class TopologicalSortingImpl implements TopologicalSorting {

  private final Graph g0;

  public TopologicalSortingImpl(Graph g) {
    this.g0 = g;
  }

  public boolean hasNoIncoming(Graph g, int vertex) {
    return IntStream.range(0, g.v()).boxed()
      .flatMap(src -> g.children(src).stream())
      .noneMatch(dst -> dst == vertex);
  }

  public Set<Integer> allWithNoIncoming(Graph g) {
    return IntStream.range(0, g.v())
      .filter(v -> hasNoIncoming(g, v))
      .filter(v -> !g.children(v).isEmpty())
      .boxed()
      .collect(Collectors.toSet());
  }

  public boolean hasEdges(Graph g) {
    return IntStream.range(0, g.v())
      .anyMatch(src -> !g.children(src).isEmpty());
  }

  /**
   * it dismantles graph
   * so, we cheated copy in the caller method
   */
  private Optional<Collection<Integer>> algorithm(Graph g) {
    List<Integer> outcome = new LinkedList<>();
    Set<Integer> starts = allWithNoIncoming(g);

    while (!starts.isEmpty()) {
      for (int st: starts) {
        starts.remove(st);
        outcome.add(st);

        // we need to copy, because we about to remove the edge
        Collection<Integer> dsts = new ArrayList<>(g.children(st));
        for (int dst: dsts) {
          g.removeEdge(st, dst);
          if (hasNoIncoming(g, dst)) {
            starts.add(dst);
          }
        }
      }
    }

    return hasEdges(g) ? Optional.empty() : Optional.of(outcome);
  }

  public String represent(Optional<Collection<Integer>> outcome) {
    return outcome
      .map(l -> l.stream().map(Objects::toString).collect(Collectors.joining(",", "[", "]")))
      .orElse("Graph contains cycles. can't be sorted");
  }

  @Override
  public Optional<Collection<Integer>> topologicalSorting() {
    Graph g = copyGraph(g0, () -> new GraphAL(g0.v()));
    return algorithm(g);
  }

}
