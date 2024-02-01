package Model;

/**
 * Time represents a type of powerup that reset the Game's timer.
 * @author Raf&Vinz
 *
 */
public class Time extends PowerUp{
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Game g) 
	{
		g.resetTime();
	}
}
