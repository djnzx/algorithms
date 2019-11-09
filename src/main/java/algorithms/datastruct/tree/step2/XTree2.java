package algorithms.datastruct.tree.step2;

import algorithms.datastruct.tree.core.XAbstractTree;
import algorithms.datastruct.tree.core.XNode;
import algorithms.datastruct.tree.core.XTree;

public class XTree2<T> extends XAbstractTree<T> implements XTree<T> {
  @Override
  public void add(T value) {
    if (root == null) {
      root = new XNode<>(value);
    } else {
      throw new IllegalArgumentException("XTree:add:Hasn't implemented yet");
    }
  }

  private boolean contains(T value, XNode<T> from) {
    // we reached the empty node
    if (from == null) return false;
    if (from.value == value) return true;
    return contains(value, from.left) || contains(value, from.right);
  }

  @Override
  public boolean contains(T value) {
    return contains(value, root);
  }

  @Override
  public void remove(T value) {
    throw new IllegalArgumentException("XTree:remove:Hasn't implemented yet");
  }
}
