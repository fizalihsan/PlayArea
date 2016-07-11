package lambdas;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Comment here about the class
 * User: Fizal
 * Date: 9/19/2015
 * Time: 1:24 PM
 */
public class LambdaTest {

    private static final List<Person> PERSONS = Arrays.asList(
            new Person("firstName1", "lastName1", "address1", "NYC", 25),
            new Person("firstName4", "lastName4", "address4", "JerseyC", 72),
            new Person("firstName5", "lastName5", "address5", "NYC", 32),
            new Person("firstName6", "lastName6", "address6", "NYC", 18),
            new Person("firstName2", "lastName2", "address2", "Princeton", 36),
            new Person("firstName3", "lastName3", "address3", "Edison", 54)
    );

    public static void main(String[] args) {
        preJava8Filter();
    }


    private static void preJava8Filter() {
        for (Person person : PERSONS) {
            if(person.age > 18) {
                System.out.println(person);
            }
        }
    }

    private static void java8Filter() {
        PERSONS.stream()
                .filter(person -> person.age > 18)
                .forEach(System.out::println);
    }



    private static boolean isAbove(Person person, int age) {
        System.out.println("Checking age..");
        return person.age > age;
    }

    private static boolean isInCity(Person person, String city) {
        System.out.println("Checking city..");
        return person.city.equals(city);
    }

    private static Function<Person, Boolean> isInCity(String myCity) {
        return person -> person.city.equals(myCity); //lexical scoping
    }

    private static class Person {
        String firstName, lastName, address, city;
        int age;

        public Person(String firstName, String lastName, String address, String city, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.city = city;
            this.age = age;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Person{");
            sb.append("firstName='").append(firstName).append('\'');
            sb.append(", lastName='").append(lastName).append('\'');
            sb.append(", address='").append(address).append('\'');
            sb.append(", city='").append(city).append('\'');
            sb.append(", age=").append(age);
            sb.append('}');
            return sb.toString();
        }
    }


}
