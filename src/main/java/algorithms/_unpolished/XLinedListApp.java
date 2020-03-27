package algorithms._unpolished;

import java.util.LinkedList;

public class XLinedListApp {

    LinkedList<Integer> queue = new LinkedList<>();

    void produce() {
        int el = (int) (Math.random()*100);
        queue.addFirst(el);
    }

    int get() {
        return queue.pollLast();
//        queue.getLast(); // last or Exception
//        return queue.poll(); // 1-st or null
//        queue.remove(); // 1-st or Exception
    }

    void print() {
        System.out.println(queue);
    }

    public static void main(String[] args) {
        XLinedListApp app = new XLinedListApp();
        for (int i = 0; i < 10; i++) {
            app.produce();
            app.print();
            if (app.queue.size()>2) {
                System.out.println(app.get());
                app.print();
            }
        }
    }
}
