package Model;

/**
 * ExtraBomb represents a type of powerup that increase the number of bombs.
 * @author Raf&Vinz
 *
 */
public class ExtraBomb extends PowerUp {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Game g) 
	{
		g.getBm().addBomb();
	}
}
