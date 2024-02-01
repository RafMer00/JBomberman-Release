package Model;

/**
 * Ant represents a type of enemy.
 * @author Raf&Vinz
 *
 */
public class Ant extends Enemy{
	
	private static final long serialVersionUID = 1L;

	/**
	 * This is the class builder of Ant. 
	 * @param y coordinate y in the the maze. 
	 * @param x coordinate x in the the maze.
	 */
	public Ant(int y, int x) {
		super(y, x, 200, 50);
	}
	
	@Override
	public void updatePosition(Game g) {
		randomMotion(g);
	}
}
