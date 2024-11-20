package edu.ktu.ds.lab3.demo;

import edu.ktu.ds.lab3.utils.*;

import java.util.Locale;

import static edu.ktu.ds.lab3.utils.HashMap.DEFAULT_INITIAL_CAPACITY;
import static edu.ktu.ds.lab3.utils.HashMap.DEFAULT_LOAD_FACTOR;

public class ManualTest {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // we standardize number formats
        executeTest();
    }

    public static void executeTest() {
        Car car1 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car car2 = new Car("Renault", "Megane", 2001, 20000, 3500);
        Car car3 = new Car("Toyota", "Corolla", 2001, 20000, 8500.8);
        Car car4 = new Car("Renault Laguna 2001 115900 7500");
        Car car5 = new Car.Builder().buildRandom();
        Car car6 = new Car("Honda   Civic  2007  36400 8500.3");
        Car car7 = new Car("Renault Laguna 2001 115900 7500");
        Car car8 = new Car("TRenault TLaguna 2021 15900 70500");
        // view key array
        String[] carsIds = {"TA156", "TA102", "TA178", "TA126", "TA105", "TA106", "TA107", "TA108","TA101"};
        // An array of view values
        Car[] cars = {car1, car2, car3, car4, car5, car6, car7,car8};

       executeCarMapTests(carsIds, cars);
      // executeCarMapOaTests(carsIds, cars);
    }

    private static void executeCarMapTests(String[] carsIds, Car[] cars)
    {
        ParsableMap<String, Car> carsMap
                = new ParsableHashMap<>(String::new, Car::new, DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR,
                HashManager.HashType.DIVISION);

        for (int id = 0; id < cars.length; id++)
        {
            carsMap.put(carsIds[id], cars[id]);
        }

        Ks.oun("Arrangement of pairs in the view by keys:");
        carsMap.println("");
        Ks.oun("Is there a pair in the picture?");
        Ks.oun(carsMap.contains(carsIds[6]));
        Ks.oun(carsMap.contains(carsIds[7]));
        Ks.oun("Arrangement of pairs in the view by keys. Only keys are shown:");
        carsMap.println("=");

        Ks.oun("\n" +
                "We are searching for pairs in the view:");
        Ks.oun(carsMap.get(carsIds[2]));
        Ks.oun(carsMap.get(carsIds[7]));
        Ks.oun("We print the view pairs in String:");
        Ks.ounn(carsMap);
        System.out.println("=========================Implementation of Remove method using Separate-chaining(HashMap) =========================");

        Ks.oun(carsMap.remove("TA156"));
        carsMap.println("");
        Ks.oun(carsMap.remove("TA178"));
        carsMap.println("");

        System.out.println("=========================Implementation of Replace method using Separate-chaining(HashMap) =========================");

        carsMap.println("");
        Ks.oun("Replace values "+carsMap.replace(carsIds[1],cars[1],cars[2]));
        carsMap.println("");

        System.out.println("=========================Implementation of ContainsValue method using Separate-chaining(HashMap) =========================");
        Ks.oun(carsMap.containsValue(cars[2]));
        Ks.oun(carsMap.containsValue(cars[1]));
        System.out.println("=========================Implementation of speed test using Separate-chaining(HashMap) in remove method =========================");

        ParsableMap<String, Car> carsMap2
                = new ParsableHashMap<>(String::new, Car::new, DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR,
                HashManager.HashType.DIVISION);

        for (int id = 0; id < cars.length; id++)
        {
            carsMap2.put(carsIds[id], cars[id]);
        }
        long StartTime2;
        long EndTime2;
        long ElapsedTime2;
        Ks.oun("\n Remove Method speed-Testing: ");
        StartTime2= System.nanoTime();
        carsMap2.remove(carsIds[3]);
        EndTime2= System.nanoTime();
        ElapsedTime2=EndTime2-StartTime2;
        Ks.oun("Separate-chaining(HashMap)-> Re;move method takes:\t"+ElapsedTime2+"ns");
        Ks.oun("======================================Implementation of ReplaceAll in HashMap================================================================");
        Ks.oun("Printing the carsMap");
        Ks.oun("Replace values "+carsMap.replace(carsIds[7],cars[7],cars[5]));
        carsMap.println("");
        carsMap.replaceAll(cars[5],cars[2]);
        Ks.oun("Print after replaceAll");
        carsMap.println("");
       // replaceAll(V oldValue, V newValue)
    }

    private static void executeCarMapOaTests(String[] carsIds, Car[] cars) {
        ParsableMap<String, Car> carsMapOa
                = new ParsableHashMapOa<>(String::new, Car::new, DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, HashManager.HashType.DIVISION, HashMapOa.OpenAddressingType.LINEAR);

        for (int id = 0; id < cars.length; id++) {
            carsMapOa.put(carsIds[id], cars[id]);
        }

        Ks.oun("Location of pairs in the open address view by keys:");
        carsMapOa.println("");
        Ks.oun("Is there a pair in the open address view?");
        Ks.oun(carsMapOa.contains(carsIds[6]));
        Ks.oun(carsMapOa.contains(carsIds[7]));
        Ks.oun("Arrangement of pairs in an open addressing view by keys. Only keys are shown:");
        carsMapOa.println("=");

        Ks.oun("We are searching for pairs in the open address view:");
        Ks.oun(carsMapOa.get(carsIds[2]));
        Ks.oun(carsMapOa.get(carsIds[7]));
        Ks.oun("We print the pairs of the open address view in the String line:");
        Ks.ounn(carsMapOa);
        System.out.println("=========================Implementation of Remove method using Open-addressing(HashMapOa) =========================");
        carsMapOa.println("");
        carsMapOa.remove(carsIds[1]);
        carsMapOa.println("");
    /*    carsMapOa.put(carsIds[1], cars[1]);
        carsMapOa.println("");*/
        carsMapOa.put(carsIds[8], cars[7]);
        carsMapOa.println("");
        System.out.println("=========================Implementation of Replace method using Open-addressing(HashMapOa) =========================");
        Ks.oun("Replace values "+carsMapOa.replace(carsIds[8],cars[7],cars[2]));
        carsMapOa.println("");
        System.out.println("=========================Implementation of ContainsValue method using Open-addressing(HashMapOa) =========================");
        Ks.oun(carsMapOa.containsValue(cars[2]));
        Ks.oun(carsMapOa.containsValue(cars[1]));

        ParsableMap<String, Car> carsMapOa2
                = new ParsableHashMapOa<>(String::new, Car::new, DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR,
                HashManager.HashType.DIVISION, HashMapOa.OpenAddressingType.LINEAR);
        for (int id = 0; id < cars.length; id++)
        {
            carsMapOa2.put(carsIds[id], cars[id]);
        }

        long StartTime1;
        long EndTime1;
        long ElapsedTime1;

        Ks.oun("\n Remove Method speed-Testing: ");
        StartTime1= System.nanoTime();
        carsMapOa2.remove(carsIds[3]);
        EndTime1= System.nanoTime();
        ElapsedTime1=EndTime1-StartTime1;
        Ks.oun("Open addressing(HashMapOa)-> Remove method takes:\t"+ElapsedTime1+"ns");

        Ks.oun("============================================Implementation of Values method in HashMapOa======================================");
        Ks.oun("Printing the carsMapOa");
        carsMapOa.println("");
     /*  // carsMapOa.Values();
        for(Car c :  carsMapOa.Values())
        {
            Ks.oun(c.toString());
        }*/

        for(String k :  carsMapOa.keySet())
        {
            Ks.oun(k.toString());
        }
    }
}
