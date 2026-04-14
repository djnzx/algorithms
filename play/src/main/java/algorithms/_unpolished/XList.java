package algorithms._unpolished;

public class XList implements IList {
    private final static int DEFAULT_SIZE = 4;
    private int[] data;
    private int position;

    public XList() {
        this(DEFAULT_SIZE);
    }

    public XList(int capacity) {
        this.data = new int[capacity];
        position = 0;
    }

    private void resize() {
        int newLength = this.data.length << 1 | 1;
        int[] new_data = new int[newLength];
        for (int i = 0; i < this.data.length; i++) {
            new_data[i] = this.data[i];
        }
        this.data = new_data;
    }

    @Override
    public void add(int el) {
        if (position >= this.data.length) {
            resize();
        }
        this.data[position++] = el;
    }

    private void checkValid(int index) {
        if (index < 0 || index >= this.position) {
            throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public int get(int index) {
        checkValid(index);
        return this.data[index];
    }

    @Override
    public int size() {
        return this.position;
    }

    @Override
    public void remove(int index) {
        checkValid(index);
        int[] data_new = new int[this.data.length];
        for (int i = 0; i < index; i++) {
            data_new[i] = data[i];
        }
        for (int i = index+1; i < position; i++) {
            data_new[i-1] = data[i];
        }
        this.data = data_new;
        this.position--;
    }

    @Override
    public void print() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < this.position; i++) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append(data[i]);
        }
        System.out.println(sb.append("]"));
    }


    @Override
    public void remove() {
        remove(this.position);
    }

    public static void main1(String[] args) {
        XList s = new XList();
        s.add(5);
        s.add(10);
        s.add(15);
        System.out.println(s.get(0));
        System.out.println(s.get(1));
        System.out.println(s.get(2));
        System.out.println(s.get(3));
    }

    public static void main2(String[] args) {
        XList s = new XList();
        s.add(5);
        s.print();
        s.add(10);
        s.print();
        s.add(15);
        s.print();
        s.remove(1);
        s.print();
        s.remove(1);
        s.print();
        s.remove(0);
        s.print();

    }

    public static void main(String[] args) {
        XList a = new XList(0);
        a.add(1);
        a.print();
        a.add(5);
        a.print();
    }

}
