package io.keweishang.oop.visitor;

/**
 * Created by kshang on 29/07/2017.
 *
 * Base element being visited
 */
interface CarElement {
  // the element being visited accepts the visitor
  void accept(final CarElementVisitor visitor);
}
