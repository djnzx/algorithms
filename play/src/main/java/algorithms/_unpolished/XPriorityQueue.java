package algorithms._unpolished;

public class XPriorityQueue {

    private int[] data;
    private int count;

    public XPriorityQueue(int size) {
        this.data = new int[size];
    }

    private int poll() {
        return data[0];
    }

    private void shift(int pos) {
        for (int i = count-1; i >= pos; i--) {
            data[i+1] = data[i];
        }
        count++;
    }

    private void add(int newVal) {
        int pos = find_pos(newVal);
        shift(pos);
        insert(pos, newVal);
    }

    void print() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < count; i++) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append(data[i]);
        }
        System.out.println(sb.append("]"));
    }

    void insert(int pos, int val) {
        this.data[pos] = val;
    }

    int find_pos2(int val) {
        // this.data[0..count-1]
        for (int i = 0; i < count ; i++) {
            if (data[i] >= val) {
                return i;
            }
        }
        return count;
    }

    int find_pos(int val) {
        int left = 0;
        int right = count-1;
        while (left <= right) {
            int middle = (right + left) / 2;
            if (val > data[middle]) {
                left = middle + 1;
            } else if (val < data[middle]) {
                right = middle -1;
            } else {
                return middle;
            }
        }
        return left;

    }


    public static void main(String[] args) {
        XPriorityQueue pq = new XPriorityQueue(20);
        for (int i = 1; i <= 20; i++) {
            int val = (int) (Math.random()*99);
            pq.add(val);
            pq.print();
        }
        pq.print();
    }
}
