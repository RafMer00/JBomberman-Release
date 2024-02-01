package Model;

/**
 * Propeller represents a type of enemy.
 * @author Raf&Vinz
 *
 */
public class Propeller extends Enemy{
	
	private static final long serialVersionUID = 1L;

	/**
	 * This is the class builder that save the position of Propeller as attributes.
	 * @param y coordinate y in the the maze.
	 * @param x coordinate x in the the maze.
	 */
	public Propeller(int y, int x) {
		super(y, x, 50, 10);
	}
	
	@Override
	public void updatePosition(Game g) {
		randomMotion(g);
	}
	
}
