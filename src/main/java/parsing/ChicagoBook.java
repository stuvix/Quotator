package parsing;

public class ChicagoBook extends AbstractParser {

	public boolean canUseAuthor() {
		return true;
	}
	
	public boolean canUseTitle() {
		return true;
	}
	
	public boolean canUsePublisher() {
		return true;
	}
	
	public boolean canUsePlace() {
		return true;
	}
	
	public boolean canUseDate() {
		return true;
	}
	
	public String getName() {
		return "Chicago - Book";
	}
	
	public String toString() {
		return getName();
	}

	@Override
	public String parse() {
		// TODO do this properly
		String format = "";
		String[] authors = super.getAuthor();
		format += authors[0].split(" ")[1] + ", " + authors[0].split(" ")[0]; //only works for 2 piece names
		if (authors.length > 1) {
			format += ", and" + authors[1];
		}
		format += ". ";
		format += super.getDate() + ". ";
		format += "<i>" + super.getTitle() + ".</i> ";
		format += super.getPlace() + ": " + super.getPublisher() + ".";
		return format;
	}
}
