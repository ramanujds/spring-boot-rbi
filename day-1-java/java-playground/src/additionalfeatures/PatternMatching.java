package additionalfeatures;

public class PatternMatching {

//    static void processResponse(Object response) {
//        if (response instanceof String) {
//            String message = (String)response;
//            System.out.println("Response is a message: " + message);
//        } else if (response instanceof Integer) {
//            int errorCode = (Integer)response;
//            System.out.println("Error Code: " + errorCode);
//        }
//    }

    static String processResponse(Object response) {
       return switch (response) {
           case String message -> "Response is a message: " + message;
           case Integer errorCode -> "Error Code: " + errorCode;
           default -> "";
       };

    }

    public static void main(String[] args) {


        System.out.println(processResponse("Not Found"));


    }

}
