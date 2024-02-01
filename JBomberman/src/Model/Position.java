package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.function.BiPredicate;

/**
 * Position represents the location of objects in the game.
 * @author Raf&Vinz
 *
 */
public class Position implements Serializable {
	private static final long serialVersionUID = 1L;
	private int y, x;
	
	/**
	 * This is the class builder.
	 * @param y coordinate y of the position.
	 * @param x coordinate x of the position.
	 */
	public Position(int y, int x) {
		this.y = y;
		this.x = x;
	}

	/**
	 * y-axes coordinate getter.
	 * @return y-axes coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * x-axes coordinate getter.
	 * @return x-axes coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * This method translate the position.
	 * @param dir direction of the translation.
	 * @return new Position translated.
	 */
	public Position traslation(Direction dir) {
		return new Position(y + dir.getDeltaY(), x + dir.getDeltaX());
	}
	
	/**
	 * This method generate a random position.
	 * @param condition is a BiPredicate that will be tested on a random position.
	 * @return a free position.
	 */
	protected static Position randomPosition(BiPredicate<Integer, Integer> condition) {
		Random r = new Random();
		int y = r.nextInt(Maze.height);
		int x = r.nextInt(Maze.width);
		while(condition.test(y, x)) {
			y = r.nextInt(Maze.height);
			x = r.nextInt(Maze.width);
		}
		return new Position(y, x);
	}
	
	/**
	 * This method checks if the character is inside maze edges.
	 * @param c a character.
	 * @return true if the character is out of range.
	 */
	public boolean isOutOfRange(Character c) {
		int y1 = y + c.getHeight();
		int x1 = x + c.getWidth();
		return y < 0 || x < 0 || y1 > Maze.totalHeight || x1 > Maze.totalWidth; 
	}
	
	@Override 
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o instanceof Position) {
			Position p = (Position)o;
			return p.x == x && p.y == y;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return x + y;
	}

	/**
	 * This method save the state of the Position in a ObjectOutputStream, which will then be saved in a file.
	 * @param oos ObjectOutputStream stream to save on the file.
	 */
	public void writeObject(ObjectOutputStream oos){
		try{
			//Write y
            oos.writeInt(y);
            //Write x
            oos.writeInt(x);
        }
        catch(Exception e){
            System.out.println("Error while saving the position");
            e.printStackTrace();
        }
	}
	
	/**
	 * This method loads a Position reading its attribute from ObjectInputStream  
	 * @param ois ObjectInputStream stream to read from file.
	 * @return a Position.
	 */
	public static Position loadObject(ObjectInputStream ois){
        try{
        	int y = ois.readInt();
        	if (y != -1) {
	        	int x = ois.readInt();
	        	return new Position(y, x);
        	}
        }
        catch(Exception e){
        	System.out.println("Error while loading the position");
            e.printStackTrace();
        }
        return null;
    }
}
 