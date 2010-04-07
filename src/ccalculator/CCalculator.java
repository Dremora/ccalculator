package ccalculator;
import java.util.Scanner;
import java.text.*;

/**
 * Simple console calculator
 * 
 * @author Dremora
 * @version 0.1 beta
 */
public class CCalculator
{
    public static void main(String[] args)
    {
        String line;
        if (args.length == 0)
        {
            System.out.println("Calculator v0.1");
            System.out.println();
            System.out.println("Input a combination of numbers, basic arithmetic operators and brackets, then press <Enter> to get a result");
        
            Scanner scanner = new Scanner(System.in);
            while(true)
            {
                System.out.print(" > ");
                line = scanner.nextLine();
                if (line.trim().toLowerCase().equals("exit"))
                {
                    break;
                }
                System.out.println(calculate(line));
            }
        }
        else
        {
            String result = "";
            for (String str: args) {
                result += str;
            }
            System.out.println(calculate(result));
        }
    }
    
    public static String calculate(String line)
    {
        try
        {            
            // Tokenize
            Block expression = new Block(line);
            return Double.toString(expression.value());
        }
        catch (ParseException e)
        {
            System.err.println(e.getMessage());
            for (int i = -3; i < e.getErrorOffset(); i++)
            {
                System.out.print(" ");
            }
            System.out.println("^");
            return e.getMessage();
        }
    }
    
    public static boolean inArray(char[] array, char value)
    {
        for (char element: array)
        {
            if (element == value) return true;
        }
        return false;
    }
}
