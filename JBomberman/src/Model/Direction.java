package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This enum represents one of the four direction (UP, DOWN, LEFT, RIGHT)
 * @author Raf&Vinz
 *
 */
public enum Direction {
	UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

	private int deltaY;
	private int deltaX;
	
	/**
	 * Class builder that create a direction based on deltaY and deltaX.
	 * @param deltaY integer for y-axis.
	 * @param deltaX integer for x-axis.
	 */
	Direction(int deltaY, int deltaX) {
		this.deltaY = deltaY;
		this.deltaX = deltaX;
	}
	
	/**
	 * Getter of deltaY.
	 * @return integer deltaY.
	 */
	public int getDeltaY() {
		return deltaY;
	}
	
	/**
	 * Getter of deltaX.
	 * @return integer deltaX.
	 */
	public int getDeltaX() {
		return deltaX;
	}
	
	/**
	 * This method returns the opposite direction.
	 * @return the opposite direction of the input.
	 */
	public Direction getOpposite() {
		return switch(this) {
		case UP -> DOWN;
		case DOWN -> UP;
		case RIGHT -> LEFT;
		case LEFT -> RIGHT;
		};
	}
	
	/**
	 * This method returns a string from a direction.
	 */
	public String toString() {
		return switch(this) {
		case UP -> "Up";
		case DOWN -> "Down";
		case RIGHT -> "Right";
		case LEFT -> "Left";
		};
	}
	
	/**
	 * This method save the state of the direction in a ObjectOutputStream, which will then be saved in a file.
	 * @param oos ObjectOutputStream stream to save on the file.
	 */
	public void writeObject(ObjectOutputStream oos) {
		try{
			//Write deltay
			oos.writeInt(getDeltaY());
			//Write deltax
			oos.writeInt(getDeltaX());
        }
        catch(Exception e){
            System.out.println("Error while saving the direction");
            e.printStackTrace();
        }
	}
	
	/**
	 * This method loads a direction reading its attribute from ObjectInputStream.
	 * @param ois ObjectInputStream stream to read from file.
	 * @return Direction.
	 */
	public static Direction loadObject(ObjectInputStream ois){
        try{
        	//Load deltay
        	int deltaY = ois.readInt();
        	//Load deltax
        	int deltaX = ois.readInt();
        	
        	if (deltaY == -1) 
        		return Direction.UP;
        	if (deltaY == 1)
        		return Direction.DOWN;
        	else {
        		if (deltaX == -1)
        			return Direction.LEFT;
        		else
        			return Direction.RIGHT;
        	}
        }
        catch(Exception e){
        	System.out.println("Error while loading the direction");
            e.printStackTrace();
        }
        return null;
    }
}
