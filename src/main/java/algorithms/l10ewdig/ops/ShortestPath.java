package algorithms.l10ewdig.ops;

import algorithms.l10ewdig.rep.DiEdge;

import java.util.Optional;

/**
 * properties:
 *
 */
public interface ShortestPath {
  Optional<Iterable<DiEdge>> shortestPathTo(int s, int v);
  Optional<Double> distanceTo(int s, int v);
}
