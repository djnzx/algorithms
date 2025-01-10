package algorithms.l08graph.ops;

import java.util.Collection;
import java.util.Optional;

public interface PathFromTo {
  Optional<Collection<Integer>> path(int src, int dst);
}
