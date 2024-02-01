package View;

import javax.swing.ImageIcon;

/**
 * This class represents a bomb image.
 * @author Raf&Vinz
 *
 */
public class Bomb extends Element{

	private static final long serialVersionUID = 1L;
	
	/**
	 * This is the class builder which creates a bomb image saving where it will be painted as an attribute.
	 * @param panel where the bomb will be painted
	 */
	public Bomb(MazeWindow panel) {
		super(panel);
	}
	
	@Override
	public void paint(int x, int y) {
		ImageIcon bombIcon = new ImageIcon("img/Bomb/bomb.png");
		setIcon(bombIcon);
        setBounds(x, y, bombIcon.getIconWidth(), bombIcon.getIconHeight());
        ((MazeWindow)panel).getWindow().getWindowManager().playSoundEffect("audio/bomb-sound.wav");
        panel.add(this);
	}
	
	/**
	 * This method remove the bomb image when it explodes in the game.
	 */
	public void explode(){
		getPanel().remove(this);
		getPanel().validate();
		getPanel().repaint();
	}
}
