package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

/**
 * PowerUp represents an abstract powerup.
 * @author Raf&Vinz
 *
 */
public abstract class PowerUp implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean visible;
	
	/**
	 * This method execute a powerup.
	 * @param g the game.
	 */
	public abstract void execute(Game g);
	
	/**
	 * This method randomly chooses a powerup.
	 * @param n integer to set the limit of choices.
	 * @return new powerup.
	 */
	public static PowerUp randomPowerUp(int n) {
		Random r = new Random();
		return switch(r.nextInt(n)) {
		case 0 -> new ExplosionExpander();
		case 1 -> new Time();
		case 2 -> new ExtraBomb();
		case 3 -> new Cake();
		case 4 -> new Hearth();
		case 5 -> new Armor();
		case 6 -> new Skull();
		case 7 -> new MisteryItem();		
		default -> null;
		};
	}
	
	/**
	 * This method is visibility getter.
	 * @return true if the powerup is visible, false otherwise.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Visibility setter.
	 * @param b the value to set as visibility.
	 */
	public void setVisibility(boolean b) {
		visible = b;
	}
		
	/**
	 * This method save the state of the PowerUp in a ObjectOutputStream, which will then be saved in a file.
	 * @param oos ObjectOutputStream stream to save on the file.
	 */
	public void writeObject(ObjectOutputStream oos) {
		try{
			oos.writeObject(getClass().getSimpleName());
			oos.writeBoolean(visible);
        }
        catch(Exception e){
            System.out.println("Error while saving the powerup");
            e.printStackTrace();
        }
	}
	
	/**
	 * This method loads a PowerUp reading its attribute from ObjectInputStream  
	 * @param ois ObjectInputStream stream to read from file.
	 * @return a PowerUp.
	 */
	public static PowerUp loadObject(ObjectInputStream ois){
        try{
        	//Loads a powerup if it exists 
        	Object o = ois.readObject();
			if (o != null) {
				PowerUp pu = switch((String)o) {
				case "ExplosionExpander" -> new ExplosionExpander();
				case "Time" -> new Time();
				case "ExtraBomb" -> new ExtraBomb();
				case "Cake" -> new Cake();
				case "Hearth" -> new Hearth();
				case "Armor" -> new Armor();
				case "Skull" -> new Skull();
				case "MisteryItem" -> new MisteryItem();
				default -> throw new IllegalArgumentException("Unexpected value: " + ois.readObject());
				};
				pu.visible = ois.readBoolean();
				return pu;
			}
        }
        catch(Exception e){
        	System.out.println("Error while loading the powerup");
            e.printStackTrace();
        }
        return null;
    }
}
