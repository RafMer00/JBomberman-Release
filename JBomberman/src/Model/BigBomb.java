package Model;

/**
 * BigBomb represents a type of Boss.
 * @author Raf&Vinz
 *
 */
public class BigBomb extends Boss{
	
	private static final long serialVersionUID = 1L;

	/**
	 * This is the builder of BigBomb after taking the position set the speed, killscore, hp, wisth and height.
	 * @param y coordinate y in the the maze.
	 * @param x coordinate x in the the maze.
	 */
	public BigBomb(int y, int x) {
		super(y, x, 10000, 1000);
		width = 45;
		height = 36;
		time = - 25;
	}
	
	@Override
	public void updatePosition(Game g) {
		//If the boss started to move he continue to do it.
		if (lastMove != null && time%2 == 0 && time > 0) 
			move(lastMove);
		//Otherwise he moves to reach Bomberman.
		else if (lastMove == null && time == 0) {
			int deltaX = g.getBm().getX() - getX();
			int deltaY = g.getBm().getY() - getY();
			
			if (Math.abs(deltaX) > Math.abs(deltaY)) {
				if (deltaX >= 0)
					move(Direction.RIGHT);
				else
					move(Direction.LEFT);
			}
			else {
				if (deltaY >= 0)
					move(Direction.DOWN);
				else
					move(Direction.UP);
			}	
		}
		
		time++;
		//Every 36 seconds he stops to hit the wall
		if (time >= 36 && time < 39) 
			lastMove = null;
		//He stops hitting to prepare to move
		else if (time >= 42) 
			time = 0;
		
	}
	
	@Override
	public boolean hitBomberman(Game g) {
		return Game.hitBomberman(g.getBm().getPos(), g.getBm().getWidth(), g.getBm().getHeight(), 
				pos, width, height*2);
	}
}
