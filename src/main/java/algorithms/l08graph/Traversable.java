package algorithms.l08graph;

import java.util.Collection;

public interface Traversable<T> {
  Collection<T> traverse(T source);
}
