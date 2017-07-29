package io.keweishang.oop.hasbetter;

/**
 * Created by kshang on 29/07/2017.
 * <p>
 * HAS-A is better than IS-A.
 * <p>
 * We can change the behavior of Duck's subclasses by giving different implementations.
 * <p>
 * We don't duplicate code of the same behavior if 2 Duck's subclasses share the same behavior.
 * Because the same behavior is encapsulated in the same implementation of interface.
 * <p>
 * It's also called Strategy Pattern
 */
public abstract class Duck {
  FlyBehavior flyBehavior;
  QuackBehavior quackBehavior;

  public Duck() {
  }

  // Every duck shares the same swim behavior
  public void swim() {
    System.out.println("All ducks float!");
  }

  // Every duck can display, we can also use an interface here like FlyBehavior/QuackBehavior if we want to.
  public abstract void display();

  // Not all ducks can fly, such as toy duck, it depends on the implementation of flyBehavior.
  // Duck HAS a flyBehavior, which encapsulates the behavior.
  public void performFly() {
    flyBehavior.fly();
  }

  // Not all ducks can quack, such as wooden duck, it depends on the implementation of quackBehavior.
  // Duck HAS a quackBehavior, which encapsulates the behavior.
  public void performQuack() {
    quackBehavior.quack();
  }

  public void setFlyBehavior(FlyBehavior flyBehavior) {
    this.flyBehavior = flyBehavior;
  }

  public void setQuackBehavior(QuackBehavior quackBehavior) {
    this.quackBehavior = quackBehavior;
  }

}
