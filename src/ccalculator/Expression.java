package ccalculator;
import java.text.ParseException;

public class Expression extends Value {
	private Block block;
	
	public Expression(String str) throws ParseException {
		offset = 0;
		block = new Block(str);
		length = block.length();
		findWhitespace(str.substring(offset));
		if (offset < str.length()) {
			throw new ParseException("Unknown token", offset);
		}
	}

	public double value() {
		return block.value();
	}
}
