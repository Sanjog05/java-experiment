package io.keweishang.regex;

import java.util.regex.Pattern;

public class NegateWord {
  public static void main(String[] args) {
    //    The first word should be "foo", the second word should be "bar", the third word can be
    // anything
    //    Pattern p1 = Pattern.compile("foo\\.bar\\..*");
    //    // true
    //    System.out.println(p1.matcher("foo.bar.baz").matches());
    //    // false
    //    System.out.println(p1.matcher("foo.baz.baz").matches());
    //    // false
    //    System.out.println(p1.matcher("foo.barz.baz").matches());

    //    The first word should be "foo", the second word should NOT be "bar", the third word can be
    // anything
    Pattern p2 = Pattern.compile("foo\\.(?!bar\\.).*\\..*");
    // false
    System.out.println(p2.matcher("foo.bar.baz").matches());
    // true
    System.out.println(p2.matcher("foo.baz.baz").matches());
    // true
    System.out.println(p2.matcher("foo.barz.baz").matches());
  }
}
