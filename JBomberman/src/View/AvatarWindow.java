package View;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class represents a panel with the account image of the user
 * @author Raf&Vinz
 *
 */
public class AvatarWindow extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel wall;
	private JLabel info;
	private JLabel avatar1;
	private JLabel avatar2;
	private JLabel avatar3;
	private JLabel avatar4;
	private AccountWindow accountWindow;
	
	/**
	 * This is the class builder which creates a window with user's image
	 */
	public AvatarWindow(AccountWindow accountWindow) {
		this.accountWindow = accountWindow;
		setBounds(150, 175, 500, 205); 
		setLayout(null);	
		paint();
	}
	
	/**
	 * Add to the panel all the components of the window
	 */
	public void paint() {
		//Information for the section
		info = new JLabel("Select the avatar you want to use");
		info.setBounds(40, 5, 430, 30);
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setForeground(Color.WHITE);
		info.setFont(Window.SUBTITLE);
		
		//Background for the possible avatar image to choose
		ImageIcon wallpaper = new ImageIcon("img/Avatar/subAvatar.jpg");
		wall = new JLabel(wallpaper);
		wall.setBounds(0,  0, 500, 205);
		
		//First possible avatar image
		ImageIcon avatar1Image = new ImageIcon("img/Avatar/dog1.jpeg");
		avatar1 = new JLabel(avatar1Image);
		avatar1.setBounds(40,70,100,100);
		avatar1.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				accountWindow.getAccountManager().changeAvatar(avatar1Image);
				accountWindow.updateAvatar();
				setVisible(false);
            }
		});
		

		//Second possible avatar image
		ImageIcon avatar2Image = new ImageIcon("img/Avatar/west.jpeg");
		avatar2 = new JLabel(avatar2Image);
		avatar2.setBounds(150,70,100,100);
		avatar2.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				accountWindow.getAccountManager().changeAvatar(avatar2Image);
				accountWindow.updateAvatar();
				setVisible(false);
            }
		});
		

		//Third possible avatar image
		ImageIcon avatar3Image = new ImageIcon("img/Avatar/dog2.jpeg");
		avatar3 = new JLabel(avatar3Image);
		avatar3.setBounds(260,70,100,100);
		avatar3.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				accountWindow.getAccountManager().changeAvatar(avatar3Image);
				accountWindow.updateAvatar();
				setVisible(false);
            }
		});
		

		//Fourth possible avatar image
		ImageIcon avatar4Image = new ImageIcon("img/Avatar/moon.jpg");
		avatar4 = new JLabel(avatar4Image);
		avatar4.setBounds(370,70,100,100);
		avatar4.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				accountWindow.getAccountManager().changeAvatar(avatar4Image);
				accountWindow.updateAvatar();
				setVisible(false);
            }
		});
		
		
		//Add components to the panel
		add(avatar1);
		add(avatar2);
		add(avatar3);
		add(avatar4);
		add(info);
		add(wall);
	}
}
