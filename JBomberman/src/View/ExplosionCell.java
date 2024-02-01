package View;

import javax.swing.JPanel;

/**
 * This class represents a cell image with an explosion.
 * @author Raf&Vinz
 *
 */
public abstract class ExplosionCell extends Cell{

	private static final long serialVersionUID = 1L;
	
	/**
	 * This is the class builder which creates an explosion cell image saving where it will be painted as an attribute.
	 * @param panel where the cell will be painted.
	 * @param path image path.
	 * @param x coordinate x where the cell has to be painted.
	 * @param y coordinate x where the cell has to be painted.
	 */
	public ExplosionCell(JPanel panel, String path, int x, int y) {
		super(panel, path, x, y);
	}
	
	/**
	 * This method remove the explosion.
	 */
	public void explode(){
		getPanel().remove(this);
		getPanel().validate();
		getPanel().repaint();
	}

	/**
	 * This method animate the explosion in the cell.
	 * @return the new Explosion cell.
	 */
	public abstract ExplosionCell animate();

}
