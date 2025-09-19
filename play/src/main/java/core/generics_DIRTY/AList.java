package core.generics_DIRTY;

public class AList<T> {
    private final T[] data;

    public AList(int size) {
        this.data = (T[]) new Object[size];
    }

    void put(int index, T val) {
        data[index] = val;
    }

    T get(int index) {
        return data[index];
    }
}
