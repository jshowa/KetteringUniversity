/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jshowa
 */
public class ExeTimer {

    public final static int SAMPLE_SIZE = 300;

    private static long[] sample_times = new long[SAMPLE_SIZE];

    private static boolean start = true;

    private static long start_time = 0;
    private static long total_time = 0;
    private static long average = 0;

    private static int size = 0;

    public static void startTimer() {
        if (start && size < SAMPLE_SIZE) {
            start_time = 0;
            start_time = System.nanoTime();
            start = false;
        }
        else if (!start) {
            System.out.println("Call stopTimer before starting new timer.");
        }
        else
            System.out.println("Flush time sample buffer before timer can be used.");
    }

    public static void stopTimer() {
        if (!start && size < SAMPLE_SIZE) {
            total_time = System.nanoTime() - start_time;
            start = true;
        }
        else if (!start) {
            System.out.println("Call startTimer before stop timer can be used.");
            return;
        }
        else if (size >= SAMPLE_SIZE) {
            System.out.println("Flush time sample buffer before timer can be used.");
            return;
        }

        sample_times[size] = total_time;
        total_time = 0;
        size++;
    }

    public static int getCurTimesSize() {
        return size;
    }

    public static void resetCurTimesSize() {
        size = 0;
    }

    public static long calcAverage() {
        if (size >= SAMPLE_SIZE) {
            for (int i = 0; i < SAMPLE_SIZE; i++)
                average += sample_times[i];
            average /= SAMPLE_SIZE;
            return average;
        }
        else {
            System.out.println("All " + SAMPLE_SIZE + " times must be collected before averageing.");
            return -1;
        }
    }

    public static void clearTimes() {
        for (int i = 0; i < SAMPLE_SIZE; i++)
            sample_times[i] = 0;
    }


}
