package View;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class represents an enemy image.
 * @author Raf&Vinz
 *
 */
public class Enemy extends Element{

	private static final long serialVersionUID = 1L;
	protected String path;
	protected int timer;
	
	/**
	 * This is the class builder which creates a enemy image saving where it will be painted as an attribute.
	 * @param panel where the enemy will be painted.
	 * @param x coordinate x where the enemy has to be painted.
	 * @param y coordinate x where the enemy has to be painted.
	 * @param name name of the enemy model class.
	 */
	public Enemy(JPanel panel, int x, int y, String name) {
		super(panel);
		path = "img/Enemies/" + name + "/Right/0.png";
		paint(x, y);
        panel.add(this);
	}
	
	@Override
	public void paint(int x, int y) {
		ImageIcon enemyIcon = new ImageIcon(path);
		setIcon(enemyIcon);
        setBounds(x, y - enemyIcon.getIconHeight() , enemyIcon.getIconWidth(), enemyIcon.getIconHeight());
        panel.repaint();
	}
	
	/**
	 * This method set the path image of the enemy.
	 * @param path the path of the image.
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * This method increment a timer which will define what image painting.
	 * @return the timer value.
	 */
	public int incrTimer() {
		timer++;
		timer %= 6;
		return timer;
	}
	
	/**
	 * This method get the path of the enemy image.
	 * @return the path of the enemy image.
	 */
	public String getPath() {
		return path;
	}
}
