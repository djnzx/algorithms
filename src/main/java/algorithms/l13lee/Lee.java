package algorithms.l13lee;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Lee {
  private final static int OBSTACLE = -10;
  private final static int START = -1;
  private final int dimX;
  private final int dimY;
  private final int[][] board;
  private static final int[][] allowed = { {0, -1}, {-1, 0}, {1, 0}, {0, 1} };

  public Lee(int dimX, int dimY) {
    this.dimX = dimX;
    this.dimY = dimY;
    this.board = new int[dimY][dimX];
  }

  public String formatted(LPoint point, List<LPoint> path) {
    int val = at(point);
    if (val == OBSTACLE) return " XX ";
    if (path.isEmpty()) return String.format("%3d ", val);       // intermediate steps
    if (path.contains(point)) return String.format("%3d ", val); // final step
    return " __ ";
  }

  public void printMe(List<LPoint> path) {
    for (int row = 0; row < dimY; row++) {
      for (int col = 0; col < dimX; col++) {
        LPoint p = new LPoint(col, row);
        System.out.print(formatted(p, path));
      }
      System.out.println();
    }
    System.out.println();
  }

  int at(LPoint p) {
    return this.board[p.y][p.x];
  }

  void mark(LPoint p, int val) {
    this.board[p.y][p.x] = val;
  }

  boolean isInRange(int a, int low, int hi) {
    return a >= low && a < hi;
  }

  boolean isOnBoard(LPoint p) {
    return isInRange(p.x, 0, dimX) && isInRange(p.y, 0, dimY);
  }

  boolean isUnvisited(LPoint p) {
    return at(p) == 0;
  }

  Stream<LPoint> adj(LPoint p) {
    return Arrays.stream(allowed)
      .map(dxdy -> p.move(dxdy[0], dxdy[1]));
  }

  Stream<LPoint> neighbors(LPoint p) {
    return adj(p)
      .filter(this::isOnBoard);
  }

  Stream<LPoint> neighborsUnvisited(LPoint point) {
    return neighbors(point)
      .filter(this::isUnvisited);
  }

  LPoint anyNeighborByValue(LPoint point, int value) {
    return neighbors(point)
      .filter(p -> at(p) == value)
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("shouldn't be there"));
  }

  private void initBoard() {
    IntStream.range(0, dimY).forEach(y ->
      IntStream.range(0, dimX).forEach(x ->
        board[y][x] = 0
      )
    );
  }

  private void putObstacles(Iterable<LPoint> obstacles) {
    obstacles.forEach(p -> mark(p, OBSTACLE));
  }

  private Iterable<LPoint> traceBack(LPoint start, LPoint finish, int[] counter) {
    mark(start, 0);
    LinkedList<LPoint> path = new LinkedList<>();
    path.add(finish);
    LPoint curr_p = finish;
    while (counter[0] > 0) {
      counter[0]--;
      LPoint prev_p = anyNeighborByValue(curr_p, counter[0]);
      path.addFirst(prev_p);
      curr_p = prev_p;
    }
    return path;
  }

  private Optional<Iterable<LPoint>> trace(LPoint start, LPoint finish, boolean debug) {
    boolean found = false;
    mark(start, START);
    Set<LPoint> curr = new HashSet<LPoint>() {{ add(start); }};
    int[] counter = {0}; // we need to access it from lambda
    while (!curr.isEmpty() && !found) {
      counter[0]++;
      Set<LPoint> next = curr.stream()
        .flatMap(this::neighborsUnvisited)
        .collect(Collectors.toSet());
      next.forEach(p -> mark(p, counter[0]));
      if (next.contains(finish)) found = true;
      if (debug) printMe(new ArrayList<>());
      curr = new HashSet<>(next);
    }
    return found ? Optional.of(traceBack(start, finish, counter)) : Optional.empty();
  }

  public Optional<Iterable<LPoint>> trace(LPoint start, LPoint finish, Iterable<LPoint> obstacles, boolean debug) {
    initBoard();
    putObstacles(obstacles);
    return trace(start, finish, debug);
  }
}
