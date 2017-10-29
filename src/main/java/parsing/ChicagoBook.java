package parsing;

public class ChicagoBook extends AbstractParser {
	protected final String regex = "A, F((, F A)*, and F A)?. D. <i>T</i>. L: P.";

	@Override
	public String toString() {
		return "Chicago - Book";
	}

	@Override
	protected String getRegex() {
		return this.regex;
	}
}
