package Model;

/**
 * EvilBomb represents a type of enemy.
 * @author Raf&Vinz
 *
 */
public class EvilBomb extends Enemy{
	private static final long serialVersionUID = 1L;

	/**
	 * This is the class builder.
	 * @param y coordinate y in the the maze.
	 * @param x coordinate x in the the maze.
	 */
    public EvilBomb(int y, int x) {
        super(y, x, 800, 500);
    }
     
    @Override
	public void updatePosition(Game g) {
		proMotion(g);
	}
}
