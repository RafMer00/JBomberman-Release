package View;

import javax.swing.JPanel;

/**
 * This class represents a cell image that can't be destroyed by Bomberman
 * @author Raf&Vinz
 *
 */
public class IndestructibleCell extends Cell{

	private static final long serialVersionUID = 1L;
	
	/**
	 * This is the class builder which creates a indestructible cell image saving where it will be painted as an attribute.
	 * @param panel where the cell will be painted.
	 * @param path path of the image.
	 * @param x coordinate x where the cell has to be painted.
	 * @param y coordinate x where the cell has to be painted.
	 */
	public IndestructibleCell(JPanel panel, String path, int x, int y) {
		super(panel, path + "/wall.png", x, y);
	}
}
