package ccalculator;
import java.text.ParseException;

/**
 * Represents the whole expression string.
 */
public class Expression extends Value {
	/**
	 * Stores parsed expression data.
	 */
	private Block block;
	
	/**
	 * Initializes expression from the string and its parsing process.
	 * 
	 * Additionally removes extra whitespace from the end of a string.
	 * 
	 * @param str             String to parse
	 * @throws ParseException Throws on a parse error
	 */
	public Expression(String str) throws ParseException {
		offset = 0;
		block = new Block(str);
		length = block.length();
		findWhitespace(str.substring(offset));
		if (offset < str.length()) {
			throw new ParseException("Unknown token", offset);
		}
	}

	/**
	 * Returns the calculated value of the expression.
	 */
	public double value() {
		return block.value();
	}
}
