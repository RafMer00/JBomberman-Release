package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Bomb represents a single bomb of the game.
 * @author Raf&Vinz
 *
 */
public class Bomb implements Serializable{
	private static final long serialVersionUID = 1L;
	protected int range;
	protected int damage;
	protected int timer;
	protected Position pos;
	
	/**
	 * This is the class builder with no parameters. It sets range, damage and the position with the default values.
	 */
	public Bomb() {
		this(1, 50, null);
	}
	
	/**
	 * This is the class builder. It sets range with the value
	 * given as parameter and damage and the position with the default values.
	 * @param range the explosion's range of the bomb in the game.
	 */
	public Bomb(int range) {
		this(range, 50, null);
	}
	
	/**
	 * This is class builder. It sets range, damage and position with the values
	 * given as parameter .
	 * @param range the explosion's range of the bomb in the game.
	 * @param damage the explosion's damage of the bomb in the game.
	 * @param pos the explosions's position of the bomb in the game. 
	 */
	public Bomb(int range, int damage, Position pos)
	{
		this.range = range;
		this.damage = damage;
		this.timer = 3000; 		//ms
		this.pos = pos;
	}
	
	
	/**
	 * This is the range setter.
	 * @param range the explosion's range of the bomb in the game.
	 */
	public void setRange(int range) { 
		this.range = range; 
	}
	
	/**
	 * This is the position setter.
	 * @param pos the explosions's position of the bomb in the game.
	 */
	public void setPosition(Position pos) {
		this.pos = pos;
	}
	
	/**
	 * Getter of the damage.
	 * @return damage the damage inflicted by the bomb.
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Setter of damage.
	 * @param damage the damage inflicted by the bomb.
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * This method check if a bomb has exploded.
	 * @return the bomb's state.
	 */
	public boolean isExplosed() {
		return timer == 0;
	}
	
	/**
	 * Getter of range.
	 * @return the range of the bomb.
	 */
	public int getRange() {
		return range;
	}
	
	/**
	 * This method change time based on delta.
	 * @param delta the incremental value.
	 */
	public void deltaTime(int delta) {
		timer += delta;
	}
	
	
	/**
	 * Getter of timer.
	 * @return the timer of the bomb.
	 */
	public int getTimer() {
		return timer;
	}

	/**
	 * Getter of position.
	 * @return the position of the bomb.
	 */
	public Position getPos() {
		return pos;
	}
	
	/**
	 * This method save the state of the bomb in a ObjectOutputStream, which will then be saved in a file.
	 * @param oos ObjectOutputStream stream to save on the file.
	 */
	public void writeObject(ObjectOutputStream oos) {
		try{
            //Write range
			oos.writeInt(range);
            //Write damage
			oos.writeInt(damage);
            //Write timer
			oos.writeInt(timer);
            //Write position if it exists
			if (pos != null)
            	pos.writeObject(oos);
            else {
            	oos.writeInt(-1);
            }
        }
        catch(Exception e){
            System.out.println("Error while saving the bomb");
            e.printStackTrace();
        }
	}
	
	/**
	 * This method loads the bomb state reading its attribute from ObjectInputStream  
	 * @param ois ObjectInputStream stream to read from file.
	 * @return a Bomb.
	 */
	public static Bomb loadObject(ObjectInputStream ois){
        try{
        	Bomb b = new Bomb();
        	//Read range
        	b.range = ois.readInt();
			//Read damage
        	b.damage = ois.readInt();
            //Read timer
        	b.timer = ois.readInt();
            //Read position
        	b.pos = Position.loadObject(ois);
            return b;
        }
        catch(Exception e){
        	System.out.println("Error while loading the bomb");
            e.printStackTrace();
        }
        return null;
    }
}
