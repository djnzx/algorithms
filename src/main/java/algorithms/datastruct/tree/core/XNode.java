package algorithms.datastruct.tree.core;

public class XNode<T> {
  public final T value;
  public XNode<T> left;
  public XNode<T> right;

  public XNode(T value) {
    this(value, null, null);
  }

  public XNode(T value, XNode<T> left, XNode<T> right) {
    this.value = value;
    this.left = left;
    this.right = right;
  }
}
