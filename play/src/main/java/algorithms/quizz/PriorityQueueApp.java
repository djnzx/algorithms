package algorithms.quizz;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.stream.Collectors;

public class PriorityQueueApp {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new Random().ints(10, 100)
            .limit(20)
            .boxed()
            .collect(Collectors.toCollection(PriorityQueue::new));

        System.out.println(pq.toString());
        while (!pq.isEmpty()) {
            System.out.printf("%d, ",pq.poll());
        }
    }
}
