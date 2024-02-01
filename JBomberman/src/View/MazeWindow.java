package View;

import java.awt.Component;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Control.Manager;
import Control.MazeManager;
import Control.MoveController;

/**
 * This class represents the window where the user will play the game.
 * @author Raf&Vinz
 *
 */
public class MazeWindow extends JPanel implements Observer{

	public static final long serialVersionUID = 1L;
	public static final int xMargin = 49;
	public static final int yMargin = 135;
	
	private GameData gameBar;
	public static final int cellSize = 54;
	private Bomberman bomberMan;
	private Border border;
	private MoveController moveController = new MoveController();
	private MazeManager mazeManager;
	private JLabel tutorial;
	private JLabel playButton;
	private JLabel pause;
	private JLabel saveButton;
	private JLabel resumeButton;
	private JLabel exitButton;	
	private Window window;
	/**
	 * This is the class builder.
	 */
	public MazeWindow(Window window) {
		this.window = window;
		mazeManager = new MazeManager(this);
		setBounds(200, 0, 800, 800);
		setLayout(null);
		paint();
		
		mazeManager.paintMaze("img/World" + Manager.game.getWorld());

		sortComponents();
	}
	
	/**
	 * This method returns the window where the panel is painted.
	 * @return the window where the panel is painted.
	 */
	public Window getWindow() {
		return window;
	}

	/**
	 * Add to the panel all the components of the window.
	 */
	public void paint() {
		addKeyListener(moveController);
		addMouseListener(moveController);
		setFocusable(true);
		requestFocus();

		bomberMan = new Bomberman(this, Manager.bm.getX()*6 + xMargin,
								Manager.bm.getY()*6 - 50 + yMargin, 
								"img/Bomberman/" + Manager.bm.getLastMove().toString() + ".png");
		add(bomberMan);
		mazeManager.paintEnemies();	
		border = new Border(this, Manager.game.getWorld());
	}
	

	/**
	 * This method sorts all the components in the panel based on their type.
	 */
	public void sortComponents() {
		int i = 0;
		String[] list = {	"JLabel",
							"Boss",
							"Bomberman", 
							"Enemy",
							"Shoot",
							"Bomb",
							"Border",
							"Gate",
							"IndestructibleCell", 
							"DestructibleCell", 
							"ExplosionCellC",
							"ExplosionCellO",
							"ExplosionCellV",
							"PowerUpCell",
							"Floor",
							"GameData"};
		
		for (String s: list) 
			for (Component c: getComponents()) 
				if (c.getClass().getSimpleName().equals(s))
					setComponentZOrder(c, i++);
	}
	
	/**
	 * This method is the Game bar getter.
	 * @return the game bar.
	 */
	public GameData getGameBar() {
		return gameBar;
	}

	/**
	 * This method is the Bomberman getter.
	 * @return Bomberman.
	 */
	public Bomberman getBomberMan() {
		return bomberMan;
	}

	/**
	 * This method returns the controller for the move.
	 * @return the move controller.
	 */
	public MoveController getMoveController() {
		return moveController;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		mazeManager.update(o, arg);
	}

	/**
	 * This method stop the game.
	 */
	public void stopManager() {
		mazeManager.stop();
	}

	/**
	 * This method paint the tutorial at the start of the game.
	 * @param tutorial image with the tutorial.
	 * @param button button to play.
	 */
	public void setTutorial(JLabel tutorial, JLabel button) {
		this.tutorial = tutorial;
		this.playButton = button;

		add(playButton);
		add(tutorial);
	}
	
	/**
	 * This method set the game in pause.
	 * @param pause image with the pause menu.
	 * @param saveButton button to save the game.
	 * @param resumeButton button to resume the game.
	 * @param exitButton button to exit the game.
	 */
	public void setPause(JLabel pause, JLabel saveButton, JLabel resumeButton, JLabel exitButton) {
		this.pause = pause;
		this.saveButton = saveButton;
		this.resumeButton = resumeButton;
		this.exitButton = exitButton;
		
		add(exitButton);
		add(saveButton);
		add(resumeButton);
		add(pause);
	}

	/**
	 * This method return the tutorial image.
	 * @return tutorial image.
	 */
	public JLabel getTutorial() {
		return tutorial;
	}

	/**
	 * This method return the manager of the game.
	 * @return maze manager.
	 */
	public MazeManager getMazeManager() {
		return mazeManager;
	}

	/**
	 * This method set the game bar of the window.
	 * @param gameBar bar with all the game stats.
	 */
	public void setGameBar(GameData gameBar) {
		this.gameBar = gameBar;
	}
}