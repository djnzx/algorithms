package algorithms.sort;

import java.util.*;

public class BubbleSort2 {

    public static void main1(String[] args) {
        List<Integer> ll = new LinkedList<>();
        List<Integer> al = new ArrayList<>(ll);
    }

    public static int randomFromRange(int min, int max) {
        Random r = new Random();
        return min + r.nextInt(max - min);
    }

    public static int[] randomFromRangeWithLength(int min, int max, int len) {
        int[] data = new int[len];
        for (int i = 0; i < data.length; i++) {
            data[i] = randomFromRange(min, max);
        }
        return data;
    }

    static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[] data = randomFromRangeWithLength(1, 200, 100);
        System.out.println(Arrays.toString(data));

        /**
         * complexity: O(n^2/2)
         */
        for (int i = 0; i < data.length; i++) {
            for (int j = i; j < data.length; j++) {
                if (data[j] < data[i]) {
                    swap(data, i, j);
                }
            }
        }

        System.out.println(Arrays.toString(data));

    }
}
