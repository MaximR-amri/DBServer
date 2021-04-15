package cc.javastudio.testapp;

import cc.javastudio.raw.FileHandler;
import cc.javastudio.raw.Person;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestApp {
    public static void main(String[] args) {
        try (FileHandler fh = new FileHandler("DBServer.db")){
            fh.add("Johñ Doe", 44,"New-York","www-404",
                    "Blue VW Beetle");
            Person person = fh.readRow(0);
            System.out.println(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
