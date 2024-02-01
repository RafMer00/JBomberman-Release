package Control;

import Model.*;
import View.Window;

/**
 * This class represents the Control Manager of the game. It saves as attributes some important things like menu,
 * user, game, bomberman and the JFrame
 * @author Raf&Vinz
 *
 */
public class Manager {
	public static Menu menu = new Menu();
	public static User user = User.getInstance();
	public static Game game;
	public static Bomberman bm;
	public static Window window;
}
