package Model;

/**
 * ExplosionExpander represents a type of a powerup that increase the explosion radius.
 * @author Raf&Vinz
 *
 */
public class ExplosionExpander extends PowerUp{
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Game g) 
	{
		g.getBm().incRangeBomb(1);
		g.getBm().getBombs().forEach(x -> x.setRange(g.getBm().getRangeBomb()));
	}

}
