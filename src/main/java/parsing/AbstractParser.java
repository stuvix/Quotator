package parsing;

import java.util.ArrayList;

import gui.SanitizedInputInterface;
import regex.parsing.NonTerminalInterface;
import regex.parsing.Parser;

public abstract class AbstractParser {	
	protected String regex;
	
	private static ArrayList<AbstractParser> parserList; 
	
	public static AbstractParser[] getAllParsers() {
		if (parserList == null) {
			parserList = new ArrayList<>();
			parserList.add(new ChicagoBook());
		}
		return parserList.toArray(new AbstractParser[1]);
	}
	
	public boolean canUseAuthor() {
		return getRegex().contains(NonTerminalInterface.LAST_NAME);
	}
	
	public boolean canUseTitle() {
		return getRegex().contains(NonTerminalInterface.TITLE);
	}
	
	public boolean canUsePublisher() {
		return getRegex().contains(NonTerminalInterface.PUBLISHER);
	}
	
	public boolean canUseLocation() {
		return getRegex().contains(NonTerminalInterface.LOCATION);
	}
	
	public boolean canUseDate() {
		return getRegex().contains(NonTerminalInterface.DATE);
	}
	
	public String parse() {
		Parser parser = new Parser(this.getRegex());
		new SanitizedInputInterface();
		return parser.parse();
	}
	
	protected abstract String getRegex();
	
	public abstract String toString();
}
