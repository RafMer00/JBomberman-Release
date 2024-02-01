package Model;

/**
 * Boss represents a generic Boss.
 * @author Raf&Vinz
 *
 */
public abstract class Boss extends Enemy{
	
	private static final long serialVersionUID = 1L;
	protected int time;
	
	/**
	 * This is the class builder.
	 * @param y coordinate y in the the maze.
	 * @param x coordinate x in the the maze.
	 * @param killScore points obtained after killing the Boss.
	 * @param hp the life of a Boss.
	 */
	public Boss(int y, int x, int killScore, int hp) {
		super(y, x, killScore, hp);
	}
}
