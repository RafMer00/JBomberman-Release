package View;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class represents a cell image that can be destroyed by Bomberman
 * @author Raf&Vinz
 *
 */
public class DestructibleCell extends Cell{


	private static final long serialVersionUID = 1L;
	private int timer;
	
	/**
	 * This is the class builder which creates a destructible cell image saving where it will be painted as an attribute.
	 * @param panel where the cell will be painted.
	 * @param path path of the image.
	 * @param x coordinate x where the cell has to be painted.
	 * @param y coordinate x where the cell has to be painted.
	 */
	public DestructibleCell(JPanel panel, String path, int x, int y) {
		super(panel, path + "/dc1.png", x, y);
	}
	
	/**
	 * This method animate the breakable cell.
	 */
	public void animate() {
		timer = ++timer % 4;
		setIcon(new ImageIcon(path.substring(0, path.length() - 5) + timer + ".png"));
		repaint();
	}
}
