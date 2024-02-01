package Model;

/**
 * Floor represents a type of single cell where there is nothing.
 * @author Raf&Vinz
 *
 */
public class Floor extends ObjectCell implements Passable{
	private static final long serialVersionUID = 1L;

	/**
	 * This is the class builder which saves a powerup if it isn't null.
	 * @param powerup which will be placed on the floor.
	 */
	public Floor(PowerUp powerup) {
		if (powerup != null) {
			this.powerup = powerup;
			powerup.setVisibility(true);
		}
	}
	
	/**
	 * This is the blank class builder. 
	 */
	public Floor() {
		this(null);
	}

	/**
	 * This method remove the powerup from the floor.
	 */
	public void removePowerUp() {
		powerup.setVisibility(false);
		powerup = null;
	}
}
