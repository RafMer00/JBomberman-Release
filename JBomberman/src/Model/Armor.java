package Model;

/**
 * Armor represents a type of a powerup that protects Bomberman from damage.
 * @author Raf&Vinz
 *
 */
public class Armor extends PowerUp{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Game g) 
	{
		g.getBm().setArmor(5100);
	}
}
