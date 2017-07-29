package io.keweishang.oop.hasbetter;

/**
 * Created by kshang on 29/07/2017.
 */
public class Main {
  public static void main(String[] args) {
    Duck toy = new ToyDuck();
    toy.swim();
    toy.display();
    toy.performFly();
    toy.performQuack();

    Duck duck = new MallardDuck();
    duck.swim();
    duck.display();
    duck.performFly();
    duck.performQuack();
  }
}
