package View;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class represents a shoot from the clown boss.
 * @author Raf&Vinz
 *
 */
public class Shoot extends Element{
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int i;
	
	/**
	 * This is the class builder which creates a shoot image saving where it will be painted as an attribute.
	 * @param x coordinate x where the shoot has to be painted.
	 * @param y coordinate x where the shoot has to be painted.
	 * @param panel where the shoot will be painted.
	 */
	public Shoot(int x, int y, JPanel panel) {
		super(panel);
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void paint(int x, int y) {
		i = (i + 1)%4;
		ImageIcon shootIcon = new ImageIcon("img/Enemies/Boss/shoot" + i + ".png");
		this.x = x;
		this.y = y;
		setIcon(shootIcon);
        setBounds(x, y, shootIcon.getIconWidth(), shootIcon.getIconHeight());
        panel.add(this);
	}
}
