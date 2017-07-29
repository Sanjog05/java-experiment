package io.keweishang.oop.hasbetter;

/**
 * Created by kshang on 29/07/2017.
 */
public class Quack implements QuackBehavior {
  @Override
  public void quack() {
    System.out.println("Quack!");
  }
}
