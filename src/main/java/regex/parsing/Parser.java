package regex.parsing;

import java.util.Iterator;
import java.util.LinkedList;

public class Parser {
	private static final String Q_MARK = "?";
	private static final String STAR = "*";
	private static final String PLUS = "+";
	protected static final String ESCAPE = "$";
	
	public boolean hasRunForTooLong = false; //this is set to true by a timer after some time.
	
	private String regex;
	private NonTerminalInterface nti;
	
	private String result;
	
	private int testCounter = 0; //TODO remove after testing
	
	public Parser(String regex) {
		this.nti = new NonTerminalInterface(); //get the input from the gui for this parsing experience 
		this.regex = regex;
	}
	
	public String parse() {
		/*if (parseRec(0, new Stack(), this.regex)) {
			return result;
		}
		else {
			throw new IllegalArgumentException();
		}*/
		return this.parseBFS(regex); //TODO choose parser
	}
	
	/**
	 * builds a formatted string from regex and user input. 
	 * @param position position of the pointer to the regex symbols
	 * @param stack the current Stack on which to build
	 * @param regex the regular expression
	 * @return true if this line worked, false otherwise
	 */
	private boolean parseRec(int position, Stack stack, String regex) {
		//do a quick check to see if we are running too long and abort because infinite loop.
		if (this.hasRunForTooLong) {
			return false;
		}
		//System.out.println("BP1: " + stack.toString() + " " + position); //TODO
		/*Component temp = stack.pop(); //check he condition without the last element on the stack, as it could be removed by an operator
		if (!stack.isOnlyTerminals() && !nti.checkAllNumConstraints(stack.getNonTerminalsInString())) {
			//System.out.println("BP2: " + stack.toString());//TODO
			return false; //in this case, there are more of one non-terminal than arguments provided
		}
		if (temp != null) {
			stack.push(temp);
		}	*/ //TODO this thing was removed and may lead to endless loops, but not if option without recursion is selected first
		if (position >= regex.length()) {
			//we are finished with this run
			
			//check for more correctness and return 
			if (nti.checkAllExactConstraints(stack.getNonTerminalsInString())) {
				this.result = stack.fillIn();
				return true;
			}
			else {
				System.out.println("Fail: " + stack.toString()); 
				testCounter++;
				if (testCounter > 10) {
					this.hasRunForTooLong = true;
				}
				//TODO remove these lines after testing
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
				//first try without the element that is starred
				stack.pop();
				if (parseRec(position + 1, new Stack(stack), regex)) {
					return true;
				}
				//then set of a recursive call of the same place with a + instead of *
				if (parseRec(position, recStack, changeNthCharacterTo(regex, position, "+"))) {
					return true; //after eliminating the "case 0" for the *, it behaves just like a +.
				}
				return false;
			case PLUS:
				recStack = new Stack(stack);
				//first try with the element present once
				if (parseRec(position + 1, new Stack(stack), regex)) {
					return true;
				}
				//then push the top element another time and do recursion
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
	 * another approach to parsing with BFS strategy
	 * @param regex the regex
	 * @return the parsed String or an error message to display
	 */
	public String parseBFS(String regex) {
		LinkedList<Stack> mainStackList = new LinkedList<>();
		LinkedList<Integer> mainPointerList = new LinkedList<>();
		LinkedList<Stack> helperStackList = new LinkedList<>();
		LinkedList<Integer> helperPointerList = new LinkedList<>();
		LinkedList<String> mainRegexList = new LinkedList<>();
		LinkedList<String> helperRegexList = new LinkedList<>();
		
		Stack currentStack;
		int currentPointer;
		String currentSymbol;
		String currentRegex;
		
		Stack s; //helper variables
		Component c;
		
		Iterator<Stack> stackIt;
		Iterator<Integer> pointerIt;
		Iterator<String> regexIt;
		
		mainStackList.add(new Stack());
		mainPointerList.add(0);
		mainRegexList.add(regex);
		
		while (!this.hasRunForTooLong) {
			
			stackIt = mainStackList.iterator(); //these should always have the same length
			pointerIt = mainPointerList.iterator();
			regexIt = mainRegexList.iterator();
			
			while (stackIt.hasNext()) {
				currentStack = stackIt.next();
				currentPointer = pointerIt.next();
				currentRegex = regexIt.next();
				currentSymbol = currentRegex.substring(currentPointer, currentPointer + 1);
				
				switch (currentSymbol) {
				case (Q_MARK):
					//add with element
					helperStackList.add(new Stack(currentStack));
					helperPointerList.add(new Integer(currentPointer) + 1);
					helperRegexList.add(regex);
					
					//add without element
					currentStack.pop();
					helperStackList.add(new Stack(currentStack));
					helperPointerList.add(new Integer(currentPointer) + 1);
					helperRegexList.add(regex);
					break;
				
				case (STAR):
					//add with element and a PLUS instead of STAR
					helperStackList.add(new Stack(currentStack));
					helperPointerList.add(new Integer(currentPointer));
					helperRegexList.add(this.changeNthCharacterTo(currentRegex, currentPointer, PLUS));
					
					//add one without the element
					currentStack.pop();
					helperStackList.add(new Stack(currentStack));
					helperPointerList.add(new Integer(currentPointer) + 1);
					helperRegexList.add(currentRegex);
					break;
					
				case(PLUS):
					//leave element be and advance
					helperStackList.add(new Stack(currentStack));
					helperPointerList.add(new Integer(currentPointer) + 1);
					helperRegexList.add(regex);
					
					//add last element another time
					c = currentStack.pop();
					currentStack.push(c);
					currentStack.push(c);
					helperStackList.add(new Stack(currentStack));
					helperPointerList.add(new Integer(currentPointer));
					helperRegexList.add(regex);
					break;
					
				case ("("):
					//create new empty stack and set current as parent of the new stack
					s = new Stack();
					s.setParent(currentStack);
					helperStackList.add(s);
					helperPointerList.add(new Integer(currentPointer) + 1);
					helperRegexList.add(regex);
					break;
					
				case (")"):
					//set the parent of current as the new stack and add the current to the parent
					s = currentStack.getParent();
					s.push(currentStack);
					helperStackList.add(s);
					helperPointerList.add(new Integer(currentPointer) + 1);
					helperRegexList.add(regex);
					break;
					
				case (ESCAPE):
					//push the next symbol and advance two
					currentStack.push(new Terminal(currentRegex.substring(currentPointer + 1, currentPointer + 2)));
					helperStackList.add(new Stack(currentStack));
					helperPointerList.add(new Integer(currentPointer) + 2);
					helperRegexList.add(regex);
					break;
					
				default:
					if (NonTerminalEnum.isNonTerminal(currentSymbol)) {
						currentStack.push(new NonTerminal(currentSymbol));
						helperStackList.add(new Stack(currentStack));
						helperPointerList.add(new Integer(currentPointer) + 1);
						helperRegexList.add(regex);
					}
					else {
						currentStack.push(new Terminal(currentSymbol));
						helperStackList.add(new Stack(currentStack));
						helperPointerList.add(new Integer(currentPointer) + 1);
						helperRegexList.add(regex);
					}
				}								
			}
			
			//the helper lists become the main lists now
			mainStackList.clear();
			mainStackList.addAll(helperStackList);
			mainPointerList.clear();
			mainPointerList.addAll(helperPointerList);
			mainRegexList.clear();
			mainRegexList.addAll(helperRegexList);
			
			//check if any of the new stacks have arrived
			stackIt = mainStackList.iterator(); //these should always have the same length
			pointerIt = mainPointerList.iterator();
			regexIt = mainRegexList.iterator();
			
			while (pointerIt.hasNext()) {
				if (pointerIt.next() >= regexIt.next().length()) {
					//this one has arrived
					currentStack = stackIt.next();
					if (!currentStack.isOnlyTerminals() && nti.checkAllExactConstraints(currentStack.getNonTerminalsInString())) {
						//this is it
						return currentStack.fillIn();
					}
					else {
						stackIt.remove();
						pointerIt.remove();
						regexIt.remove();
					}
				}
			}
			
			if (mainStackList.isEmpty()) {
				return "No Solution found, maybe check you input?";
			}
			
			/*for (Stack ps: mainStackList) {
				System.out.print(ps.toString());
			}*/
			//System.out.println(mainStackList.size()); //TODO
			
			
			helperStackList.clear();
			helperPointerList.clear();
			helperRegexList.clear();
			assert mainPointerList.size() == mainRegexList.size();
			assert mainPointerList.size() == mainStackList.size();
		}
		return "An Error occured";
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
