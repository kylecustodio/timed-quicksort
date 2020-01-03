// Kyle Custodio | kyc180000
// CS 3345 | Anjum Chida
// Section 001
// Project 5: Timed quicksort algorithm using various pivot strategies

import java.io.File;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Error incorrect Arguments:" + Arrays.toString(args));
            System.exit(0);
        }

        try {
            int size = Integer.parseInt(args[0]);
            File report = new File(args[1]);
            File unsorted = new File(args[2]);
            File sorted = new File(args[3]);

            ArrayList<Integer> list1 = QuickSorter.generateRandomList(size);
            /*QuickSorter.timedQuickSort(list1, QuickSorter.PivotStrategy.MEDIAN_OF_THREE_ELEMENTS);
            Random rand = new Random();
            for (int i = 0; i < (list1.size() - 1) / 10; i++) {
                QuickSorter.swapReferences(list1, rand.nextInt(list1.size()), rand.nextInt(list1.size()));
            }*/
            ArrayList<Integer> list2 = (ArrayList<Integer>) list1.clone();
            ArrayList<Integer> list3 = (ArrayList<Integer>) list1.clone();
            ArrayList<Integer> list4 = (ArrayList<Integer>) list1.clone();

            PrintWriter out = new PrintWriter(unsorted);
            out.println(list1.toString());
            out.close();

            Duration firstElem = QuickSorter.timedQuickSort(list1, QuickSorter.PivotStrategy.FIRST_ELEMENT);
            Duration randElem = QuickSorter.timedQuickSort(list2, QuickSorter.PivotStrategy.RANDOM_ELEMENT);
            Duration medianRand = QuickSorter.timedQuickSort(list3, QuickSorter.PivotStrategy.MEDIAN_OF_THREE_RANDOM_ELEMENTS);
            Duration median = QuickSorter.timedQuickSort(list4, QuickSorter.PivotStrategy.MEDIAN_OF_THREE_ELEMENTS);

            out = new PrintWriter(sorted);
            out.println(list1.toString());
            out.println(list2.toString());
            //out.println(list3.toString());
            //out.println(list4.toString());
            out.close();

            out = new PrintWriter(report);
            out.println("ARRAY SIZE = " + size);
            out.println("FIRST_ELEMENT: " + firstElem);
            out.println("RANDOM_ELEMENT: " + randElem);
            out.println("MEDIAN_OF_THREE_RANDOM_ELEMENTS: " + medianRand);
            out.println("MEDIAN_OF_THREE_ELEMENTS: " + median);
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
