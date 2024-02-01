package Model;

/**
 * BombCell represents a single bomb cell that replaces the normal cell when a bomb is placed.
 * @author Raf&Vinz
 *
 */
public class BombCell extends Cell implements Passable{
	private static final long serialVersionUID = 1L;
	private Bomb bomb;
	
	/**
	 * This is the class builder.
	 * @param bomb the bomb placed in the cell.
	 */
	public BombCell(Bomb bomb) {
		this.bomb = bomb;
	}
	
	/**
	 * Getter of the bomb.
	 * @return the bomb placed in the cell
	 */
	public Bomb getBomb() {
		return bomb;
	}

	/**
	 * Bomb setter.
	 * @param bomb the bomb to place in the cell.
	 */
	public void setBomb(Bomb bomb) {
		this.bomb = bomb;
	}
}
