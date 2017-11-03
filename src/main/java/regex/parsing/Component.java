package regex.parsing;

public interface Component {
	
	
	/**
	 * returns whether this component consists only of terminal symbols.
	 * @return true if only terminal symbols, false otherwise
	 */
	public boolean isOnlyTerminals();
	
	/**
	 * gives the non-terminal symbols of this component in a String, just concatenated with no other symbols between.
	 * @return a string with all non-terminal symbols, returns null if this is only terminals
	 */
	public String getNonTerminalsInString();
	
	/**
	 * returns this component as a String with the non-terminals filled in. take care in which order these are called,
	 * as result is order sensitive.
	 * @return a finished String representation of this component.
	 */
	public String fillIn();
	
}
