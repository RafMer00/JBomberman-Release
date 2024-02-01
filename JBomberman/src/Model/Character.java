package Model;


import java.util.Observable;

/**
 * Character represent a generic character of the game. 
 * @author Raf&Vinz
 *
 */
public abstract class Character extends Observable{
	protected int height;
	protected int width;
	protected int hp;
	protected Position pos;
	protected Direction lastMove;

	/**
	 * This is the class builder.
	 * @param y coordinate y in the the maze.
	 * @param x coordinate x in the the maze.
	 * @param height the height of the character.
	 * @param width the width of the character.
	 * @param hp the life of the character.
	 */
	public Character(int y, int x, int height, int width, int hp) {
		pos = new Position(y, x);
		this.height = height;
		this.width = width;
		this.hp = hp;
		lastMove = Direction.RIGHT;
	}
	
	/**
	 * This method set lastMove of the character with the parameter.
	 * @param dir the direction of the character.
	 */
	public void move(Direction dir) {
			pos = pos.traslation(dir);
			lastMove = dir;
			notifyView();
	}
	
	/**
	 * This method check if the character is on a bomb.
	 * @param pos the position where to check if bomb is present.
	 * @param m the maze.
	 * @return return true if there is a bomb and false otherwise.
	 */
	private boolean controlBomb(Position pos, Maze m) {
		int y0 = (pos.getY());
		int x0 = (pos.getX());
		int y1 = (y0 + height - 1);
		int x1 = (x0 + width - 1);
		
		// True if one of the four corners are in a bombcell.
		return m.getMatrix()[y0/Maze.cellSide][x0/Maze.cellSide] instanceof BombCell ||
				m.getMatrix()[y0/Maze.cellSide][x1/Maze.cellSide] instanceof BombCell ||
				m.getMatrix()[y1/Maze.cellSide][x0/Maze.cellSide] instanceof BombCell ||
				m.getMatrix()[y1/Maze.cellSide][x1/Maze.cellSide] instanceof BombCell;

	}
	
	/**
	 * Getter position of the character on the x axis.
	 * @return position on the x-axis.
	 */
	public int getX(){ 
		return pos.getX();	
	}
	
	/**
	 * Getter position of the character on the y axis.
	 * @return position on the y-axis.
	 */
	public int getY(){ 
		return pos.getY();	
	}
	
	/**
	 * Getter of the character life.
	 * @return character life.
	 */
	public int getHp() {
		return hp;
	}
	
	/**
	 * Setter of character's position.
	 * @param pos the position of the character.
	 */
	public void setPosition(Position pos) {
		this.pos = pos;
		lastMove = Direction.RIGHT;
		notifyView();
	}
	
	/**
	 * This method check whether the character can go to a certain location.
	 * @param oldPos the old position of the character.
	 * @param newPos the new position of the character.
	 * @param m the maze.
	 * @return true if it can return false otherwise
	 */
	protected boolean controlMovement(Position oldPos, Position newPos, Maze m) {
		//Position is out of range
		if (newPos.isOutOfRange(this))	
			return false;
		//Check if it's going on a bomb
		if (!controlBomb(oldPos, m) && controlBomb(newPos, m)) 
			return false;
		//Checks if the character is dead
		if (hp <= 0)	
			return false;
		
		//Coordinates of the vertices above left and below right
		int y0 = (newPos.getY());	
		int x0 = (newPos.getX());
		int y1 = (y0 + height - 1);
		int x1 = (x0 + width - 1);
		
		//Checks if the coordinates are on passable cells
		return m.getMatrix()[y0/Maze.cellSide][x0/Maze.cellSide] instanceof Passable &&
				m.getMatrix()[y0/Maze.cellSide][x1/Maze.cellSide] instanceof Passable &&
				m.getMatrix()[y1/Maze.cellSide][x0/Maze.cellSide] instanceof Passable &&
				m.getMatrix()[y1/Maze.cellSide][x1/Maze.cellSide] instanceof Passable;
	}
	
	/**
	 * Getter of the width.
	 * @return width of character.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Getter of the height.
	 * @return height of the character.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Getter of the character's direction.
	 * @return the latest direction.
	 */
	public Direction getLastMove() {
		return lastMove;
	}
	
	/**
	 * Setter of the Character's life.
	 * @param hp remaining life of the character.
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	/**
	 * This method notify all the observers.
	 */
	protected void notifyView() {
		setChanged();
		notifyObservers(pos);
	}
	
	/**
	 * Getter of the character's position.
	 * @return position of the character.
	 */
	public Position getPos() {
		return pos;
	}	

	
}
