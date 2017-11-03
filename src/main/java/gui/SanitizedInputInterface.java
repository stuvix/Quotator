package gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import regex.parsing.NonTerminalEnum;

public class SanitizedInputInterface {
	private MainFrame gui;
	
	private HashMap<NonTerminalEnum, LinkedList<String>> inputMap;
	private HashMap<NonTerminalEnum, Iterator<String>> itMap;
	
	/**
	 * creates a new Sanitized input Interface with all the lists reset.
	 * The Lists are filled on creation of this instance and can't be altered later.
	 * For new lists, create new instance.
	 */
	public SanitizedInputInterface() {
		gui = MainFrame.getFrame();
		inputMap = new HashMap<>();
		itMap = new HashMap<>();
		
		String input;
		for (NonTerminalEnum nte: NonTerminalEnum.values()) {
			inputMap.put(nte, new LinkedList<>());
			if (nte == NonTerminalEnum.FIRST_NAME || nte == NonTerminalEnum.LAST_NAME) {
				continue; //added later; 
			}
			input = gui.getTextFieldByIdentifier(nte);
			if (!input.equals("")) {
				inputMap.get(nte).add(input);
			} //else leave list empty
			itMap.put(nte, inputMap.get(nte).iterator());
		}
		addAuthor();
		
	}
	
	/**
	 * hack to add authors to this mess, since they behave differently (2 NonTerminals, but only one text field...)
	 */
	private void addAuthor() {
		String input = gui.getTextFieldByIdentifier(NonTerminalEnum.LAST_NAME);
		LinkedList<String> lnl = this.inputMap.get(NonTerminalEnum.LAST_NAME);
		LinkedList<String> fnl = this.inputMap.get(NonTerminalEnum.FIRST_NAME);
		String[] parsed;
		for (String name: input.split(", ")) {
			parsed = this.parseAuthor(name);
			lnl.add(parsed[1]);
			fnl.add(parsed[0]);
		}
		itMap.put(NonTerminalEnum.LAST_NAME, inputMap.get(NonTerminalEnum.LAST_NAME).iterator());
		itMap.put(NonTerminalEnum.FIRST_NAME, inputMap.get(NonTerminalEnum.FIRST_NAME).iterator());
	}
	
	/**
	 * takes an author from input and separates first and last name into an array
	 * @param author a plain text author
	 * @return an array with two entries, 0: first name(s), 1: last name
	 */
	private String[] parseAuthor(String author) {
		String[] split = author.split(" ");
		String[] result = new String[2];
		result[0] = "";
		result[0] += split[0]; //if this does not exist, fuck the user
		for (int i = 1; i < split.length - 1; i++) {
			result[0] += " " + split[i];
		}
		result[1] = split[split.length - 1];
		return result;
	}
	
	public int getNumberOfInput(NonTerminalEnum nte) {
		return inputMap.get(nte).size();
	}
	
	/**
	 * returns the next user input of given type
	 * @param nte a use input type
	 * @return throws exception if there is no next element, else returns next element
	 */
	public String getNextOfType(NonTerminalEnum nte) {
		return itMap.get(nte).next();
	}
	
	public boolean containsNTE(NonTerminalEnum nte) {
		return this.gui.getParser().canUse(nte);
	}

}
