package regex.parsing;

public class Parser {
	private static final String Q_MARK = "?";
	private static final String STAR = "*";
	private static final String PLUS = "+";
	protected static final String ESCAPE = "$";
	
	
	private String regex;
	private NonTerminalInterface nti;
	
	private String result;
	
	public Parser(String regex) {
		this.nti = new NonTerminalInterface(); //get the input from the gui for this parsing experience 
		this.regex = regex;
	}
	
	public String parse() {
		if (parseRec(0, new Stack(), this.regex)) {
			return result;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * builds a formatted string from regex and user input. 
	 * @param position position of the pointer to the regex symbols
	 * @param stack the current Stack on which to build
	 * @param regex the regular expression
	 * @return true if this line worked, false otherwise
	 */
	private boolean parseRec(int position, Stack stack, String regex) {
		//System.out.println("BP1: " + stack.toString() + " " + position); //TODO
		/*Component temp = stack.pop(); //check he condition without the last element on the stack, as it could be removed by an operator
		if (!stack.isOnlyTerminals() && !nti.checkAllNumConstraints(stack.getNonTerminalsInString())) {
			//System.out.println("BP2: " + stack.toString());//TODO
			return false; //in this case, there are more of one non-terminal than arguments provided
		}
		if (temp != null) {
			stack.push(temp);
		}	*/ //TODO this thing was removed and may lead to endless loops, but not if always not is selected first
		if (position >= regex.length()) {
			//we are finished with this run
			//check for more correctness and return 
			if (nti.checkAllExactConstraints(stack.getNonTerminalsInString())) {
				this.result = stack.fillIn();
				return true;
			}
			else {
				//System.out.println("Fail"); //TODO
				return false;
			}
		}
				
		String currentSymbol = regex.substring(position, position + 1);
		Stack recStack;
		//Stack curStack = new Stack(stack);
		//curStack.setParent(curStack.getParent());
		if (isControlSymbol(currentSymbol)) {
			switch (currentSymbol) {
			case Q_MARK:
				recStack = new Stack(stack);
				recStack.pop();
				//first try without ?
				if (parseRec(position + 1, recStack, regex)) {
					return true;
				}
				//then try with ?
				if (parseRec(position + 1, new Stack(stack), regex)) {
					return true;
				}
				return false;
			case STAR:
				recStack = new Stack(stack);
				stack.pop();
				if (parseRec(position + 1, new Stack(stack), regex)) {
					return true;
				}
				if (parseRec(position, recStack, changeNthCharacterTo(regex, position, "+"))) {
					return true; //after eliminating the "case 0" for the *, it behaves just like a +.
				}
				return false;
			case PLUS:
				recStack = new Stack(stack);
				if (parseRec(position + 1, new Stack(stack), regex)) {
					return true;
				}
				Component c = recStack.pop(); 
				if (c == null) {
					//System.out.println("----" + recStack.getParent().toString()); //TODO
				}
				recStack.push(c);		//here null is put onto the stack
				recStack.push(c);
				if (parseRec(position, recStack, regex)) {
					return true;
				}
				return false;
			}
			assert false;
			return false;
		}
		
		if (currentSymbol.equals("(")) {
			recStack = new Stack();
			recStack.setParent(stack);
			//System.out.println("BP3: " + recStack.getParent().toString()); //TODO
			return parseRec(position + 1, recStack, regex);
		}
		
		if (currentSymbol.equals(")")) {
			//System.out.println(stack.toString() + "||" + stack.getParent().toString()); //TODO
			Stack temp1 = stack.getParent();
			temp1.push(stack);
			return parseRec(position + 1, temp1, regex);
		}
		
		if (NonTerminalEnum.isNonTerminal(currentSymbol)) {
			stack.push(new NonTerminal(currentSymbol));
			return parseRec(position + 1, stack, regex);
		}
		
		else {
			if (currentSymbol.equals(ESCAPE)) {
				//escape the next symbol and add it as plain text
				stack.push(new Terminal(regex.substring(position + 1, position + 2)));
				return parseRec(position + 2, stack, regex);
			}
			else {
				stack.push(new Terminal(currentSymbol));
				return parseRec(position + 1, stack, regex);
			}
		}
	}
	
	/**
	 * "Hello", 1, "3" gives H3llo
	 * @param s
	 * @param position
	 * @param to
	 * @return
	 */
	public String changeNthCharacterTo(String s, int position, String to) {
		if (position >= s.length()) {
			return s;
		}
		return s.substring(0, position) + to + s.substring(position + 1);
	}
	
	/**
	 * determines whether this is a control symbol. () do not count, they are special
	 * @param s
	 * @return
	 */
	private boolean isControlSymbol(String s) {
		switch (s) {
		case Q_MARK:
		case STAR:
		case PLUS: return true;
		}
		return false;
	}
}
