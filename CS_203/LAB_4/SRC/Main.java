/* @author Jacob Howarth
 * Class: CS-203, Spring 2009
 * Programming Assignment 4
 *
 * Description: This class contains the program execution point along
 *              with the quicksort and shell sort algorithms. It
 *              also collects the execution times for the algorithms
 *              as well.
 *
 * File created on May 30, 2009
 */

public class Main {

    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {  
        final int SAMPLE_SIZE = 500;
        final int ARRAY_SIZE_MAX = 1000;
        long[] randArray;
        long curr_time = 0;
        long diff_time = 0;
        long total_time = 0;

        double[] qsortAvgTPerN = new double[100];
        double[] shsortAvgTPerN_hs1 = new double[100];
        double[] shsortAvgTPerN_hs2 = new double[100];
        int[] hseq1;
        int[] hseq2;

        /***********QUICKSORT DATA COLLECTION*****************************/

        for (int i = 10, n = 0; i <= ARRAY_SIZE_MAX; i += 10, n++) {
            randArray = createRandArray(i);

            for (int k = 0; k < SAMPLE_SIZE; k++) {
                curr_time = System.nanoTime();
                qsort(randArray, 0, randArray.length - 1);
                diff_time = System.nanoTime() - curr_time;
                total_time += diff_time;
                //clearRandArray(randArray);
                randArray = createRandArray(i);
            }

            qsortAvgTPerN[n] = (((double)total_time * 1e-9) / 1e-6) / (SAMPLE_SIZE);
        }

        System.out.println("---QUICK SORT TIME DATA---");

        for (int i = 0, j = 10; i < 100; i++, j += 10) {
            System.out.println(j + " = " + qsortAvgTPerN[i]);
        }

        /******************************************************************/

        // clear all time variables
        curr_time = 0;
        diff_time = 0;
        total_time = 0;

        /*******************SHELLSORT HSEQ_1 DATA COLLECTION***************/
        
        for (int i = 10, n = 0; i <= ARRAY_SIZE_MAX; i += 10, n++) {
            randArray = createRandArray(i);

            for (int k = 0; k < SAMPLE_SIZE; k++) {
                hseq1 = hseq1(randArray.length);
                curr_time = System.nanoTime();
                shsort(randArray, hseq1);
                diff_time = System.nanoTime() - curr_time;
                total_time += diff_time;
                //clearRandArray(randArray);
                randArray = createRandArray(i);
            }

            shsortAvgTPerN_hs1[n] = (((double)total_time * 1e-9) / 1e-6) / (SAMPLE_SIZE);
        }

        System.out.println("---SHELL SORT TIME DATA: H-SEQ 1---");

         for (int i = 0, j = 10; i < 100; i++, j += 10) {
            System.out.println(j + " = " + shsortAvgTPerN_hs1[i]);
         }

        /******************************************************************/

        curr_time = 0;
        diff_time = 0;
        total_time = 0;

        /*******************SHELLSORT HSEQ_2 DATA COLLECTION***************/

        for (int i = 10, n = 0; i <= ARRAY_SIZE_MAX; i += 10, n++) {
            randArray = createRandArray(i);

            for (int k = 0; k < SAMPLE_SIZE; k++) {
                hseq2 = hseq2(randArray.length);
                curr_time = System.nanoTime();
                shsort(randArray, hseq2);
                diff_time = System.nanoTime() - curr_time;
                total_time += diff_time;
                //clearRandArray(randArray);
                randArray = createRandArray(i);
            }

            shsortAvgTPerN_hs2[n] = (((double)total_time * 1e-9) / 1e-6) / (SAMPLE_SIZE);
        }

        System.out.println("---SHELL SORT TIME DATA: H-SEQ 2---");

         for (int i = 0, j = 10; i < 100; i++, j += 10) {
            System.out.println(j + " = " + shsortAvgTPerN_hs2[i]);
         }

        /******************************************************************/
    }

    public static int split(long a[], int low, int high) {
        long x = a[low];
        int s = low + 1;
        long temp;

        for (int y = low + 1; y <= high; y++)
            if ( a[y] < x ) {
                temp = a[y];
                a[y] = a[s];
                a[s] = temp;
                s++;
            }

        s--;
        temp = a[low];
        a[low] = a[s];
        a[s] = temp;

        return s;
    }

    public static void qsort(long a[], int low, int high) {
        int split = 0;

        if (low < high) {
            split = split(a, low, high);
            qsort(a, low, split - 1);
            qsort(a, split + 1, high);
        }
    }

    public static void shsort(long A[], int hseq[]) {
        int h, i, j, k;
        long temp;

        for (k = 0; k < hseq.length; k++) {
            h = hseq[k];

            for (i = h; i < A.length; i++) {
                temp = A[i];
                j = i;
                while (j >= h && A[j - h] > temp) {
                    A[j] = A[j - h];
                    j = j - h;
                }
                A[j] = temp;
            }
        }
    }

    public static int[] hseq1(int length) {
        int[] hseq;

        if (length > 0) {
            hseq = new int[2];
            hseq[1] = 1;
            hseq[0] = (int)(1.732 * Math.pow(length, ((double)1 / 3)));
            return hseq;
        }

        return null;
    }

    public static int[] hseq2(int length) {
        int[] hseq;

        if (length > 0) {
            int loglen = (int)Math.log10(length);// / Math.log10(2));
            hseq = new int[loglen];
            for (int k = loglen, j = 0; k >= 1 && j < loglen; k--, j++) // 1 <= k <= logn
                hseq[j] = (int)(Math.pow(2, k) - 1);
            return hseq;
        }

        return null;
    }

    public static long[] createRandArray(int length) {
        long[] randArray = new long[length];

        // initialize the array with 10 digit numbers 1e10 to 9.99e10
        for (int j = 0; j < randArray.length; j++) {
            randArray[j] = (long)(10000000000L * Math.random());
            while (randArray[j] < 1000000000L)
                randArray[j] = (long)(10000000000L * Math.random());
        }

        //qsort(randArray, 0, randArray.length - 1);

        return randArray;
    }
}
