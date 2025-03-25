package additionalfeatures;

public class SwitchExpression {

    public static void main(String[] args) {

        int day = 6;
        String message= switch (day){
                     case 1-> "Monday";
                     case 2-> "Tuesday";
                     case 6,7-> {
                         System.out.println("Have fun");
                         yield "Weekend";
                     }
                    default -> throw new IllegalArgumentException("Invalid Day");
                };

        System.out.println(message);

//        switch (day){
//            case 1:
//                message="Monday";
//            case 2:
//                message="Tuesday";
//                break;
//            default:
//               throw new  IllegalArgumentException("Invalid Day");
//
//        }



    }

}
