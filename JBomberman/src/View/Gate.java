package View;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class represents the gate image.
 * @author Raf&Vinz
 *
 */
public class Gate extends Cell{

	private static final long serialVersionUID = 1L;
	private int timer;
	
	/**
	 * This is the class builder which creates the gate image saving where it will be painted as an attribute.
	 * @param panel where the gate will be painted.
	 * @param x coordinate x where the gate has to be painted.
	 * @param y coordinate x where the gate has to be painted.
	 */
	public Gate(JPanel panel, int x, int y) {
		super(panel, "img/PowerUp/Door0.png", x, y);
	}
	
	@Override
	public void paint(int x, int y) {
		ImageIcon puIcon = new ImageIcon(path);
		setIcon(puIcon);
        setBounds(x, y, puIcon.getIconWidth(), puIcon.getIconHeight());
        panel.add(this);
	}
	
	
	/**
	 * This method change the image of the gate simulating its movement when opened.
	 */
	public void animate() {
		timer = timer == 0 ? 1 : 0;
		path = path.substring(0, path.length() - 5) + timer + ".png";
		setIcon(new ImageIcon(path));
	}

}