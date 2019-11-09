package algorithms.datastruct.tree.step3;

import algorithms.datastruct.tree.core.XAbstractTree;
import algorithms.datastruct.tree.core.XNode;
import algorithms.datastruct.tree.core.XTree;

public class XTree3<T extends Integer> extends XAbstractTree<T> implements XTree<T> {

  private void add(T value, XNode<T> from) {
    if (value.compareTo(from.value) < 0) {
      // add to the left branch
      throw new IllegalArgumentException("XTree:add:Hasn't implemented yet");
    } else if (value.compareTo(from.value) > 0) {
      // add to the left branch
      throw new IllegalArgumentException("XTree:add:Hasn't implemented yet");
    }
    else throw new IllegalArgumentException("XTree:add:duplicate element");
  }

  @Override
  public void add(T value) {
    if (head == null) {
      head = new XNode<>(value);
    } else {
      add(value, head);
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
    return contains(value, head);
  }

  @Override
  public void remove(T value) {
    throw new IllegalArgumentException("XTree:remove:Hasn't implemented yet");
  }
}
