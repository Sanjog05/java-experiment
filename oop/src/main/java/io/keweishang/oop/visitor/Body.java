package io.keweishang.oop.visitor;

class Body implements CarElement {
  public void accept(final CarElementVisitor visitor) {
    visitor.visit(this);
  }
}