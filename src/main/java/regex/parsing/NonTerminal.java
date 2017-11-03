package regex.parsing;

public class NonTerminal implements Component {
	private NonTerminalEnum nte;

	public NonTerminal(String s) {
		this.nte = NonTerminalEnum.getNTE(s);
		if (this.nte == null) {
			throw new IllegalArgumentException("This is not a Non Terminal");
		}
	}
	
	@Override
	public boolean isOnlyTerminals() {
		return false;
	}

	@Override
	public String getNonTerminalsInString() {
		return nte.getKey();
	}

	@Override
	public String fillIn() {
		return NonTerminalInterface.getInstance().fillIn(nte);
	}
	
	public String toString() {
		return nte.getKey();
	}

}
