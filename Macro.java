import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Macro {

	private Scanner input;
	private String script;
	private int delay = 100; // default delay between commands.

	public Macro() {
		script = "";
	}

	public String readScript(String fileName) {
		try {
			input = new Scanner(new File(fileName));
			// script += "script: " + fileName.substring(0, fileName.length() -
			// 3);
			while (input.hasNext()) {
				script += input.nextLine() + "\n";
			}
			input.close();
			return script;
		} catch (FileNotFoundException e) {
			// Do nothing. Just catch the exception and return error message
			// below.
		} finally {
			if (input != null) {
				input.close();
			}
		}

		return "File Not Found!";
	}

	public String executeScript() {
		if (script == null) {
			return "Script not loaded!";
		} else {
			int lineNumber = 0;
			input = new Scanner(script);
			while (input.hasNext()) {
				String line = input.nextLine();
				executeCommand(line, lineNumber);
				lineNumber++;
			}
			return "Script has finished!";
		}
	}

	public String executeCommand(String command, int lineNumber) {
		MacroCommand mc1 = new MacroCommand(command);
		String output = mc1.executeCommand();
		System.out.println(output);
		return output;
	}

	public static void main(String[] args) {
		Macro m1 = new Macro();
		m1.readScript("create generic alert.txt");
		m1.executeScript();
	}

}
