package View;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents an element that can be painted on a window.
 * @author Raf&Vinz
 *
 */
public abstract class Element extends JLabel{
	private static final long serialVersionUID = 1L;
	protected JPanel panel;
	
	public Element(JPanel panel) {
		this.panel = panel;
	}
	
	/**
	 * This method returns the panel where the element is painted.
	 * @return the panel where the bomb is painted.
	 */
	public JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Paint the element in the panel at the specified position.
	 * @param x coordinate x where the bomb has to be painted.
	 * @param y coordinate x where the bomb has to be painted.
	 */
	public abstract void paint(int x, int y);

}
