package io.keweishang.oop.visitor;

/**
 * Created by kshang on 29/07/2017.
 * <p>
 * Ref: https://en.wikipedia.org/wiki/Visitor_pattern
 */
public class Main {
  public static void main(final String[] args) {
    final Car car = new Car();

    car.accept(new CarElementPrintVisitor());
    car.accept(new CarElementDoVisitor());
  }
}
