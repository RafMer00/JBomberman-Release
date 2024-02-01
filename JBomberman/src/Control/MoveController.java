package Control;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Model.Direction;
import Model.Position;
import View.MazeWindow;

/**
 * This class represents the controls for the maze window.
 * @author Raf&Vinz
 *
 */
public class MoveController extends Controller{
	private boolean paused;
	
	/**
	 * This method return true if the controls are in pause.
	 * @return the status of the controls.
	 */
	public boolean isPaused() {
		return paused;
	}
	
	/**
	 * This method set the control status of the control.
	 * @param paused new status of the controls.
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if (!paused)
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP: Manager.bm.updatePosition(Direction.UP, Manager.game); break;
			case KeyEvent.VK_DOWN: Manager.bm.updatePosition(Direction.DOWN, Manager.game); break;
			case KeyEvent.VK_LEFT: Manager.bm.updatePosition(Direction.LEFT, Manager.game); break;
			case KeyEvent.VK_RIGHT: Manager.bm.updatePosition(Direction.RIGHT, Manager.game); break;
			case KeyEvent.VK_W: Manager.bm.updatePosition(Direction.UP, Manager.game); break;
			case KeyEvent.VK_S: Manager.bm.updatePosition(Direction.DOWN, Manager.game); break;
			case KeyEvent.VK_A: Manager.bm.updatePosition(Direction.LEFT, Manager.game); break;
			case KeyEvent.VK_D: Manager.bm.updatePosition(Direction.RIGHT, Manager.game); break;
			case KeyEvent.VK_SPACE: Manager.game.placeBomb(); break;
			case KeyEvent.VK_ENTER: Manager.window.getMazeWindow().getMazeManager().pause(); break;
			case KeyEvent.VK_1: Manager.window.getMazeWindow().getMazeManager().cheatCode(); break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!paused)
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP: Manager.bm.updatePosition(Direction.UP, Manager.game); break;
			case KeyEvent.VK_DOWN: Manager.bm.updatePosition(Direction.DOWN, Manager.game); break;
			case KeyEvent.VK_LEFT: Manager.bm.updatePosition(Direction.LEFT, Manager.game); break;
			case KeyEvent.VK_RIGHT: Manager.bm.updatePosition(Direction.RIGHT, Manager.game); break;
			case KeyEvent.VK_W: Manager.bm.updatePosition(Direction.UP, Manager.game); break;
			case KeyEvent.VK_S: Manager.bm.updatePosition(Direction.DOWN, Manager.game); break;
			case KeyEvent.VK_A: Manager.bm.updatePosition(Direction.LEFT, Manager.game); break;
			case KeyEvent.VK_D: Manager.bm.updatePosition(Direction.RIGHT, Manager.game); break;
			case KeyEvent.VK_SPACE: Manager.game.placeBomb(); break;
			case KeyEvent.VK_ENTER: Manager.window.getMazeWindow().getMazeManager().pause(); break;
			case KeyEvent.VK_1: Manager.window.getMazeWindow().getMazeManager().cheatCode(); break;

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Vediamo se fa le stesse cose o non fa niente
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int y = e.getY();
			int x = e.getX();
			Position pos = new Position(MazeManager.convertVwMap(MazeWindow.yMargin, y, 6), 
										MazeManager.convertVwMap(MazeWindow.xMargin, x, 6));
			Manager.window.getMazeWindow().getMazeManager().moveToPosition(pos);
		}
		else if (e.getButton() == MouseEvent.BUTTON3) {
			Manager.game.placeBomb();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}