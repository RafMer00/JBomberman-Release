package Model;

import java.util.Observable;

/**
 * Menu represents the menu of the game.
 * @author Raf&Vinz
 *
 */
public class Menu extends Observable{
	private Option pointedOption;
	
	/**
	 * Menu's builder.
	 */
	public Menu() {
		pointedOption = Option.NEWGAME;
	}
	
	/**
	 * Nested enum that enumerates the possible things to do in a menu.
	 * @author Raf&Vinz
	 *
	 */
	public static enum Option{
		NEWGAME(0),
		LOADGAME(1),
		SETTINGS(2);
		
		private int i;
		
		/**
		 * Class builder that create an option.
		 * @param i integer that represents an option.
		 */
		Option(int i) {
			this.i = i;
		}
		
		/**
		 * Option identifier getter.
		 * @return option identifier.
		 */
		public int getValue() {
			return i;
		}
	}
	
	/**
	 * This method changes the menu pointed option based on the direction in input.
	 * @param d a direction.
	 */
	public void changeOption(Direction d) {
		switch(pointedOption.getValue() + d.getDeltaY()) {
			case -1: case 2: pointedOption = Option.SETTINGS; break;
			case 0: case 3: pointedOption = Option.NEWGAME; break;
			case 1: pointedOption = Option.LOADGAME; break;
		}
		notifyView();
	}
	
	/**
	 * This method notify all the observers.
	 */
	private void notifyView() {
		setChanged();
		notifyObservers(pointedOption.getValue());		
	}	
	
	/**
	 * This method return the pointed option.
	 * @return the pointed option.
	 */
	public Option getPointedOption() {
		return pointedOption;
	}
}
