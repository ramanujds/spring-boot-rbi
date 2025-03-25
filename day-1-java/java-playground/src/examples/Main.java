package examples;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello");
        System.out.println("We are doing some division");
        int x = 10;
        int y = 5;
        try {
            int result = x / y;
            System.out.println("result=" + result);
        } catch (ArithmeticException e) {
            System.out.println("Error : "+e.getMessage());
        }
        finally {
            System.out.println("All done");
        }


        System.out.println("Bye");


    }
}