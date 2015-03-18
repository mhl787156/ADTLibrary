package implementations;

import adt.Stack;

/**
 * Created by Mickey on 18/03/2015.
 */
public class StackCalculator {

    public static void main(String args){
        Stack<String> s = new Stack();

        String[] comms = args.split(" ");

        for( String str : comms){
            s.push(str);
        }

        System.out.println(eval(s));
    }

    private static double eval(Stack<String> s){
        String comm = s.pop();
        if(isOp(comm)){
            double left = eval(s);
            double right = eval(s);

            switch(comm){
                case "+":
                    return left + right;
                case "-":
                    return left - right;
                case "*":
                    return left * right;
                case "/":
                    return left / right;
            }
        }
        try{
            return Double.parseDouble(comm);
        }catch (NumberFormatException e){
            System.out.println("Please only use numbers!");
            return 0;
        }
    }

    private static boolean isOp(String op){
        switch (op){
            case "+":
            case "-":
            case "*":
            case "/":
                return true;
            default: return false;
        }
    }
}
