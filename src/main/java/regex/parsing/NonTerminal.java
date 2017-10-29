package regex.parsing;

public class NonTerminal implements Component {
	private String s;

	public NonTerminal(String s) {
		this.s = s;
	}
	
	@Override
	public boolean isOnlyTerminals() {
		return false;
	}

	@Override
	public String getNonTerminalsInString() {
		return s;
	}

	@Override
	public String fillIn() {
		return NonTerminalInterface.getInstance().fillIn(s);
	}
	
	public String toString() {
		return s;
	}

}
