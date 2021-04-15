package cc.javastudio.raw;

public class Person {
    public String name;
    public int age;
    public String address;
    public String licensePlate;
    public String description;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
