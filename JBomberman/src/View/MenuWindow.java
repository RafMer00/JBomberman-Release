package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

import Control.Manager;
import Control.MenuController;

/**
 * This class represents the starting window.
 * @author Raf&Vinz
 *
 */
public class MenuWindow extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private int option = 0;
	private JLabel wallpaper;
	private JLabel logoPanel;
	private JLabel arrow;
	private JLabel option1;
	private JLabel option2;
	private JLabel option3;
	private JLabel baloon;
	private JLabel airship;
	private MenuController menuController = new MenuController();
	
	public Timer timer = new Timer(50, new ActionListener() {
		private int dSeconds = 0;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dSeconds = ++dSeconds % 800;
			
			baloon.setBounds(-100 + dSeconds, 5, 125, 185);
			airship.setBounds(750 - dSeconds, 90, 305, 130);
			repaint();
		}
	});
	
	/**
	 * This is the class builder.
	 */
	public MenuWindow() {
		setBounds(200, 0, 800, 800);
		setLayout(null);
		paint();
		timer.start();
	}
	
	/**
	 * This method adds all the components to the menu.
	 */
	public void paint() {
		//Add controller
		addKeyListener(menuController);
		setFocusable(true);
		requestFocus();
		
		//Add wallpaper
		ImageIcon wallpaperImg = new ImageIcon("img/DesignElements/SfondoMenuPatina.jpg");
		wallpaper = new JLabel(wallpaperImg);
		wallpaper.setBounds(0, 0, wallpaperImg.getIconWidth(), wallpaperImg.getIconHeight());
		
		//Add logo
		ImageIcon logo = new ImageIcon("img/Logo.png");
		logoPanel = new JLabel(logo);
		logoPanel.setBounds(100, 40, logo.getIconWidth(), logo.getIconHeight());
		
		//Add arrow that show menu option
		ImageIcon arrowImg = new ImageIcon("img/DesignElements/Arrow.png");
		arrow = new JLabel(arrowImg);
		arrow.setBounds(200, 430 + 60*option, arrowImg.getIconWidth(), arrowImg.getIconHeight());

		//New Game
		ImageIcon opt1 = new ImageIcon("img/DesignElements/NewGame.png");
		option1 = new JLabel(opt1);
		option1.setBounds(260, 430, opt1.getIconWidth(), opt1.getIconHeight());
		
		//Load Game
		ImageIcon opt2 = new ImageIcon("img/DesignElements/LoadGame.png");
		option2 = new JLabel(opt2);
		option2.setBounds(260, 490, opt2.getIconWidth(), opt2.getIconHeight());
		
		//Account settings
		ImageIcon opt3 = new ImageIcon("img/DesignElements/Account.png");
		option3 = new JLabel(opt3);
		option3.setBounds(260, 550, opt3.getIconWidth(), opt3.getIconHeight());
		
		//Add design objects
		ImageIcon mong = new ImageIcon("img/DesignElements/Mongolfiera.png");
		baloon = new JLabel(mong);
		baloon.setBounds(-100, 5, mong.getIconWidth(), mong.getIconHeight());
		
		ImageIcon pall = new ImageIcon("img/DesignElements/Pallone.png");
		airship = new JLabel(pall);
		airship.setBounds(750, 90, pall.getIconWidth(), pall.getIconHeight());
		
		//Add all the components
		add(logoPanel);
		add(baloon);
		add(airship);
		add(arrow);
		add(option1);
		add(option2);
		add(option3);		
		add(wallpaper);

	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		arrow.setBounds(200, 430 + 60*option, 50,50);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg != null) {
			option = (int)arg;
			repaint();
		}
	}	
	
	/**
	 * This method remove the game controller.
	 */
	public void removeKeyListener() {
		removeKeyListener(menuController);
	}
}
