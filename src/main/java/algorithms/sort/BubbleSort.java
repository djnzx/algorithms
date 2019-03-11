package algorithms.sort;

public class BubbleSort {
    private static int counter = 0;

    public static void swap(int[] origin, int i, int j) {
        int tmp = origin[i];
        origin[i] = origin[j];
        origin[j] = tmp;
    }

    public static int[] sort(int[] origin) {
        int[] process = origin.clone();
        for (int i = 0; i < process.length; i++) {
            for (int j = 0; j < process.length; j++) {
                if (process[i] < process[j]) {
                    swap(process, i , j);
                    counter++;
                }
            }
        }
        return process;
    }

    public static void main(String[] args) {
        int[] origin = Utils.create_random_data(100);
        Utils.printArray("Source array:", origin);
        int[] sorted = sort(origin);
        Utils.printArray("Sorted array:", sorted);
        System.out.println(counter);
    }
}
