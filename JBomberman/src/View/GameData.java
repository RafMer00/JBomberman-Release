package View;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class represents a bar with all the info about the game.
 * @author Raf&Vinz
 *
 */
public class GameData extends JPanel{

	private static final long serialVersionUID = 1L;
	private ArrayList<JPanel> squares;
	private JLabel bar;
	private JLabel lives;
	private JLabel score;
	private JLabel hand;
	private JPanel panel;

	/**
	 * This is the class builder which creates a bar with all the info about the game.
	 * @param panel where the bar data will be painted.
	 * @param time time of the game.
	 * @param nLives number of lives of Bomberman.
	 * @param points score of the current game.
	 */
	public GameData(JPanel panel, int time, int nLives, int points) {
		this.panel = panel;
		setBounds(0, 0, 800, 90);
		setLayout(null);
        squares = new ArrayList<>();
		paint(time, nLives, points);
	}
	
	/**
	 * This method paint the game bar based on time, number of lives and score.
	 * @param time time of the game.
	 * @param nLives number of lives of Bomberman.
	 * @param points score of the current game.
	 */
	public void paint(int time, int nLives, int points) {
		ImageIcon barImg = new ImageIcon("img/BarData/BarData.jpg");
		bar = new JLabel(barImg);
		bar.setBounds(0, 0, 800, 90);
		
		//Rotate the hand of the clock
        chooseHand(0);
		
        //Paint rectangles in the bar to show the passed time
        paintTime(time);
        
        //Number of lives remaining
		lives = new JLabel("" + nLives);
		lives.setBounds(73, 10, 25, 45);
		lives.setHorizontalAlignment(SwingConstants.CENTER);
		lives.setFont(Window.DATA);
		lives.setForeground(Color.WHITE);

		//Score of the game
		score = new JLabel("" + points);
		score.setBounds(155, 10, 200, 45);
		score.setHorizontalAlignment(SwingConstants.RIGHT);
		score.setFont(Window.DATA);
		score.setForeground(Color.WHITE);

		//Add components to the panel
        add(hand);
		add(score);
		add(lives);
		add(bar);
	}
	
	/**
	 * This method choose the right image to put the hand in the clock.
	 * @param time time remaining in the game.
	 */
	public void chooseHand(int time) {
		String URL = "img/BarData/lancetta" + time + ".png";
		ImageIcon handIcon = new ImageIcon(URL);
        if (hand == null)
        	hand = new JLabel(handIcon);
        else
        	hand.setIcon(handIcon);
        hand.setBounds(385, 29, 30, 30);

        repaint();
	}
	
	/**
	 * This method choose the right image to put the hand in the clock at the start of the game.
	 */
	public void chooseHand() {
		String URL = ((ImageIcon)hand.getIcon()).getDescription();
		int time = Integer.parseInt(""+URL.charAt(URL.length() - 5));
		chooseHand(++time%8);
	}
	
	
	/**
     * Paint rectangles in the bar to show the passed time.
	 * @param time time remaining in the game.
	 */
	public void paintTime(int time) {
		for (JPanel square: squares)
			remove(square);
		squares = new ArrayList<>();
		fillTime(time);
	}
	
	/**
	 * This method fill the rectangle that represents the time when the game start.
	 * @param time time remaining in the game.
	 */
	public void fillTime(int time) {		
		for (int j = 0; j < 14; j++) {
			if (time >= j*13) {
				JPanel timePanel = new JPanel();
				timePanel.setBackground(Color.white);
				timePanel.setBounds(28 + j*25, 65, 18, 14);
				squares.add(timePanel);
				add(timePanel);
				setComponentZOrder(timePanel, 0);
			}
		}
		
		for (int j = 0; j < 14; j++) {
			if (time >= (j + 14)*13) {
				JPanel timePanel = new JPanel();
				timePanel.setBackground(Color.white);
				timePanel.setBounds(429 + j*25, 65, 18, 14);
				squares.add(timePanel);
				add(timePanel);
				setComponentZOrder(timePanel, 0);
			}
		}
	}
	
	/**
	 * This method reload the bar with the new game info.
	 * @param nLives number of lives of Bomberman.
	 * @param points score of the current game.
	 */
	public void reload(int nLives, int points) {
		lives.setText("" + nLives);
		score.setText("" + points);
		panel.repaint();
	}
	
	
}
