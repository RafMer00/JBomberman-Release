package Model;

import java.util.Random;
import javax.swing.ImageIcon;

/**
 * User represents a real User.
 * @author Raf&Vinz
 *
 */
public class User {
	private static User instance;
	private ImageIcon avatar;
	private String nickname;
	private int score;
	private int totGame;		//Number of played games 
	private int totwongame;		//Number of won games
	
	/**
	 * This method is used to implement Singleton pattern. It will
	 * create an instance of audio manager if it doesn't exists and then will return it.
	 * @return the single instance of AudioManager
	 */
    public static User getInstance() {	
       if(instance == null)
    	   instance = new User();
       return instance;
    }
	
    /**
     * User's builder.
     */
	private User() {
		nickname = "User";
		Random r = new Random();
		for (int i = 0; i < 8; i++) 
			nickname += r.nextInt(10);
		
		avatar = new ImageIcon("img/Avatar/User.jpg");
	}
	
	/**
	 * Nickname getter.
	 * @return user's nickname.
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Nickname setter.
	 * @param nickname new User's nickname.
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	/**
	 * User's score getter. 
	 * @return user's score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * User's score setter.
	 * @param score new user's score. 
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * User's totalgame getter.
	 * @return user's total played game.
	 */
	public int getTotGame() {
		return totGame;
	}
	
	/**
	 * User's total game setter.
	 * @param totGame total played game. 
	 */
	public void setTotGame(int totGame) {
		this.totGame = totGame;
	}
	
	/**
	 * User's total won game.
	 * @return total won game.
	 */
	public int getTotWonGame() {
		return totwongame;
	}
	
	/**
	 * User's total won games setter.
	 * @param totwongame the number of won game.
	 */
	public void setTotwongame(int totwongame) {
		this.totwongame = totwongame;
	}
	
	/**
	 * User's avatar getter.
	 * @return image of the user's avatar.
	 */
	public ImageIcon getAvatar() {
		return avatar;
	}
	
	/**
	 * This method changes the user's avatar image.
	 * @param address the new Image's path.
	 */
	public void changeAvatar(String address) {
		avatar = new ImageIcon(address);
	}
	
	/**
	 * This method allow the user to select an avatar Image.
	 * @param avatar new user's avatar.
	 */
	public void changeAvatar(ImageIcon avatar) {
		this.avatar = avatar;
	}
	
	/**
	 * This method reset the user's parameter.
	 */
	public void reset() {
		totGame = 0;
		totwongame = 0;
		score = 0;
	}
	
	/**
	 * This method increase the user's tot won game.
	 */
	public void newGame() {
		totGame++;
	}
	
	/**
	 * This method increase the tot won game and add the newest score to the user's account.
	 * @param game is the game.
	 */
	public void gameWon(Game game) {
		totwongame++;
		score += game.getScore();
	}
}
