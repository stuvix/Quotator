package regex.parsing;

import gui.SanitizedInputInterface;

/**
 * this class is NOT suitable for multithreading
 * @author Michel Max
 *
 */
public class NonTerminalInterface {
	public static final String FIRST_NAME = "F";
	public static final String LAST_NAME = "A";
	public static final String TITLE = "T";
	public static final String DATE = "D";
	public static final String PUBLISHER = "P";
	public static final String LOCATION = "L";
	
	private SanitizedInputInterface gui;
	
	private static NonTerminalInterface instance;
	
	/**
	 * checks whether the String s is a non-terminal
	 * @param s a single letter string
	 * @return true if non-terminal, false otherwise
	 */
	protected static boolean isNonTerminal(String s) {
		switch (s) {
		case FIRST_NAME:
		case LAST_NAME:
		case TITLE:
		case DATE:
		case PUBLISHER:
		case LOCATION: return true;
		}
		return false;
	}
	
	/**
	 * checks if all non-terminals are less ore equal often present as provided by user
	 * @param s
	 * @return false if at least one non-terminal is present more often than provided by user
	 */
	protected boolean checkAllNumConstraints(String s) {
		return checkNumFirstName(s) && checkNumLastName(s) && checkNumDate(s) && checkNumTitle(s) 
				&& checkNumPublisher(s) && checkNumLocation(s);
	}
	
	private boolean checkNumFirstName(String s) {
		return containsChar(s, FIRST_NAME.charAt(0)) <= gui.getNumOfAuthors();
	}
	
	private boolean checkNumLastName(String s) {
		return containsChar(s, LAST_NAME.charAt(0)) <= gui.getNumOfAuthors();
	}
	
	private boolean checkNumTitle(String s) {
		return containsChar(s, TITLE.charAt(0)) <= gui.getNumOfTitles();
	}
	
	private boolean checkNumDate(String s) {
		return containsChar(s, DATE.charAt(0)) <= gui.getNumOfDates();
	}
	
	private boolean checkNumPublisher(String s) {
		return containsChar(s, PUBLISHER.charAt(0)) <= gui.getNumOfPublishers();
	}
	
	private boolean checkNumLocation(String s) {
		return containsChar(s, LOCATION.charAt(0)) <= gui.getNumOfLocations();
	}
	
	/**
	 * thism and the following 6 methods, check if all non-terminals are provided exactly as often as provided by user
	 * @param s
	 * @return
	 */
	protected boolean checkAllExactConstraints(String s) {
		return checkExactFirstName(s) && checkExactLastName(s) && checkExactDate(s) && checkExactTitle(s) 
				&& checkExactPublisher(s) && checkExactLocation(s);
	}
	
	private boolean checkExactFirstName(String s) {
		return containsChar(s, FIRST_NAME.charAt(0)) == gui.getNumOfAuthors();
	}
	
	private boolean checkExactLastName(String s) {
		return containsChar(s, LAST_NAME.charAt(0)) == gui.getNumOfAuthors();
	}
	
	private boolean checkExactTitle(String s) {
		return containsChar(s, TITLE.charAt(0)) == gui.getNumOfTitles();
	}
	
	private boolean checkExactDate(String s) {
		return containsChar(s, DATE.charAt(0)) == gui.getNumOfDates();
	}
	
	private boolean checkExactPublisher(String s) {
		return containsChar(s, PUBLISHER.charAt(0)) == gui.getNumOfPublishers();
	}
	
	private boolean checkExactLocation(String s) {
		return containsChar(s, LOCATION.charAt(0)) == gui.getNumOfLocations();
	}
	
	/**
	 * counts how many times c is in s
	 * @param s
	 * @param c
	 * @return
	 */
	private static int containsChar(String s, char c) {
		int counter = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == c) {
				counter += 1;
			}
		}
		return counter;
	}
	
	/**
	 * refreshes the input. needs to be called every time a new input is to be parsed.
	 */
	protected NonTerminalInterface() {
		this.gui = new SanitizedInputInterface();
		instance = this;
	}
	
	/**
	 * fills in this non-terminal with help from the gui. No checks are performed here. if there are no more available, 
	 * this will crash.
	 * @param s
	 * @return the next user provided variable for this non-terminal, or null if this is not a non-terminal
	 */
	protected String fillIn(String s) {
		switch (s) {
		case FIRST_NAME: return gui.getNextFirstName();
		case LAST_NAME: return gui.getNextLastName();
		case TITLE: return gui.getNextTitle();
		case DATE: return gui.getNextDate();
		case PUBLISHER: return gui.getNextPublisher();
		case LOCATION: return gui.getNextLocation();
		}
		return null;
	}
	
	/**
	 * gets the newest instance of this class
	 * @return
	 */
	protected static NonTerminalInterface getInstance() {
		return instance;
	}
}
