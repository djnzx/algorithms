package algorithms.datastruct;

public class PriorityQueue_ {

    private int[] data;
    private int count;

    public PriorityQueue_(int size) {
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

    private void add(int v) {
        this.data[count++] = v;
    }

    void print() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < this.data.length; i++) {
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

    int find_pos(int val) {
        // this.data[0..count-1]
        for (int i = 0; i < count ; i++) {
            if (data[i] >= val) {
                return i;
            }
        }
        return count;
    }


    public static void main(String[] args) {
        PriorityQueue_ pq = new PriorityQueue_(20);
        for (int i = 30; i < 40; i++) {
//            int val = (int) (Math.random()*50);
            int val = i;
            System.out.println(val);
            pq.add(val);
        }
        pq.print();

        int newVal = 33;
        int pos = pq.find_pos(newVal);
        pq.shift(pos);
        pq.insert(pos, newVal);

        pq.print();
//        System.out.println();
//        System.out.println(pq.poll());
    }
}
