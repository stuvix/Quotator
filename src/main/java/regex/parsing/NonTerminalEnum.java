package regex.parsing;

public enum NonTerminalEnum {
	FIRST_NAME("F", "First Name of Author"), //this one is not drawn
	LAST_NAME("A", "Name of Author"),
	TITLE("T", "Title"),
	DATE("D", "Data of publication"),
	PUBLISHER("P", "Publisher"),
	LOCATION("L", "Location of Publication"),
	EDITOR("E", "Editor"),
	TRANSLATOR("R", "Translator"),
	PAGE_NUMBER("N", "Number of Page"),
	CHAPTER("C", "Chapter"),
	DATE_OF_ACCESS("O", "Date of Access"),
	ARTICLE_TITLE("Z", "Title of Article"),
	JOURNAL_TITLE("J", "Title of Journal"),
	ISSUE_INFORMATION("I", "Issue Information"),
	DEPARTMENTS("M", "Departments"),
	URL("U", "URL");
	
	
	private String key;
	private String name;
	/**
	 * 
	 * @param s the unique regex identifier of this entry
	 * @param name the display name for this entry
	 */
	private NonTerminalEnum(String s, String name) {
		key = s;
		this.name = name;
	}
	
	/**
	 * checks whether the String s (one letter) is a non-terminal
	 * @param s a single letter string
	 * @return true if non-terminal, false otherwise
	 */
	public static boolean isNonTerminal(String s) {
		for (NonTerminalEnum n : NonTerminalEnum.values()) {
			if ((n.key).equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * takes a string and transforms it to a nte
	 * @param s
	 * @return null if no corresponding nte
	 */
	public static NonTerminalEnum getNTE(String s) {
		for (NonTerminalEnum n : NonTerminalEnum.values()) {
			if ((n.key).equals(s)) {
				return n;
			}
		}
		return null;
	}
	
	/**
	 * returns whether this is in the given string and not escaped.
	 * @param regex
	 * @return
	 */
	public boolean isIn(String regex) {
		if (regex == null) {
			return false;
		}
		int i = 0;
		String current;
		while (i < regex.length()) {
			current = regex.substring(i, i+1);
			if (current.equals(this.key)) {
				return true;
			}
			if (current.equals(Parser.ESCAPE)) {
				i++;
			}
			i++;
		}
		return false;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getName() {
		return this.name;
	}
}

