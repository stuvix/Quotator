package parsing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JOptionPane;


/**
 * Loads Parsers at program start from file in directory.
 * 
 * @author Michel Max
 *
 */
public class ParserLoader {

	private final static String filename = "src/main/resources/parsers.txt";
	
	private final static String COMMENT = "#";
	private final static String NAME = "name:";
	private final static String REGEX = "regex:";
	
	public Collection<AbstractParser> loadParsers() {
		int lineCounter = 0;
		LinkedList<AbstractParser> list = new LinkedList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line;
			String name = "undefined name";
			
			while ((line = in.readLine()) != null) {
				if (line.startsWith(COMMENT)) {
					//do nothing i guess.
				}
				else if (line.startsWith(NAME)) {
					name = line.substring(NAME.length()).trim();
				}
				else if (line.startsWith(REGEX)) {
					list.add(new ParserFromFile(name, line.substring(REGEX.length()).trim()));
				}
				lineCounter++;
			}
			in.close();
			return list;
			
		} catch (FileNotFoundException e) {
			//display message and load nothing
			JOptionPane.showMessageDialog(null, "No File found in src\\main\\resources\\parsers.txt");
			return new LinkedList<>();
		} catch (IOException e) {
			//display error and return parsers created so far
			JOptionPane.showMessageDialog(null, "Error while reading File in line " + lineCounter);
			return list;
		}
	}
}
