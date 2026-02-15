package ru.nsu.pisarev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.nsu.pisarev.PrimeChecker.isPrime;

public class PrimeTest {
    @Test
    public void isPrimeTest(){
        assertFalse(isPrime(0));
        assertFalse(isPrime(1));
        assertTrue(isPrime(2));
        assertFalse(isPrime(4));
        assertFalse(isPrime(6));
        assertTrue(isPrime(7));
    }

    @Test
    public void mainTest() throws InterruptedException {
        int[] warmUp = generateArray(new int[1_000],100);
        // JVM starter
        assertTrue(PrimeChecker.hasNonPrimeSequential(warmUp));
        assertTrue(PrimeChecker.hasNonPrimeSequential(warmUp));
        assertTrue(PrimeChecker.hasNonPrimeThreaded(warmUp,2));
        assertTrue(PrimeChecker.hasNonPrimeThreaded(warmUp,4));
        assertTrue(PrimeChecker.hasNonPrimeThreaded(warmUp,8));
        assertTrue(PrimeChecker.hasNonPrimeThreaded(warmUp,2));
        assertTrue(PrimeChecker.hasNonPrimeThreaded(warmUp,4));
        assertTrue(PrimeChecker.hasNonPrimeThreaded(warmUp,8));
        assertTrue(PrimeChecker.hasNonPrimeParallelStream(warmUp));
        assertTrue(PrimeChecker.hasNonPrimeParallelStream(warmUp));

        Thread.sleep(100);
        //main part
        int[] array1 = generateArray(new int[50_000],20_000_000);
        //Sequential
        long start = System.nanoTime();
        assertTrue(PrimeChecker.hasNonPrimeSequential(array1));
        long end = System.nanoTime();
        System.out.println("Sequential variation:            " + (end-start));
        Thread.sleep(100);

        //Threaded
        int[] array2 = generateArray(new int[50_000],20_000_000);
        start = System.nanoTime();
        assertTrue(PrimeChecker.hasNonPrimeThreaded(array2,2));
        end = System.nanoTime();
        System.out.println("Threaded variation(2 threads):   " + (end-start));
        Thread.sleep(100);

        int[] array3 = generateArray(new int[50_000],30_000_000);
        start = System.nanoTime();
        assertTrue(PrimeChecker.hasNonPrimeThreaded(array3,4));
        end = System.nanoTime();
        System.out.println("Threaded variation(4 threads):   " + (end-start));
        Thread.sleep(100);

        int[] array4 = generateArray(new int[50_000],40_000_000);
        start = System.nanoTime();
        assertTrue(PrimeChecker.hasNonPrimeThreaded(array4,8));
        end = System.nanoTime();
        System.out.println("Threaded variation(8 threads):   " + (end-start));
        Thread.sleep(100);

        int[] array5 = generateArray(new int[50_000],50_000_000);
        start = System.nanoTime();
        assertTrue(PrimeChecker.hasNonPrimeParallelStream(array5));
        end = System.nanoTime();
        System.out.println("ParallelStream variation:        " + (end-start));

    }

    /**
    * @param array - A link to an array which have to be full
     * @param searchFrom - A minimal number, all prime numbers will be above this number
     */
    private int[] generateArray(int[] array, int searchFrom){
        int candidate = searchFrom;
        int arrayLength = array.length;

        for (int i=0; i<arrayLength; i++){
            while(!isPrime(candidate)){
                candidate++;
            }
            array[i]=candidate;
            candidate++;
        }
        array[arrayLength-1]++;
        return array;
    }
}
