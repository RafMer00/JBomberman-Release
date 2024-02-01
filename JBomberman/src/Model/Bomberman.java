package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Bomberman represents the main character of the game. 
 * @author Raf&Vinz
 *
 */
public class Bomberman extends Character implements Serializable{
	private static final long serialVersionUID = 1L;
	private int lives;
	private int armor;
	private Queue<Bomb> bombs;
	private int rangeBomb;
	private Position target;
	
	/**
	 * This is the class builder that creates an instance setting the standard values for every attributes.
	 */
	public Bomberman() {
		super(0, 0, (Maze.cellSide/2) + 1, (int)(Maze.cellSide/2) + 1, 1);
		lives = 5;
		bombs = new LinkedList<>();
		rangeBomb = 1;
		addBomb();
	}
	
	/**
	 * Lives setter.
	 * @param lives lives of Bomberman.
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	/**
	 * This method add a new bomb in a queue of bombs.
	 */
	public void addBomb() { 
		bombs.add(new Bomb(rangeBomb));
	}
	
	/**
	 * Bombs' getter.
	 * @return the number of bombs in the queue of bombs.
	 */
	public int getnBombs() {
		return bombs.size();
	}
	
	/**
	 * This method check if Bomberman has an armor.
	 * @return the armature's state.
	 */
	public boolean isArmor() {
		return armor > 0;
	}
	
	/**
	 * This method set the armor timer.
	 * @param armor the timer value of armor.
	 */
	public void setArmor(int armor) {
		this.armor = armor;
	}
	
	/**
	 * Getter of lives.
	 * @return number of lives.
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * Bombs queue getter.
	 * @return the Queue of bombs.
	 */
	public Queue<Bomb> getBombs() {
		return bombs;
	}
	
	/**
	 * This method increment the number of lives.
	 */
	public void incrLives() {
		lives++;
	}
	
	/**
	 * This method extracts the head of queue.
	 * @return the first bomb of the queue.
	 */
	public Bomb bombRelease() {
		return bombs.poll();
	}
	
	/**
	 * This method update the Bomberman position.
	 * @param dir is the character's direction.
	 * @param g is the game.
	 */
	public void updatePosition(Direction dir, Game g) {
		//If target is set Bomberman moves to that direction
		if (target != null) 
			moveToTarget(g);
		
		//Otherwise he moves in the direction given as input
		else if (dir != null){
			lastMove = dir;
			Position newPos = pos.traslation(dir);
			Maze m = g.getMaze();
			//If he can move in that direction he does it
			if (controlMovement(pos, newPos, m)) 
				move(dir);
			else { 
				//If he can't go up or down for only one pixel he does it anyway
				if (dir.equals(Direction.DOWN) || dir.equals(Direction.UP)) {
					if (pos.getX()%Maze.cellSide == (Maze.cellSide - width + 1) && controlMovement(pos, newPos.traslation(Direction.LEFT), m)) {
						move(Direction.LEFT);
						move(dir);                         
					}
					else if (pos.getX()% Maze.cellSide == (Maze.cellSide - 1) && controlMovement(pos, newPos.traslation(Direction.RIGHT), m)) {
						move(Direction.RIGHT);			
						move(dir);		
					}
				}
				//If he can't go left or right for only one pixel he does it anyway
				else if (dir.equals(Direction.LEFT) || dir.equals(Direction.RIGHT)) {
					if (pos.getY()%Maze.cellSide == (Maze.cellSide - height + 1) && controlMovement(pos, newPos.traslation(Direction.UP), m)) {
						move(Direction.UP);
						move(dir);
					}
					else if (pos.getX()%Maze.cellSide == (Maze.cellSide - 1) && controlMovement(pos, newPos.traslation(Direction.DOWN), m)) {
						move(Direction.DOWN);
						move(dir);
					}
				}
			}
			//Control if he arrives on a PowerUp
			controlCell(g);
		}
	}
	
	/**
	 * This method move Bomberman to the target.
	 * @param g the current game.
	 */
	private void moveToTarget(Game g) {
		boolean canMove = false;
		boolean moved = false;
		
		//if the target is more distant vertically he moves vertically firstly
		if (target.getY() >= target.getX()) {
			if (moveVertically(g, moved)) {
				canMove = true;
				moved = true;
			}
			if (moveHorizontally(g, moved)) {
				canMove = true;
				moved = true;
			}
		}
		//if the target is more distant horizontally he moves horizontally firstly
		else {
			if (moveHorizontally(g, moved)) {
				canMove = true;
				moved = true;
			}
			if (moveVertically(g, moved)) {
				canMove = true;
				moved = true;
			}
		}	
		
		//if he can't move he stops
		if (!canMove) 
			target = null;
		
		//if he reached the target he stops
		if (moved && target.equals(new Position(0, 0))) 
			target = null;		
		
	}
	
