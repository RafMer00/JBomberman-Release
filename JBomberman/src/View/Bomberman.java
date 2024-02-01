package View;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class represents the Bomberman image.
 * @author Raf&Vinz
 *
 */
public class Bomberman extends Element{

	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private String path;
	
	/**
	 * This is the class builder which creates Bomberman image saving where it will be painted as an attribute.
	 * @param panel where Bomberman will be painted.
	 * @param x coordinate x where Bomberman has to be painted.
	 * @param y coordinate x where Bomberman has to be painted.
	 */
	public Bomberman(JPanel panel, int x, int y, String path) {
		super(panel);
		this.x = x;
		this.y = y;
		this.path = path;
		paint(x, y);
		panel.add(this);
	}
	
	@Override
	public void paint(int x, int y) {
		ImageIcon bomberManIcon = new ImageIcon(path);
		setIcon(bomberManIcon);
		this.x = x;
		this.y = y;
        setBounds(x, y, bomberManIcon.getIconWidth(), bomberManIcon.getIconHeight());
        panel.repaint();
	}
	
	/**
	 * This method change Bomberman image simulating his death.
	 * @return the path of the image.
	 */
	public String die() {
		if (path.equals("img/Bomberman/dead1.png")) 
			path = "img/Bomberman/dead2.png";
		else if (!path.equals("img/Bomberman/dead2.png")) {
			path = "img/Bomberman/dead1.png";
		}
		ImageIcon bomberManIcon = new ImageIcon(path);
		setIcon(bomberManIcon);
	    setBounds(x, y, bomberManIcon.getIconWidth(), bomberManIcon.getIconHeight());
	    panel.revalidate();
	    panel.repaint();
	    return path;
	}
	
	/**
	 * This method returns the path of Bomberman image.
	 * @return the path of Bomberman image.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * This method sets the path of Bomberman image.
	 * @param path the new path for Bomberman image.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * This method repaint Bomberman in the same position.
	 */
	public void rePaint() {
		paint(x, y);
	}

}
