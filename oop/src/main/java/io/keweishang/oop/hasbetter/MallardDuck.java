package io.keweishang.oop.hasbetter;

/**
 * Created by kshang on 29/07/2017.
 */
public class MallardDuck extends Duck {

  public MallardDuck() {
    flyBehavior = new FlyWithWings();
    quackBehavior = new Quack();
  }

  @Override
  public void display() {
    System.out.println("Hi I'm a real duck!");
  }
}
