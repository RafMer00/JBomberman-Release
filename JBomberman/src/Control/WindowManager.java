package Control;

import Model.Game;
import View.AccountWindow;
import View.EndWindow;
import View.MazeWindow;
import View.MenuWindow;
import View.Window;

public class WindowManager {
	private AudioManager audioManager;
	
	public WindowManager() {
		this.audioManager = AudioManager.getInstance();
	}

	/**
	 * This method set a new window based on the integer in input.
	 * @param idWindow number of the window to open.
	 */
	public void setWindow(int idWindow) {
		Window window = Manager.window;
		//Exit from the menu
		if (window.getMenuWindow() != null) 
			window.getContentPane().remove(window.getMenuWindow());
		
		//Exit from the account settings window
		if (window.getAccountWindow() != null) {
			window.getContentPane().remove(window.getAccountWindow());
		}
		
		//Exit from the game window
		if (window.getMazeWindow() != null) {
			window.getMazeWindow().stopManager();
			window.getContentPane().remove(window.getMazeWindow());
		}
		
		//Exit from the end game window.
		if (window.getEndWindow() != null) 
			window.getContentPane().remove(window.getEndWindow());	
		
		audioManager.stop();
		
		switch(idWindow) {
		//New Game
		case 0: Manager.game = new Game();
				Manager.bm = Manager.game.getBm();
				initMazeWindow(window);
				Manager.user.newGame();
				break;
		//Load Game
		case 1: Manager.game = Game.loadGame();
				Manager.bm = Manager.game.getBm();
				initMazeWindow(window);
				break;
		//Load Account Settings
		case 2: AccountManager accountManager = new AccountManager(window);
				AccountWindow accountWindow = accountManager.getWindow();
				window.setAccountWindow(accountWindow);
				window.getContentPane().add(accountWindow);
				playAudio("audio/menu-tone.wav");
				accountWindow.repaint();
				accountWindow.requestDefaultFocus();
				break;
		//Load Menu
		case 3: MenuWindow menuWindow = window.getMenuWindow();
				window.getContentPane().add(menuWindow);
				playAudio("audio/menu-tone.wav");
				menuWindow.repaint();
				menuWindow.requestDefaultFocus();
				break;
		//Load End Game Window after winning
		case 4: initEndWindow(window, true);
				break;
		//Load End Game Window after losing
		case 5: initEndWindow(window, false);
				break;
		default: return;
		}
		
		window.revalidate(); 
		window.repaint();
	}
	
	private void initMazeWindow(Window window) {
		MazeWindow mazeWindow = new MazeWindow(window);
		window.setMazeWindow(mazeWindow);
		window.getContentPane().add(mazeWindow);
		mazeWindow.repaint();
		mazeWindow.requestDefaultFocus();
		Manager.bm.addObserver(mazeWindow);
		Manager.game.addObserver(mazeWindow);
		playAudio("audio/maze-tone.wav");
	}
	
	private void initEndWindow(Window window, boolean won) {
		EndWindow endWindow = new EndWindow(won);
		window.getContentPane().add(endWindow);
		endWindow.repaint();
		endWindow.requestDefaultFocus();
		if (won)
			playAudio("audio/win-tone.wav");
		else
			playAudio("audio/lose-tone.wav");
	}
	
	public void playAudio(String audioName) {
		audioManager.play(audioName);
	}

	public void stopAudio() {
		audioManager.stop();
	}

	public void playSoundEffect(String audioName) {
		audioManager.playAudio(audioName);
	}
}
