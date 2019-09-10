package algorithms;

public class BoxUnbox {
    public static void main(String[] args) {
        // primitive
        int i51 = 5;

        // boxing
        Integer i52 = new Integer(5);

        String s = i52.toString(); // "5"

        // unboxing
        int i53 = i52.intValue();

    }
}
