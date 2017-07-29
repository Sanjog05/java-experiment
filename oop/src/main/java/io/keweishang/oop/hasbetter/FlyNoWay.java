package io.keweishang.oop.hasbetter;

/**
 * Created by kshang on 29/07/2017.
 */
public class FlyNoWay implements FlyBehavior {
  @Override
  public void fly() {
    System.out.println("I can't fly.");
  }
}
