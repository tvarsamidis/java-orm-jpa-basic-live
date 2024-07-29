package gr.codehub.jpa;

public class Util {

    public static String email(String person) {
        return email(person, "example.com");
    }

    public static String email(String person, String company) {
        return person + "." + Math.random() + "@" + company;
    }
}
