package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Control.AccountManager;

/**
 * This class represents the window where the user can edit its name or image and see its game stats.
 * @author Raf&Vinz
 *
 */
public class AccountWindow extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel wallpaperCont;
	private JLabel border;
	private JLabel accountImage;
	private JLabel homeButton;
	private JLabel blocknickname;
	private JTextField nicknamechanged;
	private JLabel totgametext;
	private JLabel wongametext;
	private JLabel ratioLabel;
	private JButton edit;
	private AvatarWindow avatarwindow;
	private Window window;
	private AccountManager accountManager;

	/**
	 * This is the class builder which creates a window with all user info.
	 * @param accountManager 
	 */
	public AccountWindow(Window window, AccountManager accountManager) {
		this.window = window;
		this.accountManager = accountManager;
		setBounds(200, 0, 800, 800);
		setLayout(null);
		paint();
	}
	
	/**
	 * Add to the panel all the components of the window.
	 */
	public void paint() {	
        //Wallpaper
		ImageIcon wallpaper = new ImageIcon("img/DesignElements/SfondoMenu.jpg");
		wallpaperCont = new JLabel(wallpaper);
		wallpaperCont.setBounds(0, 0, wallpaper.getIconWidth(), wallpaper.getIconHeight());

		//Frame for the window
        ImageIcon borderImage = new ImageIcon("img/DesignElements/AccountFrame.png");
        border = new JLabel(borderImage);
        border.setBounds(175, 70, borderImage.getIconWidth(), borderImage.getIconHeight());
       		
		//Home button 
		ImageIcon homeImage = new ImageIcon("img/DesignElements/Home.png");
		homeButton = new JLabel(homeImage);
		homeButton.setBounds(220, 160, homeImage.getIconWidth(), homeImage.getIconHeight());
		homeButton.setBackground(new Color(0,0,0,0));
		homeButton.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				window.setWindow(Window.menu);
            }			
		});
		
		//Window to change the avatar image
		avatarwindow = new AvatarWindow(this);
		add(avatarwindow);
		avatarwindow.setVisible(false);
		
		//Avatar image
		accountImage = new JLabel(accountManager.getAvatar());
		accountImage.setBounds(336, 181, 128, 128);
		accountImage.addMouseListener(new MouseAdapter() {

			@Override
            public void mouseClicked(MouseEvent e) {
				avatarwindow.setVisible(true);
            }
			
			@Override
			public void mouseEntered(MouseEvent e) {
				accountImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

		});

		
		//Nickname
		blocknickname = new JLabel(accountManager.getNickname());
		blocknickname.setBounds(230, 315, 340, 40);
		blocknickname.setHorizontalAlignment(SwingConstants.CENTER);
		blocknickname.setFont(Window.TITLE);
		
		//Section to change nickname
		nicknamechanged = new JTextField(16);
		nicknamechanged.setBounds(250, 315, 300, 40);
		nicknamechanged.setFont(Window.TITLE);
		nicknamechanged.setVisible(false);
		nicknamechanged.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	//Quando premo invio eseguo le seguenti istruzioni
		        accountManager.setNickname(nicknamechanged.getText());
		        blocknickname.setText(nicknamechanged.getText());
		        nicknamechanged.setVisible(false);
			}
		});
		
		
		//User stats
		int totGame = accountManager.getTotGame();
		int wonGame = accountManager.getTotWonGame();
		int ratio = totGame == 0 ? 0 : wonGame/totGame*100;
		
		totgametext = new JLabel("Total Game: " + totGame);
		totgametext.setFont(Window.SUBTITLE);
		totgametext.setBounds(230, 365, 340, 30);
				
		wongametext = new JLabel("Total Won Game: " + wonGame);
		wongametext.setFont(Window.SUBTITLE);
		wongametext.setBounds(230, 395, 340, 30);
		
		ratioLabel = new JLabel("Win percentage: " + ratio + "%");
		ratioLabel.setFont(Window.SUBTITLE);
		ratioLabel.setBounds(230, 425, 340, 30);
		
		//Edit button
		edit = new JButton("Edit");
		edit.setFont(Window.SUBTITLE);
		edit.setBounds(300, 480, 200, 50);
		edit.setBackground(new Color(41, 171, 226));
		edit.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
                nicknamechanged.setVisible(!nicknamechanged.isVisible());
            }
		});
		
		//Add components to the window
		add(edit);
		add(homeButton);
		add(accountImage);
		
		add(nicknamechanged);
		add(blocknickname);
		
		add(totgametext);
		add(wongametext);
		add(ratioLabel);
		
		add(border);
		
		add(wallpaperCont);

	}

	/**
	 * This method update the account image in the window after it has been modified.
	 */
	public void updateAvatar() {
		accountImage.setIcon(accountManager.getAvatar());
		super.repaint();
	}

	/**
	 * This method returns the manager for the window.
	 * @return the manager for the window.
	 */
	public AccountManager getAccountManager() {
		return accountManager;
	}
	
	
}
