package gui;

import java.util.Iterator;
import java.util.LinkedList;

public class SanitizedInputInterface {
	private MainFrame gui;
	
	private LinkedList<String> firstNames;
	private LinkedList<String> lastNames;
	private LinkedList<String> titles;
	private LinkedList<String> dates;
	private LinkedList<String> publishers;
	private LinkedList<String> locations;
	
	private Iterator<String> firstNameIterator;
	private Iterator<String> lastNameIterator;
	private Iterator<String> titlesIterator;
	private Iterator<String> datesIterator;
	private Iterator<String> publishersIterator;
	private Iterator<String> locationsIterator;
	
	/**
	 * creates a new Sanitized input Interface with all the lists reset.
	 * The Lists are filled on creation of this instance and can't be altered later.
	 * For new lists, create new instance.
	 */
	public SanitizedInputInterface() {
		gui = MainFrame.getFrame();
		
		firstNames = new LinkedList<>();
		lastNames = new LinkedList<>();
		this.fillAuthors();
		this.firstNameIterator = this.firstNames.iterator();
		this.lastNameIterator = this.lastNames.iterator();
		titles = new LinkedList<>();
		this.fillTitles();
		this.titlesIterator = this.titles.iterator();
		dates = new LinkedList<>();
		this.fillDates();
		this.datesIterator = this.dates.iterator();
		publishers = new LinkedList<>();
		this.fillPublishers();
		this.publishersIterator = this.publishers.iterator();
		locations = new LinkedList<>();
		this.fillLocations();
		this.locationsIterator = this.locations.iterator();
	}
	
	/**
	 * primitive Author parsing. Accepts only two piece names separated by ", "
	 */
	private void fillAuthors() {
		if (gui.getAuthor().equals("")) {
			return;
		}
		String[] authors = gui.getAuthor().split(", ");
		for (int i = 0; i < authors.length; i++) {
			firstNames.add(authors[i].split(" ")[0]);
			lastNames.add(authors[i].split(" ")[1]);
		}
	}
	
	private void fillTitles() {
		if (gui.getTitleField().equals("")) {
			return;
		}
		else {
			titles.add(gui.getTitleField());
		}
	}
	
	private void fillDates() {
		String s = gui.getDate();
		if (!s.equals("")) {
			this.dates.add(s);
		}
	}
	
	private void fillLocations() {
		String s = gui.getPlace();
		if (!s.equals("")) {
			this.locations.add(s);
		}
	}
	
	private void fillPublishers() {
		String s = gui.getPublisher();
		if (!s.equals("")) {
			this.publishers.add(s);
		}
	}
	
	public int getNumOfAuthors() {
		return this.lastNames.size();
	}
	
	public int getNumOfTitles() {
		return this.titles.size();
	}
	
	public int getNumOfDates() {
		return this.dates.size();
	}
	
	public int getNumOfLocations() {
		return this.locations.size();
	}
	
	public int getNumOfPublishers() {
		return this.publishers.size();
	}
	
	public String getNextFirstName() {
		return this.firstNameIterator.next();
	}
	public String getNextLastName() {
		return this.lastNameIterator.next();
	}
	
	public String getNextTitle() {
		return this.titlesIterator.next();
	}
	
	public String getNextDate() {
		return this.datesIterator.next();
	}
	
	public String getNextLocation() {
		return this.locationsIterator.next();
	}
	
	public String getNextPublisher() {
		return this.publishersIterator.next();
	}	

}
