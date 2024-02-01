package Control;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Model.Direction;

/**
 * This class represents the controls for the menu window.
 * @author Raf&Vinz
 *
 */
public class MenuController extends Controller{
	
	@Override
	public void keyTyped(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP: Manager.menu.changeOption(Direction.UP); break;
		case KeyEvent.VK_DOWN: Manager.menu.changeOption(Direction.DOWN); break;
		case KeyEvent.VK_LEFT: Manager.menu.changeOption(Direction.LEFT); break;
		case KeyEvent.VK_RIGHT: Manager.menu.changeOption(Direction.RIGHT); break;
		case KeyEvent.VK_ENTER: Manager.window.getWindowManager().setWindow(Manager.menu.getPointedOption().getValue()); break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP: Manager.menu.changeOption(Direction.UP); break;
		case KeyEvent.VK_DOWN: Manager.menu.changeOption(Direction.DOWN); break;
		case KeyEvent.VK_LEFT: Manager.menu.changeOption(Direction.LEFT); break;
		case KeyEvent.VK_RIGHT: Manager.menu.changeOption(Direction.RIGHT); break;
		case KeyEvent.VK_ENTER: Manager.window.getWindowManager().setWindow(Manager.menu.getPointedOption().getValue()); break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Vediamo se fa le stesse cose o non fa niente
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// 
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}