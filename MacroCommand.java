import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MacroCommand {

	private static Robot r1;
	private int currentX;
	private int currentY;

	private String command = "";

	public MacroCommand(String command) {
		try {
			r1 = new Robot();
			this.command = command;
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	private void pressKey(int keyCode) {
		r1.keyPress(keyCode);
		r1.delay(200);
	}

	private void mouseMove(int x, int y) {
		Point current = MouseInfo.getPointerInfo().getLocation();
		Point destination = new Point(x, y);
		currentX = current.x;
		currentY = current.y;
		while (!(closeEnoughX(destination) && closeEnoughY(destination))) {
			moveTowardsX(destination, x);
			moveTowardsY(destination, y);
			r1.mouseMove(currentX, currentY);
			r1.delay(10);
		}
	}

	// Ensusre we are within 3 pixels of the destination in the x direction.
	private boolean closeEnoughX(Point destination) {
		if ((Math.abs(destination.x - currentX) < 3)) {
			return true;
		}
		return false;
	}

	// Ensusre we are within 3 pixels of the destination in the y direction.
	private boolean closeEnoughY(Point destination) {
		if ((Math.abs(destination.y - currentY) < 3)) {
			return true;
		}
		return false;
	}

	private void moveTowardsX(Point destination, int pixel) {
		if (closeEnoughX(destination)) {
			// Do nothing.
		} else if (pixel > currentX) {
			currentX = currentX + 3;
		} else if (pixel < currentX) {
			currentX = currentX - 3;
		}
	}

	private void moveTowardsY(Point destination, int pixel) {
		if (closeEnoughY(destination)) {
			// Do nothing.
		} else if (pixel > currentY) {
			currentY = currentY + 3;
		} else if (pixel < currentY) {
			currentY = currentY - 3;
		}
	}

	private void mouseClick() {
		r1.mousePress(InputEvent.BUTTON1_MASK);
		r1.delay(200);
		r1.mouseRelease(InputEvent.BUTTON1_MASK);
		r1.delay(200);
	}

	public String executeCommand() {
		if (command.contains("button")) {
			if (command.contains("alert")) {
				mouseMove(80, 125);
				mouseClick();
			}
		} else if (command.contains("field")) {
			if (command.contains("alert")) {
				mouseMove(500, 425);
				mouseClick();
			}
		} else if (command.equals("enter/return")) {
			pressKey(KeyEvent.VK_ENTER);
		} else if (command.contains("enter text")) {
			String text = command.substring(12); // Skip past 'enter text:' tag.
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				pressKey(KeyEvent.getExtendedKeyCodeForChar(c)); // Conversion
																	// automated?
			}
		} else {
			return "Command Failed!";
		}

		r1.delay(500);
		return "Command Executed Okay";
	}
}
