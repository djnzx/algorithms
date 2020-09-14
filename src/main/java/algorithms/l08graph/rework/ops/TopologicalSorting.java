package algorithms.l08graph.rework.ops;

import algorithms.l08graph.rework.rep.Graph;

import java.util.Collection;
import java.util.Optional;

/**
 * Graph should be Directional
 */
public interface TopologicalSorting {
  Optional<Collection<Integer>> topologicalSorting(Graph g);
}
