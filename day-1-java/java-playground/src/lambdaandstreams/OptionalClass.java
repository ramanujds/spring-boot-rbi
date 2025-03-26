package lambdaandstreams;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class OptionalClass {

    public static void main(String[] args) {

        List<Integer> list = List.of(10,20,4,6,8,78);

        // find the first odd number

        int output = list.stream()
                .filter(e->e%2!=0)
                .findFirst()
                .orElseThrow(()->new ArithmeticException());


        System.out.println(output);

    }

}
