package regex.parsing;

public class Terminal implements Component {
	private String s;
	
	public Terminal(String s) {
		this.s = s;
	}

	@Override
	public boolean isOnlyTerminals() {
		return true;
	}

	@Override
	public String getNonTerminalsInString() {
		return null;
	}
	
	public String fillIn() {
		return s;
	}
	
	public String toString() {
		return s;
	}
}
