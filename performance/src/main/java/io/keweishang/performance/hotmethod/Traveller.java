package io.keweishang.performance.hotmethod;

import java.util.Arrays;
import java.util.UUID;

public class Traveller {
  private long id;
  private String name;
  private String[] otherAttribute;
//  private Integer cachedHashCode;

  public Traveller(long id, String name, String[] otherAttribute) {
    this.id = id;
    this.name = name;
    this.otherAttribute = otherAttribute;
  }

  /**
   * Generate some random attributes
   *
   * @param size
   * @return
   */
  public static String[] randomAttributes(int size) {
    String[] res = new String[size];
    for (int i = 0; i < res.length; i++) {
      res[i] = UUID.randomUUID().toString();
    }
    return res;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Traveller traveller = (Traveller) o;

    if (id != traveller.id) return false;
    if (!name.equals(traveller.name)) return false;
    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    return Arrays.equals(otherAttribute, traveller.otherAttribute);
  }

  @Override
  public int hashCode() {
//    if (cachedHashCode != null) {
//      return cachedHashCode;
//    } else {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + name.hashCode();
    result = 31 * result + Arrays.hashCode(otherAttribute);
//      cachedHashCode = result;
    return result;
//    }

  }

  @Override
  public String toString() {
    return name;
  }
}
