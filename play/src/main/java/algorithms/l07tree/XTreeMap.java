package algorithms.l07tree;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/** balanced tree */
public class XTreeMap<K extends Comparable<K>, V> {

    class Node {
        K key;
        V value;
        Node left;
        Node right;
        int size = 1;

        public Node(K key, V value, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node(K key, Node left, Node right) {
            this(key, null, left, right);
        }

        public Node(K key) {
            this(key, null, null);
        }

        private int size(Node node) {
            return node == null ? 0 : node.size;
        }

        private void updateSize() {
            this.size = size(this.left) + 1 + size(this.right);
        }

        private int diff() {
            return size(this.left) - size(this.right);
        }
    }

    private Node root;

    private boolean contains(K key, Node curr) {
        if (curr == null) return false;
        int cmp = key.compareTo(curr.key);
        if (cmp < 0) return contains(key, curr.left);
        else if (cmp > 0) return contains(key, curr.right);
        else /*cmp == 0*/ return true;
    }

    public boolean contains(K k) {
        return contains(k, root);
    }

    private Node put(K k, V v, Node curr) {
        if (curr == null) return new Node(k);
        int cmp = k.compareTo(curr.key);
        if (cmp < 0) curr.left = put(k, v, curr.left);
        else if (cmp > 0) curr.right = put(k, v, curr.right);
        else /*cmp == 0*/ curr.value = v;
        return curr;
    }

    public void put(K k, V v) {
        root = put(k, v, root);
    }

    public void put(K k) {
        put(k, null);
    }

    ////////////////////////////// balance related ////////////////////////////////////////////////////////////////////

    private boolean needToReBalance(int diff) {
        return Math.abs(diff) >= 2;
    }

    private Node rotateRight(Node node) {
        // links
        Node n = node.left;
        node.left = n.right;
        n.right = node;
        // sizes
        node.updateSize();
        n.updateSize();

        return n;
    }

    private Node rotateLeft(Node node) {
        // links
        Node n = node.right;
        node.right = n.left;
        n.left = node;
        // sizes
        node.updateSize();
        n.updateSize();

        return n;
    }

    private Node reBalance(Node node) {
        int diff = node.diff();
        if (!needToReBalance(diff)) return node;
        return diff > 0 ? rotateRight(node) : rotateLeft(node);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Node putB(K k, V v, Node curr) {
        if (curr == null) return new Node(k);
        int cmp = k.compareTo(curr.key);
        if (cmp < 0) curr.left = putB(k, v, curr.left);
        else if (cmp > 0) curr.right = putB(k, v, curr.right);
        else /*cmp == 0*/ curr.value = v;
        curr.updateSize();
        return reBalance(curr);
    }

    public void putB(K k, V v) {
        root = putB(k, v, root);
    }

    public void putB(K k) {
        root = putB(k, null, root);
    }

    /////////////////////////////////////////////// toString //////////////////////////////////////////////////////////

    private int depth(Node node) {
        return node == null ? 0 : 1 + Math.max(depth(node.left), depth(node.right));
    }

    public int depth() {
        return depth(root);
    }

    private String padded(K key) {
        return String.format("%3s ", key.toString());
    }


    private void putNode(String[][] grid, Node node, int row, int col, int bias) {
        grid[row][col] = padded(node.key);

        final String arrowLeft = "   /";
        final String arrawRight = "\\   ";

        if (node.left != null) {
            IntStream.range(col - bias + 1, col).forEach(i -> grid[row][i] = "____");
            grid[row + 1][col - bias] = arrowLeft;
            putNode(grid, node.left, row + 2, col - bias, bias / 2);
        }

        if (node.right != null) {
            IntStream.range(col + 1, col + bias).forEach(i -> grid[row][i] = "____");
            grid[row + 1][col + bias] = arrawRight;
            putNode(grid, node.right, row + 2, col + bias, bias / 2);
        }
    }

    public String toString() {
        if (root == null) return "x";

        int depth = depth();
        int gridW = 1 << depth;
        int gridH = (depth << 1) - 1;
        int gridC = gridW / 2;

        String CELL = "    ";
        String[] ROW = Stream.generate(() -> CELL).limit(gridW).toArray(String[]::new);

        String[][] grid = IntStream.range(0, gridH)
                .mapToObj(r -> Arrays.copyOf(ROW, ROW.length))
                .toArray(String[][]::new);

        putNode(grid, root, 0, gridC, gridC / 2);

        return Arrays.stream(grid)
                .map(row -> String.join("", row))
                .collect(Collectors.joining("\n"));
    }

}
