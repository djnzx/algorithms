package core.memorymodel_DIRTY;

public class Person {
    private int age;
    private final String name;
    private static int counter = 0;
    private final static String DEFAULT_COMPANY = "Dan.IT";
    public int zz = 33;

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Person.counter = counter;
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
        counter ++;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("warmup.amazon.Person:{name=%s, age=%d}", name, age);
    }

    public static void main(String[] args) {
        Person p1 = new Person(11, "Alex");
        Person p2 = new Person(12, "Dima");

        Person.setCounter(50);
        System.out.println(p1);
        System.out.println(p2);

        System.out.println(Person.getCounter());
        System.out.println(Person.getCounter());

        // stack
        int x1 = 5;
        // heap
        Integer x2 = new Integer(5);

        new_year(p1, p2);
        System.out.println(p1);
        System.out.println(p2);
    }

    public static void new_year(Person p_a, Person p_b) {
        p_b = null;
        p_a.zz = 77;
    }

}
