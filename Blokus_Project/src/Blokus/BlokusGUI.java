package Blokus;
import javax.swing.*;
//import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * BlokusGUI - holds all GUI classes and methods that they use 
 *BlokusGUI class itself is meant to be a shelf for the interior class  
 *  
 * 
 * 
 * 
 */

public class BlokusGUI {

	static boolean colorBlind; // placed from colorblind use case
	private static class Blokus_Frame extends JFrame 
	{

		private static final long serialVersionUID = 1L;

		static final JPanel menuPanel = new JPanel(); // Holds menus including (mainMenu, settingsMenu , playMenu)
		static final MainMenu mainMenu = new MainMenu(); // creates only one of these  and reuses it 
		static final SettingsMenu settingsMenu = new SettingsMenu(); // creates only one of these  and reuses it 
		static final PlayMenu playMenu = new PlayMenu(); // creates only one of these  and reuses it 
		static final GameMenu gameMenu = new GameMenu(); // creates only one of these  and reuses it 
		public Blokus_Frame() //sets standard parameters for the window
		{
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			this.setSize(750,400);
			this.setLayout(new BorderLayout());
			this.setResizable(true);
			
			JLabel authorInfo = new JLabel("Group # 4 - Comp 2005, 2019  ", SwingConstants.RIGHT);
			this.add(authorInfo, BorderLayout.PAGE_END);

			menuPanel.setLayout(new CardLayout());
			menuPanel.add(mainMenu);
			menuPanel.add(settingsMenu);
			menuPanel.add(playMenu);
			menuPanel.add(gameMenu);

			this.add(menuPanel, BorderLayout.CENTER);
			this.setVisible(true);	
		}

		
		private abstract static class Menu extends JPanel // Base class which all menus are built from
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Menu() 
			{
				this.setLayout(new GridBagLayout());
			}

		}
		private static class MainMenu extends Menu
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = -7887556954455476971L;
			public MainMenu()
			{
				super();
				JLabel titleLabel = new JLabel("Welcome to Blokus");
				JButton playButton = new JButton("Play");
				playButton.addActionListener(new PlayButtonListener());
			
				JButton settingsButton = new JButton("Settings");
				settingsButton.addActionListener(new SettingButtonListener());
			
				JButton quitButton = new JButton("Quit");
				quitButton.addActionListener(new QuitButtonListener());


				ImageIcon image = new ImageIcon("images/BlokusIcon.png");
				Image resized = image.getImage().getScaledInstance(500, 256, Image.SCALE_SMOOTH);
				image = new ImageIcon(resized);
				
				
				
				JLabel icon = new JLabel();
				icon.setIcon(image);
				icon.setOpaque(true);

				GridBagConstraints c = new GridBagConstraints();

				c.gridy = 0;
				c.weighty = 0;
				c.gridwidth = 0;
				
				this.add(icon,c);
				this.add(titleLabel);
				this.add(playButton);
				this.add(settingsButton);
				this.add(quitButton);
				this.setVisible(true);
			}

			private class PlayButtonListener implements ActionListener 
			{
				public void actionPerformed(ActionEvent event) 
				{
					JButton source = (JButton) event.getSource();
					Blokus_Frame frame = (Blokus_Frame) source.getTopLevelAncestor();

					playMenu.setVisible(true);
					mainMenu.setVisible(false);
					frame.setVisible(true);				
				}
			}
	
