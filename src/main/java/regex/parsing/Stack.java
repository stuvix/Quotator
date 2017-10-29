package regex.parsing;

import java.util.ArrayList;
import java.util.Iterator;

public class Stack implements Component, Iterable<Component> {
	private ArrayList<Component> stack = new ArrayList<>();
	
	private Stack parent;
	
	/**
	 * creates a new empty stack with no parent
	 */
	protected Stack() {
		//empty
	}
	
	/**
	 * creates a new Stack with the same elements as the passed stack and a copy of the parent stack
	 * @param s
	 */
	protected Stack(Stack s) {
		for (Component c : s) {
			this.stack.add(c);
		}
		this.setParent(s.getParent());
	}

	@Override
	public boolean isOnlyTerminals() {
		if (this.stack.isEmpty()) {
			return true;
		}
		for (Component c: this) {
			if (!c.isOnlyTerminals()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String getNonTerminalsInString() {
		String s = "";
		for (Component c: this) {
			if (!c.isOnlyTerminals()) {
				s += c.getNonTerminalsInString();
			}
		}
		return s.equals("") ? null : s; //if s is empty, return null instead as per interface documentation
	}

	@Override
	public String fillIn() {
		String s = "";
		for (Component c: this) {
			s += c.fillIn();
		}
		return s;
	}
	
	protected void push(Component c) {
		this.stack.add(c);
	}
	
	protected Component pop() {
		if (this.stack.size() == 0) {
			return null;
		}
		return this.stack.remove(this.stack.size() - 1);
	}
	
	/**
	 * iterator over stack from bottom to top
	 */
	public Iterator<Component> iterator() {
		return this.stack.iterator();
	}
	
	public Stack getParent() {
		return this.parent;
	}
	
	/**
	 * sets the return jump parent, which is a copy of the given stack, so that other children of the given stack cannot 
	 * alter it
	 * @param stack
	 */
	protected void setParent(Stack stack) {
		if (stack == null) {
			this.parent = null;
		}
		else {
			this.parent = new Stack(stack);
		}
	}
	
	public String toString() {
		String s = "[";
		for (Component c: this) {
			s += c.toString();
		}
		return s + "]";
	}

}
