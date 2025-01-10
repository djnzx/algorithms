package algorithms.l10ewdig.impl;

import algorithms.l10ewdig.ops.ShortestPath;
import algorithms.l10ewdig.rep.DiEdge;
import algorithms.l10ewdig.rep.EWDiG;
import algorithms.l10ewdig.rep.EdgeWeightedDiGraph;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * dijkstra algorithm
 * should contain no loops
 *
 * E log V
 *
 * improvement:
 * we can relax in topological order.
 * for (v: topological)
 *   g.adj(v).foreach(e ->relax(e))
 */
public class ShortestPathDijkstra1 implements ShortestPath {

  private final EWDiG g;
  private final double[] distTo;
  private final DiEdge[] edgeTo;

  public ShortestPathDijkstra1(EWDiG g) {
    this.g = g;
    this.distTo = new double[g.v()];
    this.edgeTo = new DiEdge[g.v()];
  }

  private void initialize(int s) {
    for (int i = 0; i < g.v(); i++) {
      distTo[i] = Double.MAX_VALUE;
      edgeTo[i] = null;
    }
    distTo[s] = 0;
  }

  /**
   * we take the edge v -> w
   * and check the HAVING distTo[w]
   * and VIA given edge (e)
   */
  private void tryToRelax(DiEdge e) {
    int v = e.from;
    int w = e.to;
    double distToWViaE = distTo[v] + e.weight;

    if (distToWViaE < distTo[w]) {
      distTo[w] = distToWViaE;
      edgeTo[w] = e;
    }
  }

  /**
   * 1. updates the data structure
   * 2. returns all adjacent to it
   */
  private Stream<Integer> processNode(int v) {
    return g.adjStream(v).peek(this::tryToRelax).map(e -> e.to);
  }

  private void processNodes(Stream<Integer> src) {
    Set<Integer> processed = src.flatMap(this::processNode).collect(Collectors.toSet());
    if (processed.isEmpty()) return;
    processNodes(processed.stream());
  }

  /**
   * process, tail recursive version
   */
  private void process(int s) {
    initialize(s);
    processNodes(Stream.of(s));
  }

  /**
   * process, iterative
   */
  private void process_it(int s) {
    initialize(s);
    Set<Integer> next = Collections.singleton(s);
    while (!next.isEmpty())
      next = next.stream()
        .flatMap(this::processNode)
        .collect(Collectors.toSet());
  }

  @Override
  public Optional<Iterable<DiEdge>> shortestPathTo(int s, int v) {
    process(s);
    Stack<DiEdge> path = new Stack<>();
    for (DiEdge e = edgeTo[v]; e != null; e = edgeTo[e.from])
      path.push(e);

    return Optional.of(path);
  }

  @Override
  public Optional<Double> distanceTo(int s, int v) {
    process(s);
    return Optional.of(distTo[v]);
  }

  public static void main(String[] args) throws FileNotFoundException {
    EWDiG g = EdgeWeightedDiGraph.readFromFile("ewdig.txt", 8);
    ShortestPathDijkstra1 sp = new ShortestPathDijkstra1(g);
    sp.process(0);
    Arrays.stream(sp.distTo).forEach(System.out::println);
    Arrays.stream(sp.edgeTo).forEach(System.out::println);
  }
}
