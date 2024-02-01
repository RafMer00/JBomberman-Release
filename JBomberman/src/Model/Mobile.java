package Model;

/**
 * A class implements this interface if it can move.
 * @author Raf&Vinz
 *
 */
public interface Mobile {
	
	/**
	 * This function update the object position in the game in input.
	 * @param g the game.
	 */
	public void updatePosition(Game g);
}
