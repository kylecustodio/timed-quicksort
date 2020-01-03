// Kyle Custodio | kyc180000
// CS 3345 | Anjum Chida
// Section 001
// Project 5: Timed quicksort algorithm using various pivot strategies

import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;

public class QuickSorter
{

    /*
     * This private constructor is optional, but it does help to prevent accidental client instantiation of QuickSorter
     * via the default constructor.  (defining any constructor prevents the compiler from creating a default constructor)
     * This particular anti-instantiation technique is exactly what {@link java.util.Collections} does.
     */
    private QuickSorter() { }

    public static <E extends Comparable<E>> Duration timedQuickSort(ArrayList<E> list, PivotStrategy strategy) throws NullPointerException {
        if (list == null || strategy == null) {
            throw new NullPointerException("list or pivot strategy was null");
        }

        long startTime = System.nanoTime();

        switch (strategy) {
            case FIRST_ELEMENT:
                quicksortFirst(list, 0, list.size() - 1);
                break;
            case RANDOM_ELEMENT:
                quicksortRand(list, 0, list.size() - 1);
                break;
            case MEDIAN_OF_THREE_ELEMENTS:
                quicksortMedian3(list, 0, list.size() - 1);
                break;
            case MEDIAN_OF_THREE_RANDOM_ELEMENTS:
                quicksortMedian3Rand(list, 0, list.size() - 1);
                break;
        }

        long finishTime = System.nanoTime();
        return Duration.ofNanos(finishTime - startTime);
    }

    public static final int CUTOFF = 20;

    /**
     * Quicksort implemented with the pivot being the first element of each subarray
     * @param a the ArrayList
     * @param left the left most index
     * @param right the right most index
     * @param <E> the type of the element
     */
    private static <E extends Comparable<E>> void quicksortFirst(ArrayList<E> a, int left, int right) {
        if (left + CUTOFF <= right) {
            E pivot = a.get(left);

            int i = left, j = right;
            while (i < j) {
                i++;
                while (i <= right && a.get(i).compareTo(pivot) < 0) { i++; }
                while (j >= left && a.get(j).compareTo(pivot) > 0) { j--; }
                if (i <= right && i < j)
                    swapReferences(a, i, j);
            }

            swapReferences(a, left, j);

            quicksortFirst(a, left, j - 1);
            quicksortFirst(a, j + 1, right);
        } else {
            insertionSort(a, left, right);
        }
    }

    /**
     * Quicksort implemented with the pivot being a random element in each subarray
     * @param a the ArrayList
     * @param left the left most index
     * @param right the right most index
     * @param <E> the type of the element
     */
    private static <E extends Comparable<E>> void quicksortRand(ArrayList<E> a, int left, int right) {
        if (left + CUTOFF <= right) {
            int pivotIndex = new Random().nextInt(right - left) + left;
            swapReferences(a, pivotIndex, left);
            E pivot = a.get(left);

            int i = left, j = right;
            while (i < j) {
                i++;
                while (i <= right && a.get(i).compareTo(pivot) < 0) { i++; }
                while (j >= left && a.get(j).compareTo(pivot) > 0) { j--; }
                if (i <= right && i < j)
                    swapReferences(a, i, j);
            }

            swapReferences(a, left, j);

            quicksortRand(a, left, j - 1);
            quicksortRand(a, j + 1, right);
        } else {
            insertionSort(a, left, right);
        }
    }

    /**
     * Quicksort implemented with the pivot being the median of 3 random elements of each subarray
     * @param a the ArrayList
     * @param left the left most index
     * @param right the right most index
     * @param <E> the type of the element
     */
    private static <E extends Comparable<E>> void quicksortMedian3Rand(ArrayList<E> a, int left, int right) {
        if (left + CUTOFF <= right) {
            Random r = new Random();
            int first = r.nextInt(right - left) + left;
            int second = r.nextInt(right - left) + left;
            int third = r.nextInt(right - left) + left;
            int pivotIndex = median(a, first, second, third);
            swapReferences(a, pivotIndex, left);
            E pivot = a.get(left);

            int i = left, j = right;
            while (i < j) {
                i++;
                while (i <= right && a.get(i).compareTo(pivot) < 0) { i++; }
                while (j >= left && a.get(j).compareTo(pivot) > 0) { j--; }
                if (i <= right && i < j)
                    swapReferences(a, i, j);
            }

            swapReferences(a, left, j);

            quicksortMedian3Rand(a, left, j - 1);
            quicksortMedian3Rand(a, j + 1, right);
        } else {
            insertionSort(a, left, right);
        }
    }

