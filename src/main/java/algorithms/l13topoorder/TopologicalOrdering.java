package algorithms.l13topoorder;

import java.util.*;
import java.util.stream.Collectors;

public class TopologicalOrdering {

  private static String intToStr(Integer n) {
    return Integer.toString(n);
  }

  public static class Graph {
    int E, V;
    Set<Integer>[] adjacent;

    Graph(int V) {
      this.V = V;
      adjacent = new Set[V];
      for (int i = 0; i < adjacent.length; i++) {
        adjacent[i] = new HashSet<Integer>();
      }
    }

    public int V() {
      return V;
    }

    public int E() {
      return E;
    }

    public void add(int u, int v) {
      if (!adjacent[u].contains(v)) {
        E++;
        adjacent[u].add(v);
      }
    }

    public Integer[] adj(int v) {
      Integer[] vertices = new Integer[adjacent[v].size()];
      return adjacent[v].toArray(vertices);
    }

  }

  public static Graph readGraph(Scanner in) {
    int V = in.nextInt();
    int E = in.nextInt();
    Graph graph = new Graph(V);
    for (int i = 0; i < E; i++) {
      graph.add(in.nextInt(), in.nextInt());
    }
    return graph;
  }

  public static Set<Integer> findStarts(Graph g) {
    // we have to find who no parents, i.e. starts.
    HashSet<Integer> set = new HashSet<>();
    for (int v = 0; v < g.V; v++) {
      if (hasNoIncoming(g, v)) {
        set.add(v);
      }
    }
    return set;
  }

  public static boolean hasEdges(Graph g) {
    for (int v = 0; v < g.V; v++) {
      if (g.adj(v).length > 0) {
        return true;
      }
    }
    return false;
  }

  public static void removeEdge(Graph g, int u, int v) {
    if (g.adjacent[u].contains(v)) {
      g.adjacent[u].remove(v);
    }
  }

  public static boolean hasNoIncoming(Graph g, int end) {
    // walk through the whole graph
    for (int start = 0; start < g.V; start++) {
      Integer[] ends = g.adj(start);
      // walk through finishes and check our finish
      for (Integer v : ends) {
        if (v == end) {
          return false;
        }
      }
    }
    return true;
  }

  public static String topologicalSort1(Graph graph) {
    // Empty list that will contain the sorted elements
    ArrayList<Integer> L = new ArrayList<>();
    // Set of all nodes with no incoming edge
    Set<Integer> S = findStarts(graph);

    System.out.printf("Initial set:%s\n", S);
    // algorithm
    while (!S.isEmpty()) {
      // walk through all elements from S
      for (Integer nodeN : S) {
        // remove it from S
        S.remove(nodeN);
        // add it to tail L
        L.add(nodeN);
        // for each node m with an edge e from n to m do
        Integer[] nodesFromN = graph.adj(nodeN);
        for (Integer nodeM : nodesFromN) {
          // remove edge e:(nodeN->nodeM) from the graph
          removeEdge(graph, nodeN, nodeM);
          // if nodeM has no other incoming edges then
          if (hasNoIncoming(graph, nodeM)) {
            //insert nodeM into S
            S.add(nodeM);
          }
        }
      }
    }

    if (hasEdges(graph)) {
      return "The Graph has at least one cycle";
    } else {
      return L.stream().map(TopologicalOrdering::intToStr).collect(Collectors.joining(" "));
    }
  }

  public static String topologicalSort2(Graph g) {
    // Create a array to store indegrees of all
    // vertices. Initialize all indegrees as 0.
    int indegree[] = new int[g.V];

    // Traverse adjacency lists to fill indegrees of
    // vertices. This step takes O(V+E) time
    for (int i = 0; i < g.V; i++) {
      Integer[] temp = g.adj(i);
      for (int node : temp) {
        indegree[node]++;
      }
    }

    // Create a queue and enqueue all vertices with
    // indegree 0
    Queue<Integer> q = new LinkedList<Integer>();
    for (int i = 0; i < g.V; i++) {
      if (indegree[i] == 0)
        q.add(i);
    }

    // Initialize count of visited vertices
    int cnt = 0;

    // Create a vector to store result (A topological
    // ordering of the vertices)
    Vector<Integer> topOrder = new Vector<Integer>();
    while (!q.isEmpty()) {
      // Extract front of queue (or perform dequeue)
      // and add it to topological order
      int u = q.poll();
      topOrder.add(u);

      // Iterate through all its neighbouring nodes
      // of dequeued node u and decrease their in-degree
      // by 1
      for (int node : g.adj(u)) {
        // If in-degree becomes zero, add it to queue
        if (--indegree[node] == 0)
          q.add(node);
      }
      cnt++;
    }

    // Check if there was a cycle
    if (cnt != g.V) {
      return "There exists a cycle in the graph";
    }

    StringBuilder sb = new StringBuilder();
    // Print topological order
    for (int i : topOrder) {
      sb.append(i).append(" ");
    }
    return sb.toString();
  }




  public static void main(String[] args) {
    String input = "4 6\n" +
        "0 2\n" +
        "0 3\n" +
        "0 1\n" +
        "1 3\n" +
        "1 2\n" +
        "3 2";

    Scanner in = new Scanner(input);
    Graph graph = readGraph(in);

    for (int i = 0; i < graph.V; i++) {
      System.out.printf("Vertice:%d\n",i);
      System.out.printf("Childs list:%s\n", Arrays.stream(graph.adj(i)).map(x->Integer.toString(x)).collect(Collectors.joining(",")));
    }
    System.out.printf("Checking whether the Graph contains cycles: %s\n", hasCycles(graph));

    for (int i = 0; i < graph.V; i++) {
      System.out.printf("Vertex:%d, no parent: %s\n",i,hasNoIncoming(graph,i));
    }

    System.out.println(topologicalSort1(graph));
    System.out.println(topologicalSort2(graph));

/*
        for (Integer el : L) {
            System.out.print(el+" ");
        }
*/
  }

  private static boolean hasCycles(Graph graph, int curVertex, boolean[] visited, HashSet<Integer> path) {
    visited[curVertex] = true;
    path.add(curVertex);
    for(int vertex : graph.adj(curVertex)) {
      if(!visited[vertex]) {
        if (hasCycles(graph, vertex, visited, path)){
          return true;
        }
      } else if (path.contains(vertex)) {
        return true;
      }
    }
    path.remove(curVertex);
    return false;
  }

  private static boolean hasCycles(Graph graph) {
    boolean[] visited = new boolean[graph.V];
    HashSet<Integer> path = new HashSet<>();

    for (int i = 0; i < graph.V; i++) {
      if(!visited[i] && hasCycles(graph, i, visited, path)) {
        return true;
      }
    }
    return false;
  }
}