			private class SettingButtonListener implements ActionListener  // opens the settings menu by setting visibility to false in other menus
			{
				public void actionPerformed(ActionEvent event) 
				{
					JButton source = (JButton) event.getSource();
					Blokus_Frame frame = (Blokus_Frame) source.getTopLevelAncestor();

					settingsMenu.setVisible(true);
					mainMenu.setVisible(false);
					frame.setVisible(true);
				}
			}
			private class QuitButtonListener implements ActionListener 
			{
				public void actionPerformed(ActionEvent event) 
				{
					 new QuitScreen();
				}
			}
		}

		
		private static class PlayMenu extends Menu
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = -7887556954455476971L;
			public PlayMenu()
			{
				super();
				JLabel titleLabel = new JLabel("Let's Play!");

				JButton newGameButton = new JButton("New Game");
				newGameButton.addActionListener(new NewGameListener());
			
				JButton loadButton = new JButton("Load Game");
				loadButton.addActionListener(new LoadGameListener());

				JButton backButton = new JButton("back");
				backButton.addActionListener(new backButtonListener());
			

				ImageIcon image =new ImageIcon("images/BlokusIcon.png");
				Image resized = image.getImage().getScaledInstance(500, 256, Image.SCALE_SMOOTH); // resizing could be done outside application
				image =new ImageIcon(resized);
								
				JLabel icon = new JLabel();
				icon.setIcon(image);
				icon.setOpaque(true);

				GridBagConstraints c = new GridBagConstraints(); // used to organize buttons under the image

				c.gridy = 0;
				c.weighty = 0;
				c.gridwidth = 0;
				
				this.add(icon,c);
				this.add(titleLabel);
				this.add(newGameButton);
				this.add(loadButton);
				this.add(backButton);
				this.setVisible(true);
			}

			private class NewGameListener implements ActionListener 
			{
				public void actionPerformed(ActionEvent event) 
				{
					gameMenu.newGame();

					getTopLevelAncestor().setSize(1000, 1000);
					gameMenu.setVisible(true);
					playMenu.setVisible(false);
				}
			}
		
			private class LoadGameListener implements ActionListener 
			{
				public void actionPerformed(ActionEvent event) 
				{
					//TODO Load game
				}
			}
		
			private class backButtonListener implements ActionListener 
			{
				public void actionPerformed(ActionEvent event) 
				{
					JButton source = (JButton) event.getSource();
					Blokus_Frame frame = (Blokus_Frame) source.getTopLevelAncestor();

					playMenu.setVisible(false);
					mainMenu.setVisible(true);
					frame.setVisible(true);				
				}
			}
		}
		
		private static class SettingsMenu extends Menu
		{
			/**
			 * Used to create  settingsMenu and holds all buttons and their listeners 
			 */
			private static final long serialVersionUID = 1092357066461006241L;

			public SettingsMenu()
			{
				super();
				JLabel titleLabel = new JLabel("Welcome to Blokus");
				this.add(titleLabel);
								
				JButton instructButton = new JButton("Instructions");
				instructButton.addActionListener(new InstructionButtonListener());

				JButton colorBlindButton = new JButton("Colorblind: Off");
				colorBlindButton.addActionListener(new ColorblindButtonListener());
				colorBlind = false;

				JButton backButton = new JButton("Back");
				backButton.addActionListener(new BackButtonListener());
				
				this.add(titleLabel);
				this.add(colorBlindButton);
				this.add(instructButton);
				this.add(backButton);
				this.setVisible(true);

			}
		
			class InstructionButtonListener implements ActionListener 
			{
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, 
						"\"RULES: Each player starts with 21 pieces. Turn order is: Blue, Yellow, Red, Green.\"\n" +
						"\"A player's piece bin will highlight to indicate current turn.\" \n" +
						"\"In the first round, players place their opening piece on a corner of the board.\" \n" +
						"\"Each new piece played must be placed so that it touches at least one piece of the same color.\"\n" +
						"\"Only corner-to-corner contact allowed, edges cannot touch.\"\n" +
						"\"A player may pass, if they have no valid moves, play continues.\"\n" +
						"\"Game ends when no player can place piece.\"\n" +
						"\"Score calculated by remaining pieces in players hands.\"\n" +
						"\"The player with the highest score wins!\""
					);
				}
			}

			class ColorblindButtonListener implements ActionListener 
			{
				public void actionPerformed(ActionEvent evnt) {
					colorBlind = !colorBlind;
					if (colorBlind) {
						((JButton) evnt.getSource()).setText("Colorblind: On");
					} else {
						((JButton) evnt.getSource()).setText("Colorblind: Off");
					}
				}
			}

			class BackButtonListener implements ActionListener 
			{
				public void actionPerformed(ActionEvent event) 
				{
					JButton source = (JButton) event.getSource();
					Blokus_Frame frame = (Blokus_Frame) source.getTopLevelAncestor();

					settingsMenu.setVisible(false);
					mainMenu.setVisible(true);
					frame.setVisible(true);
				}
			}		
		}
	}			
	
	/*
	 * QuitScreen 
	 * this class creates the pop up confirm menu for quiting the game it does not contain information on saving
	 */
	private static class QuitScreen
	{
		public QuitScreen()
		{
			int r = JOptionPane.showConfirmDialog(new JFrame(),"Are you sure you want to quit", "Quitting",JOptionPane.OK_CANCEL_OPTION);
			if(r == 0) {System.exit(0);}			
		}
	}
	
		
	public static void main(String[] args)
	{
		new Blokus_Frame();
	}
}

