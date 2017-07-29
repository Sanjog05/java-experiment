package io.keweishang.oop.visitor;

// we can choose one visitor implementation or another when visitor elements
class CarElementDoVisitor implements CarElementVisitor {
  public void visit(final Body body) {
    System.out.println("Moving my body");
  }

  public void visit(final Car car) {
    System.out.println("Starting my car");
  }

  public void visit(final Wheel wheel) {
    System.out.println("Kicking my " + wheel.getName() + " wheel");
  }

  public void visit(final Engine engine) {
    System.out.println("Starting my engine");
  }
}