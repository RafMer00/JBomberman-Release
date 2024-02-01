package View;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Control.Manager;

/**
 * This class represents a cell image that is on the bottom or top of where the bomb explode
 * @author Raf&Vinz
 *
 */
public class ExplosionCellV extends ExplosionCell{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * This is the class builder which creates an explosion cell image saving where it will be painted as an attribute.
	 * @param panel where the cell will be painted.
	 * @param x coordinate x where the cell has to be painted.
	 * @param y coordinate x where the cell has to be painted.
	 */
	public ExplosionCellV(JPanel panel, int x, int y) {
		super(panel, "img/Exp/expv0.png", x, y);
	}
	
	@Override
	public void paint(int x, int y) {
		setIcon(new ImageIcon(path));
        setBounds(x, y, 54, 54);
        Manager.window.getWindowManager().playSoundEffect("audio/explosion-sound.wav");
		panel.add(this);
	}
	
	@Override
	public ExplosionCell animate() {
		path = path.equals("img/Exp/expv0.png") ? "img/Exp/expv1.png" : "img/Exp/expv0.png";
		setIcon(new ImageIcon(path));
		return this;
	}

}
