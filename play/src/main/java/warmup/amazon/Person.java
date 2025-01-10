package warmup.amazon;

public class Person implements Comparable<Person>{
    private final int age;
    private final int rate;
    private final CharSequence name;

    public Person(int age, int rate, CharSequence name) {
        this.age = age;
        this.rate = rate;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("warmup.amazon.Person:[name: %-8s, age:%d, rate:%d]", this.name, this.age, this.rate);
    }

    public void printMe() {
        System.out.println(this.toString());
    }

    @Override
    public int compareTo(Person o) {
        return this.age-o.age;
    }

    public int age() {
        return this.age;
    }

    public int rate() {
        return this.rate;
    }

    public CharSequence name() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return (name.hashCode()*31+rate)*31+age;
    }

    @Override
    public boolean equals(Object that_) {
        if (that_ == null) return false;
        if (that_ == this) return true;
        if (!(that_ instanceof Person)) return false;

        Person that = (Person)that_;
        return this.age==that.age
            && this.rate==that.rate
            && this.name.equals(that.name);
    }
}
