package io.keweishang.performance.hotmethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Three travellers travel around the world.
 * <p>
 * When updating the next travel place for a traveller, we have to calculate the traveller's hashcode.
 * <p>
 * Since it's always the same traveller, we have a gain of time complexity of N if we cache the hashcode of each traveller,
 * where N is the time complexity of computing a traveller's hashcode.
 * <p>
 * We can use jmc to see the hot methods section and understand where the application spent most of its time.
 * Refer to the file under resource folder: flight_recording_180112CacheHashCodeExample18445.jfr
 */
public class CacheHashCodeExample {

  private static String[] places = {"beijing", "paris", "new york", "berlin", "london", "tokyo", "sydney", "amsterdam", "oslo", "madrid"};

  public static void main(String[] args) {

    // size (thus complexity) of attributes to get the hashcode of an traveller instance
    int N = 10000;

    Traveller[] travellers = {
            new Traveller(1, "kewei", Traveller.randomAttributes(10000)),
            new Traveller(2, "nicolas", Traveller.randomAttributes(10000)),
            new Traveller(3, "charles", Traveller.randomAttributes(10000))
    };

    Map<Traveller, String> destinations = new HashMap<>();
    for (Traveller e : travellers) {
      // randomly choose a start place for each traveller
      destinations.put(e, nextPlace());
    }

    // long journeys for all travellers
    long stops = 10_000_000;
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < stops; i++) {
      for (Traveller e : travellers) {
        destinations.put(e, nextPlace());
      }
    }

    System.out.println("Finally arrived! " + destinations);
    System.out.println("Duration in seconds: " + (System.currentTimeMillis() - startTime) / 1000);
  }

  /**
   * Random next place
   *
   * @return
   */
  private static String nextPlace() {
    return places[(int) (Math.random() * (places.length))];
  }
}
