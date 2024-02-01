package View;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents the border of the maze.
 * @author Raf&Vinz
 *
 */
public class Border extends JLabel{

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private int world;
	
	/**
	 * This is the class builder which creates a border image saving where it will be painted as an attribute.
	 * @param panel where the bomb will be painted.
	 */
	public Border(JPanel panel, int world) {
		this.panel = panel;
		this.world = world;
		paint();
	}
	
	/**
	 * This method returns the panel where the bomb is painted.
	 * @return the panel where the bomb is painted.
	 */
	public JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Paint the border in the panel.
	 */
	public void paint() {
		ImageIcon borderIcon = new ImageIcon("img/World" + world + "/border.png");
		setIcon(borderIcon);
        setBounds(0, 77, 800, 710);        
        panel.add(this);
	}
}
