package io.keweishang.oop.hasbetter;

/**
 * Created by kshang on 29/07/2017.
 */
public class ToyDuck extends Duck {

  public ToyDuck() {
    flyBehavior = new FlyNoWay();
    quackBehavior = new Squeak();
  }

  @Override
  public void display() {
    System.out.println("I'm a toy!");
  }
}
