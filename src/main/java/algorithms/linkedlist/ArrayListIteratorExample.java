package algorithms.linkedlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ArrayListIteratorExample {
    public static void main(String[] args) {
        ArrayList<Object> al = new ArrayList<>();
        al.add(1);
        al.add(2);
        al.add(3);
        Iterator<Object> it = al.iterator();
        it.hasNext();
        it.next();

        ListIterator<Object> li = al.listIterator();
        li.hasNext();
        li.hasPrevious();
        li.next();
        li.previous();
    }
}
