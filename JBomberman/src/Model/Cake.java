package Model;

/**
 * Cake represent a type of a powerup that adds score to Bomberman.
 * @author Raf&Vinz
 *
 */
public class Cake extends PowerUp{

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Game g) 
	{
		g.incrScore(500);;
	}
}
