package parsing;

public class TestParser extends AbstractParser {
	private String regex = "F A C T?";

	@Override
	protected String getRegex() {
		return this.regex;
	}

	@Override
	public String toString() {
		return "Test Parser";
	}

}
