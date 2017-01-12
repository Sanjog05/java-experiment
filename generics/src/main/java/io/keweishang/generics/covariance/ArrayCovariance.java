package io.keweishang.generics.covariance;

/**
 * Demo of Java Array's covariance
 * <p>
 * Created by kshang on 12/01/2017.
 */
public class ArrayCovariance {
    public static void main(String[] args) {

        Student[] students = {new Student("A", "Princeton")};

        // Since Student is subtype of People
        // Student[] is subtype of People[]
        People[] people = students;

        // Runtime check: throw a runtime exception ArrayStoreException.
        // Java array keeps the type of its element. It checks its type against the to-be-inserted element's type.
        people[0] = new People("B");

        // Unreachable code
        Student student = students[0];
        System.out.println("student = [" + student + "]");
    }

    private static class People {
        private String name;

        public People(String name) {
            this.name = name;
        }
    }

    private static class Student extends People {
        private String school;

        public Student(String name, String school) {
            super(name);
            this.school = school;
        }
    }
}
