package examples;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapExample {

    public static void main(String[] args) {

        Map<String,String> frameworks = new HashMap<>();

        frameworks.put("Spring","Java");
        frameworks.put("DJango","Python");
        frameworks.put("Angular","JavaScript");
        frameworks.put("EntityFramework","C#");
        frameworks.put("Struts","Java");
        frameworks.put("Angular","Typescript");

//        var input = "Spring";
//
//        var language = frameworks.get(input);
//
//        System.out.println(language);


        for(var key : frameworks.keySet()){
            System.out.println(key+" - "+frameworks.get(key));
        }


    }

}
