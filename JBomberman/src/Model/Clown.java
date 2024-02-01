package Model;

/**
 * Clown represents a Boss.
 * @author Raf&Vinz
 *
 */
public class Clown extends Boss{
	private static final long serialVersionUID = 1L;
	private Position[] shoots;
	
	/**
	 * This is class builder.
	 * @param y coordinate y in the the maze.
	 * @param x coordinate x in the the maze.
	 */
	public Clown(int y, int x) {
		super(y, x, 50000, 1000);
		shoots = new Position[8];
		width = 45;
		height = 36;
	}
	
	/**
	 * Getter of the Clown's shoots.
	 * @return the position of the shoots.
	 */
	public Position[] getShoots() {
		return shoots;
	}
	
	@Override
	public boolean hitBomberman(Game g) {
		if (shoots[0] != null) {
			for (int i = 0; i < 8; i++)
				if (Game.hitBomberman(g.getBm().getPos(), g.getBm().getWidth(), g.getBm().getHeight(), 
					new Position(shoots[i].getY(), shoots[i].getX()), 5, 5)) {
					return true;
				}
		}
		return Game.hitBomberman(g.getBm().getPos(), g.getBm().getWidth(), g.getBm().getHeight(), 
				pos, width, height);
	}
	
	@Override 
	public void updatePosition(Game g) {
		if (hitBomberman(g)) {
			notifyView();
		}
		if (++time == 20) {
			Position p = new Position((Maze.totalHeight - Maze.cellSide)/2, (Maze.totalWidth - Maze.cellSide)/2);
			shoots[0] = new Position(p.getY(), p.getX());
			shoots[1] = new Position(p.getY(), p.getX());
			shoots[2] = new Position(p.getY(), p.getX());
			shoots[3] = new Position(p.getY(), p.getX());
			shoots[4] = new Position(p.getY(), p.getX());
			shoots[5] = new Position(p.getY(), p.getX());
			shoots[6] = new Position(p.getY(), p.getX());
			shoots[7] = new Position(p.getY(), p.getX());
			
			for(int i = 0; i < 20; i++)
				moveShoots();
		}
		else if (time > 20 && time <= 48) {
			moveShoots();
		}
		else if (time == 49){
			time = 0;
			shoots = new Position[8];
		}
	}
	
	/**
	 * This method move all the Clown's shoots in all direction.
	 */
	private void moveShoots() {
		shoots[0] = shoots[0].traslation(Direction.LEFT).traslation(Direction.UP);
		shoots[1] = shoots[1].traslation(Direction.UP);
		shoots[2] = shoots[2].traslation(Direction.RIGHT).traslation(Direction.UP);
		shoots[3] = shoots[3].traslation(Direction.RIGHT);
		shoots[4] = shoots[4].traslation(Direction.RIGHT).traslation(Direction.DOWN);
		shoots[5] = shoots[5].traslation(Direction.DOWN);
		shoots[6] = shoots[6].traslation(Direction.LEFT).traslation(Direction.DOWN);
		shoots[7] = shoots[7].traslation(Direction.LEFT);
	}
}
