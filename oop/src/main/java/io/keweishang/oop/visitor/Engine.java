package io.keweishang.oop.visitor;

class Engine implements CarElement {
  public void accept(final CarElementVisitor visitor) {
    visitor.visit(this);
  }
}