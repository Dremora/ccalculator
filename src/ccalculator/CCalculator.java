package ccalculator;
import java.util.Scanner;
import java.text.*;

/**
 * Simple console calculator.
 * 
 * Represents a command line for calculations, each line of input is parsed,
 * the result is calculated and returned.
 * 
 * Calculations include:
 * - Basic arithmetic operators: +, -, *, /, ^
 * - Brackets support
 * - Functions support.
 * 
 * Custom functions can be added to the code. Each function should be in its own class, named as
 * capitalized function. Each function may have arbitrary number of arguments, in this case all these
 * variations of a function should be located in one class. Functions should be static.
 * 
 * @author Dremora
 * @version 0.1 beta
 */
public class CCalculator
{
	/**
	 * Program input function.
	 * 
	 * @param args If set, is used instead of stdin.
	 */
    public static void main(String[] args)
    {
        String line;
        if (args.length == 0)
        {
            System.out.println("CCalculator v0.1");
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
                try {
                	System.out.println(calculate(line));
                } catch (ParseException e)
                {
                    for (int i = -3; i < e.getErrorOffset(); i++)
                    {
                        System.err.print(" ");
                    }
                    System.err.println("^");
                    System.err.println(e.getMessage());
                }
            }
        }
        else
        {
            String result = "";
            for (String str: args) {
                result += str;
            }
            try {
				System.out.println(calculate(result));
			} catch (ParseException e) {
				System.err.println(e.getErrorOffset() + ": " + e.getMessage());
			}
        }
    }
    
    /**
     * Creates a new Expression object, performs changes of its numeric value for better output.
     * 
     * @param line            String to parse
     * @return                Calculated value or message for NaN-results
     * @throws ParseException Throws on parse error
     */
    public static String calculate(String line) throws ParseException
    {
        Expression expression = new Expression(line);
        String value = Double.toString(expression.value());
        if (value.endsWith(".0")) {
        	value = value.substring(0, value.length() - 2);
        } else if (value.equals("NaN")) {
        	value = "Can't calculate result";
        }
        return value;
    }
    
    /**
     * Helper method, checks if element is in array.
     * 
     * @param array Array to search value for
     * @param value Value to be searched
     * @return      True if found, false otherwise
     */
    public static boolean inArray(char[] array, char value)
    {
        for (char element: array)
        {
            if (element == value) return true;
        }
        return false;
    }
}
