package Model;

/**
 * Hearth represents a type of a powerup that gives a new life to Bomberman.
 * @author Raf&Vinz
 *
 */
public class Hearth extends PowerUp{
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Game g) {
		g.getBm().incrLives();
	}

}
