package mcm;

import static mcm.Input.INPUT;
import static mcm.Input.NUMBER_OF_MATRICES;
import static mcm.Memoization.initTable;
import static mcm.Memoization.memoize;

public class Main {


    public static void main(String[] args) {
        int start = 1;
        int end = NUMBER_OF_MATRICES;
        long startTime;
        long endTime;
        long elapsedTime;
        int result;

        // A. Pure recursion
//        startTime = System.nanoTime();
//        result = recurse(start, end, INPUT);
//        endTime = System.nanoTime();
//        elapsedTime = endTime - startTime;
//        System.out.println("A. Pure recursion");
//        System.out.println("------------------------------------------------------------");
//        System.out.println("Total Number of Multiplications: " + result);
//        System.out.println("Elapsed Run Time (in nanoseconds): " + elapsedTime);

        // B. Recursion with memoization
        startTime = System.nanoTime();
        initTable();
        result = memoize(start, end, INPUT);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("B. Recursion with memoization");
        System.out.println("------------------------------------------------------------");
        System.out.println("Total Number of Multiplications: " + result);
        System.out.println("Elapsed Run Time (in nanoseconds): " + elapsedTime);
    }
}
