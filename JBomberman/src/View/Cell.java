package View;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class represents a cell image.
 * @author Raf&Vinz
 *
 */
public abstract class Cell extends Element{

	private static final long serialVersionUID = 1L;
	protected String path;
	
	/**
	 * This is the class builder which creates a cell image saving where it will be painted as an attribute.
	 * @param panel where the cell will be painted.
	 * @param path image path.
	 * @param x coordinate x where the cell has to be painted.
	 * @param y coordinate x where the cell has to be painted.
	 */
	public Cell(JPanel panel, String path, int x, int y) {
		super(panel);
		this.path = path;
		paint(x, y);
	}
	
	@Override
	public void paint(int x, int y) {
		ImageIcon cellIcon = new ImageIcon(path);
		setIcon(cellIcon);
        setBounds(x, y, cellIcon.getIconWidth(), cellIcon.getIconHeight());
        panel.add(this);
	}	
}
