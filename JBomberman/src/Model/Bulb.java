package Model;

/**
 * Bulb represents a type of enemy.
 * @author Raf&Vinz
 *
 */
public class Bulb extends Enemy{
	
	private static final long serialVersionUID = 1L;

	/**
	 * This is the class builder.
	 * @param y coordinate y in the the maze.
	 * @param x coordinate x in the the maze.
	 */
	public Bulb(int y, int x) {
		super(y, x, 400, 60);
	}
	
	@Override
	public void updatePosition(Game g) {
		randomMotion(g);
	}
}
