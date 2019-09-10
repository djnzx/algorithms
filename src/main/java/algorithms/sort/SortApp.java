package algorithms.sort;

public class SortApp {

    static int[] sort1(int[] data) {
        // should be replaced with according implementation
        return data;
    }

    static int[] sort2(int[] data) {
        // should be replaced with according implementation
        return data;
    }

    static int[] sort(int[] data) {
//        return sort1(data);
        return sort2(data);
    }

    public static void main(String[] args) {
        int[] origin = Utils.create_random_data(100);
        int[] result = sort(origin);
    }
}
