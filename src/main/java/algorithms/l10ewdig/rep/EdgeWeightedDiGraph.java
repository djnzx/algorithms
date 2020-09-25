package algorithms.l10ewdig.rep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Stream;

public class EdgeWeightedDiGraph implements EWDiG {
  private final int v;
  private final LinkedList<DiEdge>[] adj;

  @SuppressWarnings("unchecked")
  public EdgeWeightedDiGraph(int v) {
    this.v = v;
    this.adj = (LinkedList<DiEdge>[]) new LinkedList[v];
    for (int i = 0; i < v; i++) {
      adj[i] = new LinkedList<>();
    }
  }

  @Override
  public int v() {
    return this.v;
  }

  @Override
  public void addEdge(DiEdge e) {
    adj[e.from].add(e);
  }

  @Override
  public Iterable<DiEdge> adj(int v) {
    return Collections.unmodifiableCollection(adj[v]);
  }

  @Override
  public Stream<DiEdge> adjStream(int v) {
    return adj[v].stream();
  }

  @Override
  public Iterable<DiEdge> edges() {
    return () -> edgesStream().iterator();
  }

  @Override
  public Stream<DiEdge> edgesStream() {
    return Arrays.stream(adj).flatMap(Collection::stream);
  }

  public static EWDiG readFromFile(String fname, int vcnt) throws FileNotFoundException {
    EWDiG ewg = new EdgeWeightedDiGraph(vcnt);
    String f = EdgeWeightedDiGraph.class.getClassLoader().getResource(fname).getFile();
    try (Stream<String> lines = new BufferedReader(new FileReader(f)).lines()) {
      lines.map(line -> {
        String[] s1 = line.split(" ");
        String[] s2 = s1[0].split("-");
        double weight = Double.parseDouble(s1[1]);
        int v = Integer.parseInt(s2[0]);
        int w = Integer.parseInt(s2[1]);
        return new DiEdge(v, w, weight);
      })
        .forEach(ewg::addEdge);
      return ewg;
    }
  }
}
