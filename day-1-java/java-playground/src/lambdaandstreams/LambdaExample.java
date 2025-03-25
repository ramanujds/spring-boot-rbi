package lambdaandstreams;

interface Printable{
    void print(String s);
}

//interface Calculator{
//    int add(int a, int b);
//}

// find the length of a string using a lambda function

// find the largest number using a lambda function

interface LargestNumber{
    int findMax(int a, int b);
}


public class LambdaExample {

    public static void main(String[] args) {
        Printable printer = s -> System.out.println(s);
        printer.print("Hi");

//        Calculator calc = (a, b) -> a + b;

        LargestNumber max = (a,b)-> a > b ? a : b;

        System.out.println(max.findMax(10,4));
    }

}
