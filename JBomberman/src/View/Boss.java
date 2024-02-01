package View;


import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Control.Manager;

/**
 * This class represents a boss image.
 * @author Raf&Vinz
 *
 */
public class Boss extends Enemy{

	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	
	/**
	 * This is the class builder which creates a boss image saving where it will be painted as an attribute.
	 * @param panel where the boss will be painted.
	 * @param x coordinate x where the boss has to be painted.
	 * @param y coordinate x where the boss has to be painted.
	 * @param name
	 */
	public Boss(JPanel panel, int x, int y, String name) {
		super(panel, x, y, name);
		path = "img/Enemies/Boss/" + name + "/0.png";
		paint(x, y);
		System.out.println(name);
		if (name.equals("BigBomb"))
			Manager.window.getWindowManager().playSoundEffect("audio/bigbomb-sound.wav");
		else
			Manager.window.getWindowManager().playSoundEffect("audio/clown-sound.wav");
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
	 * This method change the boss image to simulate his death.
	 * @return the path of the image.
	 */
	public String die() {
		path = path.substring(0, path.lastIndexOf('/') + 1) + "Dead.png";
		ImageIcon bomberManIcon = new ImageIcon(path);
		setIcon(bomberManIcon);
	    setBounds(x, y, bomberManIcon.getIconWidth(), bomberManIcon.getIconHeight());
	    panel.revalidate();
	    panel.repaint();
	    return path;
	}
}
