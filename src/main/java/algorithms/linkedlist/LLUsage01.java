package algorithms.linkedlist;

public class LLUsage01 {
    public static void main(String[] args) {
        LList ll = new LList();
        ll.print(); // <>
        ll.add(5);
        ll.add(7);
        ll.print(); // <5,7>
        System.out.println(ll.contains(5)); // true
        System.out.println(ll.contains(6)); // false
        ll.add(6);
        System.out.println(ll.contains(6)); // true

//        for (Node el: ll) {
//            System.out.println(el);
//        }

//        int[] a = {1,2,3};
//        for (int i1: a) {
//            System.out.println(a);
//        }

        ll.print(); // <5,7,6>
        ll.remove(7);
        ll.print(); // <5,6>
        ll.remove(6);
        ll.print(); // <5>
        ll.remove(5);
        ll.print(); // <>
        ll.add(77);
        ll.add(88);
        ll.print_recursive(); // <77, 88>
    }
}