    /**
     * Quicksort implemented with the pivot being the median of the first, middle, and last element of each subarray
     * @param a the ArrayList
     * @param left the left most index
     * @param right the right most index
     * @param <E> the type of the element
     */
    private static <E extends Comparable<E>> void quicksortMedian3(ArrayList<E> a, int left, int right) {
        if (left + CUTOFF <= right) {
            E pivot = median3(a, left, right);

            int i = left, j = right - 1;
            for ( ; ; ) {
                while (a.get(++i).compareTo(pivot) < 0) {}
                while (a.get(--j).compareTo(pivot) > 0) {}
                if (i < j)
                    swapReferences(a, i, j);
                else
                    break;
            }

            swapReferences(a, i, right - 1);

            quicksortMedian3(a, left, i - 1);
            quicksortMedian3(a, i + 1, right);
        } else {
            insertionSort(a, left, right);
        }
    }

    /**
     * Method to swap two elements
     * @param a the ArrayList
     * @param i1 the index of the first element
     * @param i2 the index of the second element
     * @param <E> the type of the element
     */
    public static <E> void swapReferences(ArrayList<E> a, int i1, int i2) {
        E tmp = a.get(i1);
        a.set(i1, a.get(i2));
        a.set(i2, tmp);
    }

    private static <E extends Comparable<E>> int median(ArrayList<E> a, int first, int second, int third) {
        E one = a.get(first);
        E two = a.get(second);
        E three = a.get(third);
        if ((one.compareTo(two) < 0 && two.compareTo(three) < 0) || (three.compareTo(a.get(second)) > 0 && two.compareTo(one) > 0))
            return second;
        else if ((two.compareTo(one) < 0 && one.compareTo(three) < 0) || (three.compareTo(one) > 0 && one.compareTo(two) > 0))
            return first;
        else
            return third;
    }

    /**
     * return median of first, second, and third.
     * Order these and hide the pivot.
     * @param a the ArrayList
     * @param left the first index
     * @param right the second index
     * @param <E> the type of the element
     * @return median of first, second, and third
     */
    private static <E extends Comparable<E>> E median3(ArrayList<E> a, int left, int right ) {
        int center = ( left + right ) / 2;
        if( a.get(center).compareTo( a.get(left) ) < 0 )
            swapReferences( a, left, center );
        if( a.get(right).compareTo( a.get(left) ) < 0 )
            swapReferences( a, left, right );
        if( a.get(right).compareTo( a.get(center) ) < 0 )
            swapReferences( a, center, right );

        // Place pivot at position right - 1
        swapReferences( a, center, right - 1 );
        return a.get(right-1);
    }

    /**
     * Internal insertion sort routine for subarrays that is used by quicksort
     * @param a the ArrayList
     * @param left the left most index
     * @param right the right most index
     * @param <E> the type of the element
     */
    private static <E extends Comparable<E>> void insertionSort(ArrayList<E> a, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            E tmp = a.get(i);
            int j;
            for (j = i; j > left && tmp.compareTo(a.get(j - 1)) < 0; j--)
                a.set(j, a.get(j - 1));
            a.set(j, tmp);
        }
    }

    /**
     * Creates a random ArrayList with a given size
     * @param size the size of the ArrayList
     * @return an ArrayList with a given size
     */
    public static ArrayList<Integer> generateRandomList(int size) throws IllegalArgumentException {
        if (size < 0)
            throw new IllegalArgumentException("Can't create list with negative size");
        ArrayList<Integer> list = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            list.add(rand.nextInt());
        }
        return list;
    }

    public static enum PivotStrategy {
        FIRST_ELEMENT,
        RANDOM_ELEMENT,
        MEDIAN_OF_THREE_RANDOM_ELEMENTS,
        MEDIAN_OF_THREE_ELEMENTS
    }

}
