package parsing;

import java.util.ArrayList;

import gui.MainFrame;

public abstract class AbstractParser {
	
	private static ArrayList<AbstractParser> parserList; 
	
	public static AbstractParser[] getAllParsers() {
		if (parserList == null) {
			parserList = new ArrayList<>();
			parserList.add(new ChicagoBook());
		}
		return parserList.toArray(new AbstractParser[1]);
	}
	
	public boolean canUseAuthor() {
		return false;
	}
	
	public boolean canUseTitle() {
		return false;
	}
	
	public boolean canUsePublisher() {
		return false;
	}
	
	public boolean canUsePlace() {
		return false;
	}
	
	public boolean canUseDate() {
		return false;
	}
	
	/**
	 * set authors for later parsing
	 * @param authors a list of authors separated by commas
	 */
	protected String[] getAuthor() {
		String authors =  MainFrame.getFrame().getAuthor();
		return authors.split(",");
	}
	
	protected String getTitle() {
		return MainFrame.getFrame().getTitleField();
	}
	
	protected String getPublisher() {
		return MainFrame.getFrame().getPublisher();
	}
	
	protected String getPlace() {
		return MainFrame.getFrame().getPlace();
	}
	
	protected String getDate() {
		return MainFrame.getFrame().getDate(); //TODO more complex date parsing
	}
	
	public abstract String parse();
}
