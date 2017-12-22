package parsing;

public class ParserFromFile extends AbstractParser{
	private String regex;
	private String name;
	
	/**
	 * create new Parser
	 * @param name name of Parser (is displayed in drop down list)
	 * @param regex defines parser behavior
	 */
	public ParserFromFile(String name, String regex) {
		this.name = name;
		this.regex = regex;
	}

	@Override
	protected String getRegex() {
		return regex;
	}

	@Override
	public String toString() {
		return name;
	}

}
