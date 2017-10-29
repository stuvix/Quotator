package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import parsing.AbstractParser;

public class MainFrame extends JFrame{
	private JLabel authorLabel;
	private JLabel titleLabel;
	private JLabel dateLabel;
	private JLabel publisherLabel;
	private JLabel placeLabel;
	
	private JTextField authorField;
	private JTextField titleField;
	private JTextField dateField;
	private JTextField publisherField;
	private JTextField placeField;
	
	private JEditorPane resultPane;
	
	private JComboBox<AbstractParser> ddMenu;
	
	private JButton doIt;
	
	private static MainFrame frame;
	
	private MainFrame() {
		setTitle("Quotator");
	    setSize(1000, 600);
	    setResizable(false);
	    setLocation(50, 50);
	    setVisible(true);
		initComponents();
		repaint();
	}
	
	
	
	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel pane = new JPanel(new GridBagLayout());
		this.add(pane);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 20;
		c.ipady = 20;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		initStyleList();
		c.gridwidth = 2;
		ddMenu.addActionListener((e) -> listSelectionAction(e));
		pane.add(ddMenu, c);
		c.gridwidth = 1;
		
		doIt = new JButton("Do It");
		doIt.addActionListener((e) -> doItAction(e));
		c.gridx = 2;
		pane.add(doIt, c);
		c.gridx = 0;
		
		authorLabel = new JLabel("Author");
		c.gridy++;
		pane.add(authorLabel, c);
		
		authorField = new JTextField(40);
		authorField.addActionListener((e) -> enterOnPushAction(e));
		c.gridx++;
		pane.add(authorField, c);
		
		titleLabel = new JLabel("Title");
		c.gridx--;
		c.gridy++;
		pane.add(titleLabel, c);
		
		titleField = new JTextField(40);
		titleField.addActionListener((e) -> enterOnPushAction(e));
		c.gridx++;
		pane.add(titleField, c);
		
		dateLabel = new JLabel("Date");
		c.gridx--;
		c.gridy++;
		pane.add(dateLabel, c);
		
		dateField = new JTextField(20);
		dateField.addActionListener((e) -> enterOnPushAction(e));
		c.gridx++;
		pane.add(dateField, c);
		
		publisherLabel = new JLabel("Publisher");
		c.gridx--;
		c.gridy++;
		pane.add(publisherLabel, c);
		
		publisherField = new JTextField(40);
		publisherField.addActionListener((e) -> enterOnPushAction(e));
		c.gridx++;
		pane.add(publisherField, c);
		
		placeLabel = new JLabel("Place");
		c.gridx--;
		c.gridy++;
		pane.add(placeLabel, c);
		
		placeField = new JTextField(40);
		placeField.addActionListener((e) -> enterOnPushAction(e));
		c.gridx++;
		pane.add(placeField, c);
		
		resultPane = new JEditorPane();
		resultPane.setContentType("text/html");
		resultPane.setEditable(false);
		c.gridx--;
		c.gridy++;
		c.gridwidth = 2;
		pane.add(resultPane, c);
		
		//pack();
	}
	
	/**
	 * initializes the styles list
	 */
	private void initStyleList() {
		ddMenu = new JComboBox<AbstractParser>(AbstractParser.getAllParsers());
		ddMenu.setEditable(false);	
		ddMenu.setEnabled(true);
		ddMenu.setSelectedIndex(0);
		ddMenu.setVisible(true);
	}
	
	private void doItAction(ActionEvent e) {
		try {
			this.resultPane.setText(((AbstractParser) (ddMenu.getSelectedItem())).parse());
			/*Parser parser = new Parser("A, F((, F A)*, and F A)?. D. <i>T</i>. L: P."); //
			SanitizedInputInterface sip = new SanitizedInputInterface();
			this.resultPane.setText(parser.parse());*/
		}
		catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * when a style is selected, the text fields that are not used by this style will be disabled, all others enabled.
	 * @param e
	 */
	private void listSelectionAction(ActionEvent e) {
		AbstractParser current = (AbstractParser) ddMenu.getSelectedItem();
		this.authorField.setEnabled(current.canUseAuthor());
		this.titleField.setEnabled(current.canUseTitle());
		this.dateField.setEnabled(current.canUseDate());
		this.placeField.setEnabled(current.canUseLocation());
		this.publisherField.setEnabled(current.canUsePublisher());
	}
	
	/**
	 * gets used if enter is pressed in one of the textfields
	 * @param e
	 */
	private void enterOnPushAction(ActionEvent e) {
		this.doItAction(e);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame = new MainFrame();
            }
        });
	}
	
	public String getAuthor() {
		return this.authorField.getText();
	}
	
	public String getTitleField() {
		return this.titleField.getText();
	}
	
	public String getDate() {
		return this.dateField.getText();
	}
	
	public String getPublisher() {
		return this.publisherField.getText();
	}
	
	public String getPlace() {
		return this.placeField.getText();
	}
	
	public static MainFrame getFrame() {
		return frame;
	}
}
