package io.keweishang.performance.heap.gc;

// Launch application with: -Xmx1024m -XX:+PrintGCDetails
// You can observe some young GCs and old GCs in stout
// This code 100 countries * 1000 ~ 100_000 people per country consumes 470 MB heap
public class PrintGCDetails {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start allocating countries...");
        Country[] countries = new Country[100];
        for (int i = 0; i < countries.length; i++) {
            countries[i] = new Country("country" + i, i * 1000);
        }
        System.out.println("Finished allocation countries!");
    }

    public static class Country {
        People[] allPeople;
        String name;

        public Country(String name, int population) {
            this.allPeople = new People[population];
            for (int i = 0; i < population; i++) {
                allPeople[i] = new People(name + String.valueOf(i));
            }
        }
    }

    private static class People {
        String name;

        public People(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "People{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
