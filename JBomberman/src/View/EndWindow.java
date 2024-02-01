package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Control.AudioManager;
import Control.JBomberman;
import Control.Manager;
import Control.WindowManager;

/**
 * This class represents the window to open at the end of the game.
 * @author Raf&Vinz
 *
 */
public class EndWindow extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel border;
	private JLabel homeButton;
	private JLabel wallpaperCont;
	
	/**
	 * This is the class builder which creates a window at the end of the game based on if the user won.
	 * @param win true if the user won the game.
	 */
	public EndWindow(boolean win) {
		setBounds(200, 0, 800, 800);
		setLayout(null);
		paint(win);
	}
	
	/**
	 * This method paint the component in the panel based on if the user won the game.
	 * @param win true if the user won the game.
	 */
	public void paint(boolean win) {	
        //Wallpaper
		ImageIcon wallpaper = new ImageIcon("img/DesignElements/SfondoWin.jpg");
		wallpaperCont = new JLabel(wallpaper);
		wallpaperCont.setBounds(0, 0, wallpaper.getIconWidth(), wallpaper.getIconHeight());
		
		//Result of the game
		ImageIcon resultImage = win ? new ImageIcon("img/DesignElements/Won.png") : new ImageIcon("img/DesignElements/Lost.png");
        border = new JLabel(resultImage);
        border.setBounds(175, 70, resultImage.getIconWidth(), resultImage.getIconHeight());
       		
		//Home button
		ImageIcon homeImage = new ImageIcon("img/DesignElements/DarkHome.png");
		homeButton = new JLabel(homeImage);
		homeButton.setBounds(220, 160, homeImage.getIconWidth(), homeImage.getIconHeight());
		homeButton.setBackground(Color.white);
		homeButton.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				Manager.window.getWindowManager().setWindow(Window.menu);
            }
			
			@Override
			public void mouseEntered(MouseEvent e) {
				homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		
		//Add components to the window
		add(homeButton);
		add(border);
		add(wallpaperCont);

	}
}
