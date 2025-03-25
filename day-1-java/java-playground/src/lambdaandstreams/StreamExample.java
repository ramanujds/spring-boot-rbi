package lambdaandstreams;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        list.add("Hello");
        list.add("World");
        list.add("Java");
        list.add("Python");

        // find all the elements where the length>=5
        List<String> filteredList = new ArrayList<>();
        for (String e:list){
            if (e.length()>=5){
                filteredList.add(e);
            }
        }

        // convert it to uppercase then
        List<String> updatedList = new ArrayList<>();
        for (String e:filteredList){
            updatedList.add(e.toUpperCase());
        }

        System.out.println(updatedList);

        Stream<String> stream = list.stream();


        List<String> finalList = stream.filter(e->e.length() >= 5)
                                    .map(e->e.toUpperCase())
                                    .sorted()
                                    .toList();




        System.out.println(finalList);


    }

}
