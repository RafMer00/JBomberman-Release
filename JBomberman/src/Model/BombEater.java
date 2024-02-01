package Model;

/**
 * BombEater represents a type of enemy.
 * @author Raf&Vinz
 *
 */
public class BombEater extends Enemy{
	

	private static final long serialVersionUID = 1L;

	/**
	 * BombEater builder.
	 * @param x coordinate x of the cell with the bomb
	 * @param y coordinate y of the cell with the bomb
	 */
    public BombEater(int y, int x) {
        super(y, x, 400, 600);
    }
    
    @Override
	public void updatePosition(Game g) {
		proMotion(g);
	}

}
