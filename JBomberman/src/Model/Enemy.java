package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Enemy is an abstract class that represents a generic enemy. 
 * @author Raf&Vinz
 *
 */
public abstract class Enemy extends Character implements Mobile, Serializable{
	private static final long serialVersionUID = 1L;
	protected int killScore;
	protected PowerUp powerup;
	
	/**
	 * Enemy Builder. 
	 * @param y coordinate y in the the maze.
	 * @param x coordinate x in the the maze.
	 * @param speed is the speed of a generic enemy.
	 * @param killScore points obtained after killing the Boss.
	 * @param hp is the enemy life.
	 */
	public Enemy(int y, int x, int killScore, int hp) {
		super(y, x, Maze.cellSide, Maze.cellSide, hp);
		this.killScore = killScore;
	}

	/**
	 * Getter of the KillScore.
	 * @return killScore.
	 */
	public int getKillScore() {
		return killScore;
	}
	
	/**
	 * Setter of killScore.
	 * @param killScore is the damage to set.
	 */
	public void setKillScore(int killScore) {
		this.killScore = killScore;
	}
	
	/**
	 * This method check if the enemy hit Bomberman.
	 * @param g the game.
	 * @return true if the enemy hit Bomberman false otherwise.
	 */
	public boolean hitBomberman(Game g) {
		return Game.hitBomberman(g.getBm().getPos(), g.getBm().getWidth(), g.getBm().getHeight(), 
				pos, width, height);
	}
	
	/**
	 * Getter of the powerup hidden by the enemy.
	 * @return powerup hidden by the enemy.
	 */
	public PowerUp getPowerup() {
		return powerup;
	}
	
	/**
	 * Setter of the Enemy's powerup.
	 * @param powerup powerup of the enemy.
	 */
	public void setPowerup(PowerUp powerup) {
		this.powerup = powerup;
	}
	
	/**
	 * This method update the enemy position. 
	 */
	public abstract void updatePosition(Game g);
	
	/**
	 * This method move the enemy randomly
	 * @param g
	 */
	public void randomMotion(Game g) {
		//If the enemy has not completed the transition from one cell to another then continue to move 
		//in the same direction
		if (lastMove != null && (getY()%Maze.cellSide > 0 || getX()%Maze.cellSide > 0)) 
			move(lastMove);
		//Otherwise check the possible directions and choose one of them
		else {
			Direction[] directions = Direction.values();
			ArrayList<Direction> okDirections = new ArrayList<>();
			for (Direction d: directions){
				Position newPos = pos.traslation(d);
				if (!newPos.isOutOfRange(this) && controlMovement(pos, newPos, g.getMaze())) {
					okDirections.add(d);
				}
			}
			//If the enemy have reached a cell and has more than one choise to go, he won't return 
			//to the cell from which he comes
			if (lastMove != null && okDirections.size() > 1)
				okDirections.remove(lastMove.getOpposite());
			Random r = new Random();
			//Enemy choose a direction randomly and go in that direction
			if (okDirections.size() > 0) {
				int i = r.nextInt(okDirections.size());
				move(okDirections.get(i));
				lastMove = okDirections.get(i);
			}
		}
	}
	
	/**
	 * This method move the enemy making them following Bomberman.
	 * @param g the current game.
	 */
	public void proMotion(Game g) {
		//If the enemy has not completed the transition from one cell to another then continue to move 
		//in the same direction
		if (lastMove != null && (getY()%Maze.cellSide > 0 || getX()%Maze.cellSide > 0)) 
			move(lastMove);
		else {
			Position target = new Position(getPos().getY() - g.getBm().getPos().getY(), 
										getPos().getX() - g.getBm().getPos().getX());
			if (target.getY() >= target.getX()) {
				if (!moveVertically(g, target))
					if (!moveHorizontally(g, target))
						randomMotion(g);
			}
			//if the target is more distant horizontally he moves horizontally firstly
			else {
				if (!moveHorizontally(g, target))
					if (!moveVertically(g, target))
						randomMotion(g);
			}
		}
	}
		
	/**
	 * This method moves the enemy based on the x-axis.
	 * @param g the game.
	 * @param target distance from Bomberman.
	 * @return true if he has moved.
	 */
	private boolean moveHorizontally(Game g, Position target) {
		//Move to right
		if (target.getX() > 0) {
			if (controlMovement(pos, pos.traslation(Direction.LEFT), g.getMaze())){
				move(Direction.LEFT);
				return true;
			}				
		}
		//Move to left
		if (target.getX() < 0) {
			if (controlMovement(pos, pos.traslation(Direction.RIGHT), g.getMaze())) {
				move(Direction.RIGHT);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method moves the enemy based on the y-axis.
	 * @param g the game.
	 * @param target distance from Bomberman.
	 * @return true if he has moved.
	 */
	private boolean moveVertically(Game g, Position target) {
		//Moves down
		if (target.getY() > 0) {
			if (controlMovement(pos, pos.traslation(Direction.UP), g.getMaze())) { 
				move(Direction.UP);
				return true;
			}
		}
		//Moves upper
		if (target.getY() < 0) {
			if (controlMovement(pos, pos.traslation(Direction.DOWN), g.getMaze())) {
				move(Direction.DOWN);
				return true;
			}
		}
		return false;
	}		
	
	@Override
	public void notifyView() {
		setChanged();
		notifyObservers("Dead");
	}
	
	/**
	 * This method save the Enemy state in a ObjectOutputStream, which will then be saved in a file.
	 * @param oos ObjectOutputStream stream to save on the file.
	 */
	public void writeObject(ObjectOutputStream oos){
		try{
			//Write name of the enemy
			oos.writeObject(getClass().getSimpleName());
			//Write height
			oos.writeInt(height);
			//Write width
			oos.writeInt(width);
			//Write hp
			oos.writeInt(hp);
			//Write pos
			pos.writeObject(oos);
			//Write last move
			lastMove.writeObject(oos);
            //Write kill score
			oos.writeInt(killScore);
            //Write powerup if it exists
			if (powerup != null)
            	powerup.writeObject(oos);
            else
            	oos.writeObject(null);
        }
        catch(Exception e){
            System.out.println("Error while saving the enemy");
            e.printStackTrace();
        }
	}
	
	/**
	 * This method loads Enemy after reading its attribute from ObjectInputStream.
	 * @param ois ObjectInputStream stream to read from file.
	 * @return Enemy.
	 */
	public static Enemy loadObject(ObjectInputStream ois){
        try{
        	//Creates a Enemy based on the name written
			Enemy e = switch((String)ois.readObject()) {
			case "Propeller" -> new Propeller(0, 0);
			case "Bulb" -> new Bulb(0, 0);
			case "EvilBomb" -> new EvilBomb(0, 0);
			case "Ant" -> new Ant(0, 0);
			case "BombEater" -> new BombEater(0, 0);
			case "BigBomb" -> new BigBomb(0, 0);
			case "Clown" -> new Clown(0, 0);
			default -> throw new IllegalArgumentException("Unexpected value: " + ois.readObject());
			};
			//Load heigth
        	e.height = ois.readInt();
			//Load height
        	e.width = ois.readInt();
			//Load hp
        	e.hp = ois.readInt();
			//Load pos
        	e.pos = Position.loadObject(ois);
			//Load last move
        	e.lastMove = Direction.loadObject(ois);
            //Load kill score
        	e.killScore = ois.readInt();
            //Load powerup
        	e.powerup = PowerUp.loadObject(ois);
            return e;
        }
        catch(Exception e){
        	System.out.println("Error while loading the enemy");
            e.printStackTrace();
        }
        return null;
	}
}
