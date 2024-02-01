package Model;

import java.util.Random;

/**
 * MisteryItem represents a type of a powerup.
 * @author Raf&Vinz
 *
 */
public class MisteryItem extends PowerUp{
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Game g) {
		Random r = new Random();
		switch (r.nextInt(7)) {
		case 0: new Time().execute(g); break;
		case 1: new Armor().execute(g); break;
		case 2: new Cake().execute(g); break;
		case 3: new ExplosionExpander().execute(g); break;
		case 4: new ExtraBomb().execute(g); break;
		case 5: new Hearth().execute(g); break;
		case 6: new Skull().execute(g); break;
		}
	}

}
