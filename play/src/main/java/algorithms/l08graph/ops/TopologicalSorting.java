package algorithms.l08graph.ops;

import java.util.Collection;
import java.util.Optional;

/**
 * Graph should be Directional
 * Graph mustn't contain cycles
 * if graph contains cycles we return Optional.empty
 */
public interface TopologicalSorting {
  Optional<Collection<Integer>> topologicalSorting();
}
