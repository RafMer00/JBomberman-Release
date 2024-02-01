package Control;

import javax.swing.ImageIcon;

import View.AccountWindow;
import View.Window;

/**
 * This class represents a manager for the account window.
 * @author Raf&Vinz
 *
 */
public class AccountManager {
	private AccountWindow accountWindow;

	/**
	 * This is the class builder of the manager.
	 * @param window where add the account window.
	 */
	public AccountManager(Window window) {
		accountWindow = new AccountWindow(window, this);
	}
	
	/**
	 * This method return the account window managed.
	 * @return the account window.
	 */
	public AccountWindow getWindow() {
		return accountWindow;
	}
	
	/**
	 * This method set the user's nickname equals to the string in input.
	 * @param newNickname the new nickname.
	 */
	public void setNickname(String newNickname) {
		Manager.user.setNickname(newNickname);
	}

	/**
	 * This method returns the avatar path image of the user. 
	 * @return avatar path image.
	 */
	public ImageIcon getAvatar() {
		return Manager.user.getAvatar();
	}

	/**
	 * This method returns the user's nickname.
	 * @return the user's nickname.
	 */
	public String getNickname() {
		return Manager.user.getNickname();
	}

	/**
	 * This method returns the number of played math.
	 * @return the number of played match.
	 */
	public int getTotGame() {
		return Manager.user.getTotGame();
	}

	/**
	 * This method returns the number of won math.
	 * @return the number of won match.
	 */
	public int getTotWonGame() {
		return Manager.user.getTotWonGame();
	}

	/**
	 * This method change the avatar image with a new one.
	 * @param newAvatarImage new avatar image.
	 */
	public void changeAvatar(ImageIcon newAvatarImage) {
		Manager.user.changeAvatar(newAvatarImage);
	}
	
}
