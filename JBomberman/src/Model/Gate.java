package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Gate represents the gate of a maze.
 * @author Raf&Vinz
 *
 */
public class Gate implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean opened;
	private int y;
	private int x;
	
	/**
	 * This is the class builder that save the position of the gate.
	 * @param x coordinate x of the gate.
	 * @param y coordinate y of the gate.
	 */
	public Gate(int y, int x) {
		this.y = y;
		this.x = x;
	}
	
	/**
	 * Y-axes Getter.
	 * @return coordinate y of the gate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * X-axes Getter.
	 * @return coordinate x of the gate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * This method check if the gate is opened.
	 * @return returns true if the gate is open, false otherwise.
	 */
	public boolean isOpened() {
		return opened;
	}
	
	/**
	 * This method set the status of the gate.
	 * @param opened true if the gate is opened, false otherwise.
	 */
	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	/**
	 * This method save the state of the Gate in a ObjectOutputStream, which will then be saved in a file.
	 * @param oos ObjectOutputStream stream to save on the file.
	 */
	public void writeObject(ObjectOutputStream oos) {
		try{
			//Write opened
            oos.writeBoolean(opened);
            //Write y
            oos.writeInt(y);
            //Write x
            oos.writeInt(x);
        }
        catch(Exception e){
            System.out.println("Error while saving the gate");
            e.printStackTrace();
        }
	}
	
	/**
	 * This method loads a Gate reading its attribute from ObjectInputStream.  
	 * @param ois ObjectInputStream stream to read from file.
	 * @return a Gate.
	 */
	public static Gate loadObject(ObjectInputStream ois){
        Gate g = new Gate(0, 0);
        try{
        	g.opened = ois.readBoolean();
			g.y = ois.readInt();
            g.x = ois.readInt();
        }
        catch(Exception e){
        	System.out.println("Error while loading the gate");
            e.printStackTrace();
        }
        return g;
    }
}
