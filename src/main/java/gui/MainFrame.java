package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import regex.parsing.NonTerminalEnum;

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
	
	private HashMap<NonTerminalEnum, JLabel> labelList = new HashMap<>();
	private HashMap<NonTerminalEnum, JTextField> textFieldList = new HashMap<>();
	
	private JEditorPane resultPane;
	
	private JComboBox<AbstractParser> ddMenu;
	
	private JButton doIt;
	private boolean doItEnabled = true;
	
	private static MainFrame frame;
	
	private MainFrame() {
		setTitle("Quotator");
	    setSize(1000, 600);
	    setResizable(true);
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
		c.ipadx = 20;
		c.ipady = 20;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 1;
		//initialize the lists with the potential fields
		for (NonTerminalEnum nte: NonTerminalEnum.values()) {
			if (nte == NonTerminalEnum.FIRST_NAME) {
				continue; //this one does not get a box, its box is shared with LAST_NAME
			}
			labelList.put(nte, new JLabel(nte.getName()));
			pane.add(labelList.get(nte), c);
			
			c.gridx++;
			
			textFieldList.put(nte, new JTextField(40));
			textFieldList.get(nte).addActionListener((e) -> enterOnPushAction(e));
			pane.add(textFieldList.get(nte), c);
			
			c.gridy++;
			c.gridx--;
		} 
		
		c.gridwidth = 2;
		this.resultPane = new JEditorPane();
		resultPane.setContentType("text/html");
		resultPane.setEditable(false);
		pane.add(resultPane, c);
		c.gridwidth = 1;
		
		c.gridx = 0;
		c.gridy = 0;
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
		pack();
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
		listSelectionAction(null);
	}
	
	private void doItAction(ActionEvent e) {
		if (!this.doItEnabled) {
			return; //prevent the button from being pressed while another parser still runs
		}
		this.doItEnabled = false;
		
		try {
			this.resultPane.setText(((AbstractParser) (ddMenu.getSelectedItem())).parse());
			pack();
			repaint();
		}
		catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}
		this.doItEnabled = true;
	}
	
	/**
	 * when a style is selected, the text fields that are not used by this style will be disabled, all others enabled.
	 * @param e not used
	 */
	private void listSelectionAction(ActionEvent e) {
		AbstractParser current = (AbstractParser) ddMenu.getSelectedItem();
		for (NonTerminalEnum nte: NonTerminalEnum.values()) {
			if (nte == NonTerminalEnum.FIRST_NAME) {
				continue; //i hate it. i just hate it.
			}
			labelList.get(nte).setVisible(current.canUse(nte)); //here nullpointerexception
			textFieldList.get(nte).setVisible(current.canUse(nte));
		}
		pack();
		repaint();
		//TODO test this
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
	
	public String getTextFieldByIdentifier(NonTerminalEnum n) {
		return this.textFieldList.get(n).getText();
	} 
	
	public static MainFrame getFrame() {
		return frame;
	}
	
	public AbstractParser getParser() {
		return (AbstractParser) ddMenu.getSelectedItem();
	}
}
