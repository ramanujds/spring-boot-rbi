package examples;

import java.util.*;
import java.util.function.Consumer;

public class ArraysAndCollection {

    public static void main(String[] args) {

        // int[] arr = new int[10];

        List<Integer> arr = new LinkedList<>();

        // int vs Integer

        Set<String> list = new TreeSet<>();

        list.add("Hello");
        list.add("World");
        list.add("Java");
        list.add("Python");
        list.add("Java");
        list.add("Python");


        list.forEach(e-> System.out.println(e));

//

//        for (var e: list) {
//            System.out.println(e.toUpperCase());
//        }

//        Queue<String> queue = new LinkedList<>();
//
//        queue.offer("Hello");
//        queue.offer("World");
//        queue.offer("Java");
//        queue.offer("Python");
//
//       while(!queue.isEmpty()) {
//           var current = queue.poll();
//           System.out.println(current);
//       }

        Stack<String> stack = new Stack<>();

        stack.push("Hello");
        stack.push("World");
        stack.push("Java");
        stack.push("Python");

        while (!stack.isEmpty()) {
            var current = stack.pop();
            System.out.println(current);
        }


    }
}
