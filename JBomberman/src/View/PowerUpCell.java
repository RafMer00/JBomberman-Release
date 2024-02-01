package View;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class represents a powerup image.
 * @author Raf&Vinz
 *
 */
public class PowerUpCell extends Cell{


	private static final long serialVersionUID = 1L;
	private int timer;
	
	/**
	 * This is the class builder which creates a powerup image saving where it will be painted as an attribute.
	 * @param panel where the powerup will be painted.
	 * @param x coordinate x where the powerup has to be painted.
	 * @param y coordinate x where the powerup has to be painted.
	 */
	public PowerUpCell(JPanel panel, String path, int x, int y) {
		super(panel, "img/PowerUp/" + path + "0.png", x, y);
		paint(x, y);
	}
	
	@Override
	public void paint(int x, int y) {
		ImageIcon puIcon = new ImageIcon(path);
		setIcon(puIcon);
        setBounds(x, y, puIcon.getIconWidth(), puIcon.getIconHeight());
        panel.add(this);
	}

	/**
	 * This method change the image of the powerup.
	 */
	public void animate() {
		timer = timer == 0 ? 1 : 0;
		path = path.substring(0, path.length() - 5) + timer + ".png";
		setIcon(new ImageIcon(path));
	}

}