	/**
	 * This method moves Bomberman based on the x-axis. 
	 * @param g the game.
	 * @param moved state of Bomberman, if he moved.
	 * @return true if he has moved.
	 */
	private boolean moveHorizontally(Game g, boolean moved) {
		//Move to right
		if (target.getX() > 0 && !moved) {
			if (controlMovement(pos, pos.traslation(Direction.RIGHT), g.getMaze())){
				move(Direction.RIGHT);
				target = target.traslation(Direction.LEFT);
				return true;
			}				
		}
		//Move to left
		if (target.getX() < 0 && !moved) {
			if (controlMovement(pos, pos.traslation(Direction.LEFT), g.getMaze())) {
				move(Direction.LEFT);
				target = target.traslation(Direction.RIGHT);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method moves Bomberman based on the y-axis.
	 * @param g the game.
	 * @param moved state of Bomberman, if he moved.
	 * @return true if he has moved.
	 */
	private boolean moveVertically(Game g, boolean moved) {
		//Moves down
		if (target.getY() > 0 && !moved) {
			if (controlMovement(pos, pos.traslation(Direction.DOWN), g.getMaze())) { 
				move(Direction.DOWN);
				target = target.traslation(Direction.UP);
				return true;
			}
		}
		//Moves upper
		if (target.getY() < 0 && !moved) {
			if (controlMovement(pos, pos.traslation(Direction.UP), g.getMaze())) {
				move(Direction.UP);
				target = target.traslation(Direction.DOWN);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method check if a cell touched by Bomberman has a powerup or the door. 
	 * @param game the game.
	 */
	private void controlCell(Game game) {
		int y0 = (pos.getY());
		int x0 = (pos.getX());
		int y1 = (y0 + height - 1)/Maze.cellSide;
		int x1 = (x0 + width - 1)/Maze.cellSide;
		
		x0 /= Maze.cellSide;
		y0 /= Maze.cellSide;
		
		controlPowerUp(game.getMaze().getCell(new Position(y0, x0)), game);
		controlPowerUp(game.getMaze().getCell(new Position(y0, x1)), game);
		controlPowerUp(game.getMaze().getCell(new Position(y1, x0)), game);
		controlPowerUp(game.getMaze().getCell(new Position(y1, x1)), game);
		Gate g = game.getMaze().getGate();
		if (((y0 == g.getY() && x0 == g.getX()) || (y0 == g.getY() && x0 == g.getX())) && g.isOpened()) {
			game.incrLevel();
		}
	}

	/**
	 * This method check if a cell is a floor and if it has a powerup.
	 * @param cell a single cell of the game.
	 * @param game the game.
	 */
	private void controlPowerUp(Cell cell, Game game) {
		if (cell instanceof Floor) {
			Floor f = (Floor) cell;
			PowerUp pu = f.getPowerup();
			if (pu != null) {
				pu.execute(game);
				f.removePowerUp();
			}
		}
	}

	/**
	 * This method increase the bomb's range.
	 * @param i how much imncrements the bombs range.
	 */
	public void incRangeBomb(int i) {
		rangeBomb += i;
		if (rangeBomb == 0)
			rangeBomb++;
		for (Bomb bomb: bombs) 
			bomb.setRange(bomb.getRange() + 1);
	}
	
	/**
	 * Range Getter.
	 * @return range of the bombs of Bomberman.
	 */
	public int getRangeBomb() {
		return rangeBomb;
	}
	
	/**
	 * This method decreases armor based on decr.
	 * @param decr is the delta integer for the decrease.
	 */
	public void decrArmor(int decr) {
		if (armor > 0)
			armor -= decr;
	}
	
	/**
	 * This method set the target.
	 * @param pos is the position target.
	 */
	public void moveToPosition(Position pos) {
		int deltaY = pos.getY() - getY();
		int deltaX = pos.getX() - getX();
		target = new Position(deltaY, deltaX);
	}
	
	/**
	 * This method kills Bomberman.
	 */
	public void die() {
		target = null;
		lastMove = null;
		lives--;
	}
	
	/**
	 * This method save the Bomberman's state in a ObjectOutputStream, which will then be saved in a file.
	 * @param oos ObjectOutputStream stream to save on the file.
	 */
	public void writeObject(ObjectOutputStream oos){
		try{
			//Write height
			oos.writeInt(height);
			//Wrote width
			oos.writeInt(width);
			//Write hp
			oos.writeInt(hp);
			//Write pos
			pos.writeObject(oos);
			//Write last move
			lastMove.writeObject(oos);
            //Write lives
			oos.writeInt(lives);
            //Write armor
			oos.writeInt(armor);
            //Write bombs
			oos.writeInt(bombs.size());
            for (Bomb bomb: bombs)
            	bomb.writeObject(oos);
            //Write rangebomb
            oos.writeInt(rangeBomb);
            //Write target
            if (target != null)
            	target.writeObject(oos);
            else
            	oos.writeObject(null);

        }
        catch(Exception e){
            System.out.println("Error while saving Bomberman");
            e.printStackTrace();
        }
	}
	
	/**
	 * This method loads Bomberman after reading its attribute from ObjectInputStream  
	 * @param ois ObjectInputStream stream to read from file.
	 * @return Bomberman.
	 */
	public static Bomberman loadObject(ObjectInputStream ois){
        Bomberman b = new Bomberman();
        try{
			//Load height
        	b.height = ois.readInt();
			//Load width
        	b.width = ois.readInt();
			//Load hp
        	b.hp = ois.readInt();
			//Load pos
        	b.pos = Position.loadObject(ois);
			//Load last move
        	b.lastMove = Direction.loadObject(ois);
			//Load lives
        	b.lives= ois.readInt();
			//Load armor
        	b.armor = ois.readInt();	
			//Load bombs
        	int n = ois.readInt();
			b.bombs = new LinkedList<>();
			for (int i = 0; i < n; i++)
				b.bombs.add(Bomb.loadObject(ois));
			//Load rangebomb
			b.rangeBomb = ois.readInt();
			//Load target
			b.target = (Position)ois.readObject();
        }
        catch(Exception e){
        	System.out.println("Error while loading Bomberman");
            e.printStackTrace();
        }
        return b;
    }
}
