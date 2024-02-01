package View;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import Control.WindowManager;

/**
 * This window represents the main window of the game.
 * @author Raf&Vinz
 *
 */
public class Window extends JFrame{	
	public static final int newGame = 1;
	public static final int loadGame = 2;
	public static final int menu = 3;
	public static final int account = 4;
	public static final int won = 5;
	public static final int lost = 6;
	
	private static final long serialVersionUID = 1235L;
	private MenuWindow menuWindow;
	private AccountWindow accountWindow;	
	private MazeWindow mazeWindow;
	private EndWindow endWindow;
	private WindowManager windowManager;
	
	
	protected static final Font TITLE = new Font("Arial", Font.BOLD, 24);
	protected static final Font SUBTITLE = new Font("Arial", Font.BOLD, 18);
	protected static final Font DATA = new Font("", Font.BOLD, 40);
	
	/**
	 * This is the class builder.
	 */
	public Window() {
		super("JBomberman");
		
		windowManager = new WindowManager();
		
		setBounds(80, 5, 1200, 800);
		setLayout(null);
		
		ImageIcon icon = new ImageIcon("img/Icon2.png"); 
		setIconImage(icon.getImage());

				
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Finestra non resizable
		setResizable(false);
		
		ImageIcon lBanner = new ImageIcon("img/LeftBanner.jpg"); 
		JLabel lImg = new JLabel(lBanner);
		lImg.setBounds(0, 0, 200, 800);
		getContentPane().add(lImg);

		ImageIcon rBanner = new ImageIcon("img/LeftBanner.jpg"); 
		JLabel rImg = new JLabel(rBanner);
		rImg.setBounds(1000, 0, 200, 800);
		getContentPane().add(rImg);


		menuWindow = new MenuWindow();
		getContentPane().add(menuWindow);
		
        setVisible(true);
   	}
	
	/**
	 * This method returns the window of the menu.
	 * @return the window of the menu.
	 */
	public MenuWindow getMenuWindow() {
		return menuWindow;
	}

	/**
	 * This method returns the window of the game.
	 * @return the window of the game.
	 */	
	public MazeWindow getMazeWindow() {
		return mazeWindow;
	}
	
	/**
	 * This method returns the window of the account settings.
	 * @return the window of the account settings.
	 */
	public AccountWindow getAccountWindow() {
		return accountWindow;
	}

	/**
	 * This method returns the end game window.
	 * @return the end game window.
	 */
	public EndWindow getEndWindow() {
		return endWindow;
	}
	
	/**
	 * This method sets the window of the menu.
	 * @param menuWindow window of the menu.
	 */
	public void setMenuWindow(MenuWindow menuWindow) {
		this.menuWindow = menuWindow;
	}

	/**
	 * This method sets the window of the account settings.
	 * @param accountWindow account window of the account settings.
	 */
	public void setAccountWindow(AccountWindow accountWindow) {
		this.accountWindow = accountWindow;
	}

	/**
	 * This method sets the window of the game.
	 * @param mazeWindow window of the game.
	 */
	public void setMazeWindow(MazeWindow mazeWindow) {
		this.mazeWindow = mazeWindow;
	}

	/**
	 * This method sets the window at the end of the game.
	 * @param endWindow window at the end of the game.
	 */
	public void setEndWindow(EndWindow endWindow) {
		this.endWindow = endWindow;
	}
	
	/**
	 * This method returns the manager for the windows.
	 * @return the manager for the windows.
	 */
	public WindowManager getWindowManager() {
		return windowManager;
	}

	/**
	 * This method changes the window based on an integer.
	 * @param idWindow identifier of the window to load.
	 */
	public void setWindow(int idWindow) {
		windowManager.setWindow(idWindow);
	}

	

}


