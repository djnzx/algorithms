package algorithms.l11unionfind.rep;

public interface UnionFind {
  int v();
  void union(int p, int q);
  boolean isConnected(int p, int q);
}
