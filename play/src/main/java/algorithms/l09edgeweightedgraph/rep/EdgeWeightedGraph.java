package algorithms.l09edgeweightedgraph.rep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Stream;

public class EdgeWeightedGraph implements EWG {
  private final int v;
  private final LinkedList<Edge>[] adj;

  @SuppressWarnings("unchecked")
  public EdgeWeightedGraph(int v) {
    this.v = v;
    this.adj = (LinkedList<Edge>[]) new LinkedList[v];
    for (int i = 0; i < v; i++) {
      adj[i] = new LinkedList<>();
    }
  }

  @Override
  public int v() {
    return this.v;
  }

  @Override
  public void addEdge(Edge e) {
    int v = e.either();
    int w = e.other(v);
    adj[v].add(e);
    adj[w].add(e);
  }

  @Override
  public Iterable<Edge> adj(int v) {
    return Collections.unmodifiableCollection(adj[v]);
  }

  @Override
  public Iterable<Edge> edges() {
    return () -> edgesStream().iterator();
  }

  @Override
  public Stream<Edge> edgesStream() {
    return Arrays.stream(adj).flatMap(Collection::stream).distinct();
  }

  public static EWG readFromFile(String fname, int vcnt) throws FileNotFoundException {
    EWG ewg = new EdgeWeightedGraph(vcnt);
    String f = EdgeWeightedGraph.class.getClassLoader().getResource(fname).getFile();
    try (Stream<String> lines = new BufferedReader(new FileReader(f)).lines()) {
      lines.map(line -> {
        String[] s1 = line.split(" ");
        String[] s2 = s1[0].split("-");
        double weight = Double.parseDouble(s1[1]);
        int v = Integer.parseInt(s2[0]);
        int w = Integer.parseInt(s2[1]);
        return new Edge(v, w, weight);
      })
        .forEach(ewg::addEdge);
      return ewg;
    }
  }

}
