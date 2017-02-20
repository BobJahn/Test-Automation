import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8568597077453053962L;
	private JButton addCommandButton = new JButton("Add Generic Command");
	private JButton clearButton = new JButton("Clear Everything");
	private JButton buttonClickButton = new JButton("Add Button Click");
	private JButton textFillButton = new JButton("Add Text Fill");
	private JButton enterButton = new JButton("Add Enter Keystroke");
	private JTextField addCommandField = new JTextField(20);
	private JTextArea commandOutput = new JTextArea(30, 2);

	private JMenuBar mainMenuBar = new JMenuBar();
	private JMenu loadSaveMenu = new JMenu("Load/Save");
	private JMenu buttonClickMenu = new JMenu("Button Click");
	private JMenuItem load = new JMenuItem("Load");
	private JMenuItem save = new JMenuItem("Save");
	private JMenuItem alertBtn = new JMenuItem("Alert");
	private JMenuItem equipmentBtn = new JMenuItem("Equipment");

	public MainWindow() {
		mainMenuBar.add(loadSaveMenu);
		mainMenuBar.add(buttonClickMenu);
		loadSaveMenu.add(load);
		loadSaveMenu.add(save);
		buttonClickMenu.add(alertBtn);
		buttonClickMenu.add(equipmentBtn);
		loadSaveMenu.setForeground(new Color(66, 134, 244));
		buttonClickMenu.setForeground(new Color(66, 134, 244));
		setJMenuBar(mainMenuBar);

		JPanel panel = new JPanel(new GridLayout(2, 1));
		JPanel p1 = new JPanel(new GridLayout(1, 2));
		p1.add(addCommandField);
		p1.add(addCommandButton);

		JPanel p2 = new JPanel(new GridLayout(1, 4));
		p2.add(clearButton);
		p2.add(buttonClickButton);
		p2.add(textFillButton);
		p2.add(enterButton);

		panel.add(p1);
		panel.add(p2);

		AddCommandListener ac1 = new AddCommandListener();
		addCommandButton.addActionListener(ac1);

		ClearListener c1 = new ClearListener();
		clearButton.addActionListener(c1);

		ButtonClickListener bc1 = new ButtonClickListener();
		buttonClickButton.addActionListener(bc1);

		EnterListener e1 = new EnterListener();
		enterButton.addActionListener(e1);

		TextFillListener tf1 = new TextFillListener();
		textFillButton.addActionListener(tf1);

		SaveFileListener sf1 = new SaveFileListener();
		save.addActionListener(sf1);

		LoadFileListener lf1 = new LoadFileListener();
		load.addActionListener(lf1);

		MenuButtonClickListener mbc1 = new MenuButtonClickListener();
		alertBtn.addActionListener(mbc1);
		equipmentBtn.addActionListener(mbc1);

		commandOutput.setBackground(new Color(0, 0, 0));
		commandOutput.setForeground(new Color(27, 226, 93));
		add(panel, BorderLayout.NORTH);
		add(commandOutput);
	}

	class AddCommandListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = addCommandField.getText().toLowerCase();
			commandOutput.append(command + "\n");
		}
	}

	class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			commandOutput.setText("");
			addCommandField.setText("");
		}
	}

	class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = "click " + addCommandField.getText().toLowerCase() + " button";
			commandOutput.append(command + "\n");
		}
	}

	class MenuButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == alertBtn) {
				String command = "click alert button";
				commandOutput.append(command + "\n");
			} else if (e.getSource() == equipmentBtn) {
				String command = "click equipment button";
				commandOutput.append(command + "\n");
			}
		}
	}

	class EnterListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = "enter/return";
			commandOutput.append(command + "\n");
		}
	}

	class TextFillListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = "enter text: " + addCommandField.getText().toLowerCase();
			commandOutput.append(command + "\n");
		}
	}

	class SaveFileListener implements ActionListener {
		private PrintWriter pw1 = null;

		public void actionPerformed(ActionEvent e) {
			String fileName = JOptionPane.showInputDialog(
					"Enter File Name (.txt extension autmotically provided. Scripts of the same name will be overwritten.)");
			try {
				pw1 = new PrintWriter(new File(fileName + ".txt"));
				pw1.write(commandOutput.getText());
				pw1.close();
				commandOutput.append("File '" + fileName + ".txt' written to local directory.");
			} catch (FileNotFoundException e1) {
				commandOutput.append("Something went wrong!");
				e1.printStackTrace();
			} finally {
				if (pw1 != null) {
					pw1.close();
				}
			}
		}
	}

	class LoadFileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String fileName = JOptionPane.showInputDialog("Enter File Name");
			Macro m1 = new Macro();
			commandOutput.setText(m1.readScript(fileName));
		}
	}

	public static void main(String[] args) {
		MainWindow frame = new MainWindow();
		frame.setTitle("'Script' Generator");
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
