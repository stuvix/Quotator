package parsing;

import java.util.ArrayList;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.reflections.Reflections;

import gui.SanitizedInputInterface;
import regex.parsing.NonTerminalEnum;
import regex.parsing.Parser;

public abstract class AbstractParser {	
	protected String regex;
	
	private static ArrayList<AbstractParser> parserList; 
	
	private static ServiceLoader<AbstractParser> parserLoader = ServiceLoader.load(AbstractParser.class);
	
	public AbstractParser() {
		//empty
	}
	
	public static AbstractParser[] getAllParsers() {
		if (parserList == null) {
			parserList = new ArrayList<>();
			
			Reflections reflections = new Reflections("parsing");    
			Set<Class<? extends AbstractParser>> classes = reflections.getSubTypesOf(parsing.AbstractParser.class);
			for (Class<? extends AbstractParser> c: classes) {
				try {
					parserList.add(c.newInstance());
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
		
		//if the parser runs longer than 3 seconds, this timer task will end it and return an error message
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				parser.hasRunForTooLong = true;
			}
		}, 1000L);
		
		String parsed;
		try {
			parsed = parser.parse();
			timer.cancel();
		}
		catch (IllegalArgumentException e) {
			parsed = "Error encountered while parsing.";
		}
				
		return parsed; 
	}
	
	protected abstract String getRegex();
	
	public abstract String toString();
}
