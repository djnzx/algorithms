package algorithms.datastruct.tree.core;

public class XAbstractTree<T> {
  protected XNode<T> root = null;

  public XNode<T> head() {
    return root;
  }
}
