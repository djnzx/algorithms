package algorithms.tree.step1;

import algorithms.tree.core.XAbstractTree;
import algorithms.tree.core.XTree;

public class XTree1<T> extends XAbstractTree<T> implements XTree<T> {
  @Override
  public void add(T value) {
    throw new IllegalArgumentException("XTree:add:Hasn't implemented yet");
  }

  @Override
  public boolean contains(T value) {
    throw new IllegalArgumentException("XTree:contains:Hasn't implemented yet");
  }

  @Override
  public void remove(T value) {
    throw new IllegalArgumentException("XTree:remove:Hasn't implemented yet");
  }
}
