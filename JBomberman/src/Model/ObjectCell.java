package Model;

/**
 * ObjectCell represents a single cell that contains an object.
 * @author Raf&Vinz
 *
 */
public abstract class ObjectCell extends Cell{
	private static final long serialVersionUID = 1L;
	protected PowerUp powerup;
	
	/**
	 * Powerup getter.
	 * @return powerup on the cell.
	 */
	public PowerUp getPowerup() {
		return powerup;
	}

	/**
	 * Powerup setter.
	 * @param powerup powerup placed on the cell.
	 */
	public void setPowerup(PowerUp powerup) {
		this.powerup = powerup;
	}
	
	
}
