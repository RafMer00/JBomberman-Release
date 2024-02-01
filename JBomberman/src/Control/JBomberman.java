package Control;

import View.*;

/**
 * This class represents the main class of the game
 * @author Raf&Vinz
 *
 */
public class JBomberman {
	private static JBomberman instance;
	
	/**
	 * This is the private class builder
	 */
	private JBomberman() {
		Manager.window = new Window();
		Manager.menu.addObserver(Manager.window.getMenuWindow());
	}	
	
	/**
	 * This method is used to implement Singleton pattern. It will
	 * create a instance of the game if it doesn't exists and then will return it.
	 * @return the single instance of JBomberman game
	 */
	public JBomberman getInstance() {
		if (instance == null)
			instance = new JBomberman();
		return instance;
	}
	
	public static void main(String[] args) {
		new JBomberman();
	}
}
