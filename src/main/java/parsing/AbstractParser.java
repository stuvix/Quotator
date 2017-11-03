package parsing;

import java.util.ArrayList;

import gui.SanitizedInputInterface;
import regex.parsing.NonTerminalEnum;
import regex.parsing.NonTerminalInterface;
import regex.parsing.Parser;

public abstract class AbstractParser {	
	protected String regex;
	
	private static ArrayList<AbstractParser> parserList; 
	
	public static AbstractParser[] getAllParsers() {
		if (parserList == null) {
			parserList = new ArrayList<>();
			parserList.add(new ChicagoBook());
			parserList.add(new TestParser());
		}
		return parserList.toArray(new AbstractParser[1]);
	}
	
	/**
	 * determines whether this style uses the given identifier.
	 * @param identifier
	 * @return
	 */
	public boolean canUse(NonTerminalEnum identifier) {
		return identifier.isIn(this.getRegex());
	}
	
	public String parse() {
		Parser parser = new Parser(this.getRegex());
		new SanitizedInputInterface();
		return parser.parse(); //TODO recognize endless loop and abort
	}
	
	protected abstract String getRegex();
	
	public abstract String toString();
}
