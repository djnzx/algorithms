package algorithms.datastruct;

import java.util.Arrays;
import java.util.PriorityQueue;

public class PriorityQueue_case {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < 10; i++) {
            int val = (int) (Math.random()*100);
            System.out.println(val);
            pq.add(val);
        }
        System.out.println(pq.toString());
        System.out.println(Arrays.toString(pq.toArray()));
        while (!pq.isEmpty()) {
            System.out.printf("%d, ",pq.poll());
        }
    }
}
