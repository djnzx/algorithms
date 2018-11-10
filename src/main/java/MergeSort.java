import java.util.Arrays;

public class MergeSort {
    void merge(int arr[], int l, int m, int r) {
        int size_l = m - l + 1;
        int size_r = r - m;

        int L[] = new int [size_l];
        int R[] = new int [size_r];

        // Copy
        for (int i=0; i<size_l; ++i) {
            L[i] = arr[l+i];
        }
        for (int j=0; j<size_r; ++j) {
            R[j] = arr[m+1+j];
        }

        // Merge
        int i = 0, j = 0;
        // Initial index of merged subarry array
        int k = l;
        while (i < size_l && j < size_r) {
            if (L[i] <= R[j]) {
                arr[k] = L[i++];
            } else {
                arr[k] = R[j++];
            }
            k++;
        }

        while (i < size_l) {
            arr[k++] = L[i++];
        }

        while (j < size_r) {
            arr[k++] = R[j++];
        }
    }

    void sort(int arr[], int l, int r) {
        if (l < r) {
            int m = (l+r)/2;
            sort(arr, l, m);
            sort(arr, m+1, r);
            merge(arr, l, m, r);
        }
    }

    public static void main(String args[]) {
        int arr[] = {12, 11, 13, 5, 6, 7, 1, 99, 2};
        System.out.printf("Given Array:\n%s\n", Arrays.toString(arr));
        new MergeSort().sort(arr, 0, arr.length-1);
        System.out.printf("Sorted Array:\n%s\n", Arrays.toString(arr));
    }
}
