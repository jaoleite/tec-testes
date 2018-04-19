package br.com.tecconcursos.util;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import sun.awt.windows.WComponentPeer;

@SuppressWarnings("restriction")
public class WindowForeround {

	public WindowForeround() {
		
	}
	
	public static Robot createRobot() throws AWTException {
		Robot robot = new Robot();
		robot.setAutoWaitForIdle(true);
		robot.setAutoDelay(10);
		return robot;
	}
	
	public static void main1(String[] args) throws AWTException {
		Robot robot = createRobot();
		robot.keyPress(KeyEvent.VK_ALT);
		for(int i=0; i<10; i++) {
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.delay(500);
		}
		robot.keyRelease(KeyEvent.VK_ALT);
	}
	
	@SuppressWarnings({ "deprecation"})
	public static long getWnd(Frame frame) {
		return frame.getPeer() != null ? ((WComponentPeer) frame.getPeer()).getHWnd() : 0;
	}
	
	public static void main(String[] args) {
		long i = getWnd(new Frame());
		System.out.println(i);
	}

}
