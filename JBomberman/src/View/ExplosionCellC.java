package View;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Control.Manager;

/**
 * This class represents the central cell image where the bomb explode
 * @author Raf&Vinz
 *
 */
public class ExplosionCellC extends ExplosionCell{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * This is the class builder which creates an explosion cell image saving where it will be painted as an attribute.
	 * @param panel where the cell will be painted.
	 * @param x coordinate x where the cell has to be painted.
	 * @param y coordinate x where the cell has to be painted.
	 */
	public ExplosionCellC(JPanel panel, int x, int y) {
		super(panel, "img/Exp/expc.png", x, y);
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
		return this;
	}
}
