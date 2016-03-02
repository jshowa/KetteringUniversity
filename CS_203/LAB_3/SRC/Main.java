import java.util.*;

/***************************************************************************
 * <B>Class:</B> maxSum <BR>
 * CS-203, Spring 2009 <BR>
 * <B>Programming Assignment 3</B> <BR>
 * <B>Description:</B> This class defines main where the program begins <BR>
 *                     execution and defines the brute force and divide <BR>
 *                     and conquer algorithms for finding the maximum <BR>
 *                     sum in a consecutive sequence in an array of size <BR>
 *                     N. <BR>
 * @author Jacob Howarth
 * @version 1.0
 ***************************************************************************
 */
public class Main {

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> main <BR>
     * <B>Description:</B>  Execution point of program for finding the <BR>
     *                      max sum of a consecutive sequence in an array <BR>
     *                      of N integers. <BR>
     * </P>
     * <!--Parameters-->
     * @param String[] - an array of strings representing the command
     *                   line arguments.
     **********************************************************************
     */
    public static void main(String[] args) {
        
        /* *************************VARIABLE TABLE*************************
         * Scanner con_in - Scanner object to store user input from console
         * String array_len - user input array length
         * String element - user input array element
         * int[] array - array used to find max continguous sum sequence of N
         *               numbers.
         * int array_elem - converted user input element from string to integer
         * boolean error - logic on/off switch to control error checking
         *                 for user input.
         * ****************************************************************
         */
        Scanner con_in = new Scanner(System.in);
        String array_len = "", element;
        boolean error = true; // assume error will occur
        int array_elem  = 0;
        int[] array;

        while (error) {
            System.out.print("Please enter an array size: ");
            array_len = con_in.next();

            // if the user input array length
            // matches a positive integer excluding zero
            // turn off error check switch
            //
            if (array_len.matches("[\\d&&[^0]]+"))
                error = false;
            else
                System.out.println("Error: please input only a positive integer.");

        }

        array = new int[Integer.parseInt(array_len)]; // create array
        error = true; // turn error switch back on to check invalid array elements
                        // as entered by user.

        // for each position in the array, ask the user to enter a positive
        // or negative integer
        for (int i = 0; i < array.length; i++) {
            while (error) {
                System.out.print("Enter an integer: ");
                element = con_in.next();

                // if the user enters an element
                // that matches a digit or a digit
                // with a negative sign, the turn off error
                // control switch and parse input to integer
                if (element.matches("[\\d[-\\d]]+")) {
                    error = false;
                    array_elem = Integer.parseInt(element);
                }
                else
                    System.out.println("Error: please input only an integer.");

            }

            array[i] = array_elem;
            error = true;
        }

        System.out.println();
        printMenu(con_in, array); // print the menu options to stdout


    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> printMenu <BR>
     * <B>Description:</B>  Prints a menu with two options to stdout. <BR>
     *                      Option one is findinng the max sum of a <BR>
     *                      consecutive sequence using a brute force <BR>
     *                      O(n^2) algorithm or a divide and conquer <BR>
     *                      O(nlogn) algorithm. The method also allows <BR>
     *                      the user to make a choice on the which option <BR>
     *                      to use. <BR>
     * </P>
     **********************************************************************
     */
    public static void printMenu(Scanner con_in, int[] array) {

        /* *************LOCAL VARIABLE TABLE FOR printMenu************
         * maxSum maxContSum - object that holds the max continguous
         *                     sum of a sequence in the array and the
         *                     interval indices of that sum
         * String choice - user input for menu option choice
         * boolean error - logic on/off switch to control error checking
         *                 for user input.
         * int choice_sel - parsed user input choice from menu options
         * ***********************************************************
         */
        maxSum maxContSum = null;
        boolean error = true; // assume error
        int choice_sel;
        String choice = "";

        while(error) { // print menu options
            System.out.print("*********OPTION MENU*********\n" +
                         "*                           *\n" +
                         "* Please select an option   *\n" +
                         "* below:                    *\n" +
                         "*                           *\n" +
                         "* 1. Brute Force Algorithm  *\n" +
                         "*                           *\n" +
                         "* 2. Divide and Conquer     *\n" +
                         "*    Algorithm              *\n" +
                         "*                           *\n" +
                         "*****************************\n" +
                         "\n" +
                         "Please select an option: ");

            choice = con_in.next();

            // if the user enters an element
                // that matches a digit or a digit
                // with a negative sign, the turn off error
                // control switch and parse input to integer
            if (choice.matches("[\\d[-\\d]]+")) {
                error = false;

                choice_sel = Integer.parseInt(choice);

                switch (choice_sel) {
                    case 1: maxContSum = bruteForceMaxSum(array);
                            break;
                    case 2: maxContSum = divConqMaxSum(array, 0, array.length - 1);
                            break;
                    default: System.out.println("Error: please input only a 1 or 2.");
                             error = false;

                }
            }
            else
                System.out.println("Error: please input only a 1 or 2.");
        }

        if (maxContSum != null)
            System.out.println(maxContSum);


    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> bruteForceMaxSum <BR>
     * <B>Description:</B>  Finds the maximum sum in a consecutive sequence <BR>
     *                      in an array of N integers using a brute force <BR>
     *                      technique. This algorithm is similar to selection <BR>
     *                      sort in that it fixes one indice and then makes <BR>
     *                      a pass through the array, sum each element with <BR>
     *                      the fixed indice. After going through all n elements <BR>
     *                      it moves the fixed indice to the next position and <BR>
     *                      repeats the above process until all possible <BR>
     *                      sequences have been summed. It then reutrns the <BR>
     *                      maximum sum that it found along with the lower <BR>
     *                      and upper bounds of the range were the sum was found. <BR>
     *                      Time Complexity: O(n^2). <BR>
     * </P>
     * <!--Parameters-->
     * @param int[] - an array of N integers.
     * <!--Returns-->
     * @return maxSum - the maximum sum of a sequence contained in an
     *                  array of N integers and the upper and lower bounds
     *                  of that sequence range.
     **********************************************************************
     */
    public static maxSum bruteForceMaxSum(int[] A) {
        maxSum maxContigSumSeq = new maxSum();
        int seqSum;

        for (int i = 0; i < A.length; i++) {
            seqSum = 0; // reset sum after every array pass
            for (int j = i; j < A.length; j++) {
                seqSum += A[j];

                // if the current sequence sum is greater
                // then the current maximum sum, reset the max
                // sum and set the lower bound and upper bound
                // (i.e. left and right) of the sequence range.
                if (seqSum > maxContigSumSeq.getSum()) {
                    maxContigSumSeq.setSum(seqSum);
                    maxContigSumSeq.setLeftBound(i);
                    maxContigSumSeq.setRightBound(j);
                }
            }
        }

        // if the array contained all negative integers
        // return max sum of zero and upper and lower bounds
        // -1 and -1.
        if (maxContigSumSeq.getSum() <= 0)
            return new maxSum(0, -1, -1);
        else
            return maxContigSumSeq;

    }

    /**
     **********************************************************************
     * <P align="left">
     * <B>Method:</B> bruteForceMaxSum <BR>
     * <B>Description:</B>  Finds the maximum sum in a consecutive sequence <BR>
     *                      in an array of N integers using a divide and conquer <BR>
     *                      technique. This algorithm first recusively divides <BR>
     *                      the array in half until the array is of size one <BR>
     *                      and then finds the consecutive sums of the smaller <BR>
     *                      pieces of the array. <BR>
     *                      Time Complexity: O(nlogn)
     * </P>
     * <!--Parameters-->
     * @param int[] - an array of N integers.
     * @param low - starting point of the array division
     * @param high - end point of the array division
     * <!--Returns-->
     * @return maxSum - the maximum sum of a sequence contained in an
     *                  array of N integers and the upper and lower bounds
     *                  of that sequence range.
     **********************************************************************
     */
    public static maxSum divConqMaxSum(int[] A, int low, int high) {
        int leftSeqSum = 0, rightSeqSum = 0, maxLeftSeqSum = 0, maxRightSeqSum = 0;
        int leftSeqBound = -1; int rightSeqBound = -1;
        maxSum maxBounds = new maxSum();

        int mid = (high + low) >>> 1; // use bit shifting to prevent
                                        // 2's complement overflow

        if (high == low) {
            maxBounds.setSum(A[low]);
            maxBounds.setLeftBound(low);
            maxBounds.setRightBound(high);
            return maxBounds;
        }

        // find the consecutive sum of the left half of the array
        maxSum maxLeftBound = divConqMaxSum(A, low, mid);
        // find consecutive sum of the right half of the array
        maxSum maxRightBound = divConqMaxSum(A, mid + 1, high);

        // sum the left half of the array and store it's maximum sum
        for (int i = mid; i >= low; i--) {
            leftSeqSum += A[i];
            if (leftSeqSum > maxLeftSeqSum) {
                maxLeftSeqSum = leftSeqSum;
                leftSeqBound = i;
            }
        }

        // sum the right half of the array and store it's maximum sum
        for (int j = mid + 1; j <= high; j++) {
            rightSeqSum += A[j];
            if (rightSeqSum > maxRightSeqSum) {
                maxRightSeqSum = rightSeqSum;
                rightSeqBound = j;
            }
        }

        // add the two sums to find the consecutive sum of that array piece
        int seqSum = maxRightSeqSum + maxLeftSeqSum;

        // decide which maximum is bigger.
        if (maxLeftBound.getSum() > maxRightBound.getSum())
            if (maxLeftBound.getSum() >= seqSum)
                return maxLeftBound;
            else
                return new maxSum(seqSum, leftSeqBound, rightSeqBound);
        else
            if (maxRightBound.getSum() >= seqSum)
                return maxRightBound;
            else
                return new maxSum(seqSum, leftSeqBound, rightSeqBound);

    }

}
