package regex.parsing;

import gui.SanitizedInputInterface;

/**
 * this class is NOT suitable for multithreading
 * @author Michel Max
 *
 */
public class NonTerminalInterface {
	
	
	private SanitizedInputInterface gui;
	
	private static NonTerminalInterface instance;
	
	/**
	 * checks if all non-terminals are less or equal often present as provided by user
	 * @param s
	 * @return false if at least one non-terminal is present more often than provided by user
	 */
	protected boolean checkAllNumConstraints(String nonTerminals) {
		for (char c: nonTerminals.toCharArray()) {
			if (gui.getNumberOfInput(NonTerminalEnum.getNTE(Character.toString(c))) < containsChar(nonTerminals, c)) {
				System.out.println(nonTerminals); //TODO remove after testing
				return false; //if the user input less of this nte than there are in the NonTerminals string, get here
			}
		}
		return true;
	}
	
	/**
	 * this checks if all non-terminals are provided exactly as often as provided by user
	 * @param s
	 * @return
	 */
	protected boolean checkAllExactConstraints(String nonTerminals) {
		for (NonTerminalEnum nte: NonTerminalEnum.values()) {
			if (gui.containsNTE(nte) && gui.getNumberOfInput(nte) != containsChar(nonTerminals, nte.getKey().toCharArray()[0])) {
				return false; //if the user didn't input exactly as many of this nte than there are in the NonTerminals string, get here
			}
		}
		return true;
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
	 * @param nte
	 * @return the next user provided variable for this non-terminal, or null if this is not a non-terminal
	 */
	protected String fillIn(NonTerminalEnum nte) {
		return this.gui.getNextOfType(nte);
	}
	
	/**
	 * gets the newest instance of this class
	 * @return
	 */
	protected static NonTerminalInterface getInstance() {
		return instance;
	}
}
