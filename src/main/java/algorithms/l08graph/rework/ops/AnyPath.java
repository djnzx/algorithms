package algorithms.l08graph.rework.ops;

import java.util.Collection;
import java.util.Optional;

public interface AnyPath {
  Optional<Collection<Integer>> path(int src, int dst);
}
