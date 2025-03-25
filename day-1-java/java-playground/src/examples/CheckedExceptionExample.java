package examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CheckedExceptionExample {


    public static void main(String[] args) {

        File myFile = new File("hello.txt");
        try {
            Scanner scanner = new Scanner(myFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

}
