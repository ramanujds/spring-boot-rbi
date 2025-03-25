package lambdaandstreams;

interface MathOperation{
    int doOperation(int a, int b);
    default int calculate(int a, int b){
        return a+b;
    }

    static int calculate(int a, int b, int c){
        return a+b+c;
    }
}

class Calculator{
    public int calculate(int a,int b, MathOperation operation){
        return operation.doOperation(a,b);
    }
}

public class FunctionalProgramming {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        int sum = calculator.calculate(5,10, (a,b)->a+b);

        int max = calculator.calculate(10,5, (a,b)->a>b?a:b);
    }

}
