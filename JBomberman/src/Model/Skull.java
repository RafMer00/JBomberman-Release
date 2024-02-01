package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Skull represents a type of powerup that has negative consequences on Bomberman.
 * @author Raf&Vinz
 *
 */
public class Skull extends PowerUp{

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Game g) {
		Random r = new Random();
		ArrayList<Integer> cases = new ArrayList<>();
		
		cases.add(0);
		
		if (g.getBm().getnBombs() > 1)
			cases.add(1);
		
		if (g.getBm().getRangeBomb() > 1)
			cases.add(2);
		
		switch(cases.get(r.nextInt(cases.size()))) {
		case 0: g.getBm().setLives(g.getBm().getLives() - 1); break;
		case 1: if (g.getBm().getnBombs() > 1) g.getBm().getBombs().poll(); break;
		case 2: g.getBm().incRangeBomb(-1); g.getBm().getBombs().forEach(x -> x.setRange(g.getBm().getRangeBomb())); break;
		}
	}
	
}
