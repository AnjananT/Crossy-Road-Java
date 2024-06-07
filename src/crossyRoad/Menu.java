/** 
 * Name: Anjanan Thirumahan
 * Submitted To: Ms. Kapustina
 * Course Code: ICS4U1
 * Date: January 8, 2022
 */
package crossyRoad;
import java.awt.event.*;
import java.awt.*;

import javax.sound.sampled.Clip;
import javax.swing.*;

//Class for main menu in which player can play game, see rules, or controls. 
//This class updates the frame to show the parts of the game
public class Menu extends JFrame implements ActionListener, ItemListener{

	private Game newGame;
	JFrame frame = new JFrame("Crossy Road"); //creating frame 
	JPanel contentPane; //creating contentPane

	//importing, resizing, and adding images to label

	//setting up main menu background label
	ImageIcon mainBackground = new ImageIcon ("mainMenu.png");
	Image img1 =mainBackground.getImage(); 
	Image newimg1 = img1.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH ) ; 
	ImageIcon mainpic = new ImageIcon( newimg1 );	
	JLabel mainlbl = new JLabel(mainpic);

	//setting up rules menu background label
	ImageIcon rulesBackground = new ImageIcon ("rulesMenu.png");
	Image img2 =rulesBackground.getImage(); 
	Image newimg2 = img2.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH ) ; 
	ImageIcon rulespic = new ImageIcon( newimg2 );	
	JLabel ruleslbl = new JLabel(rulespic);

	//setting up controls menu background label
	ImageIcon controlsBackground = new ImageIcon ("controlsMenu.png");
	Image img3 =controlsBackground.getImage(); 
	Image newimg3 = img3.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH ) ; 
	ImageIcon controlsBackgroundPic = new ImageIcon( newimg3 );	
	JLabel controlslbl = new JLabel(controlsBackgroundPic);

	String btnFile;//file names for audio files
	Clip clip;//audio clip
	public static SoundEffect btnSound;//sound effects

	/* Purpose of method: Menu frame constructor
	 * Accepts: none
	 * Returns: A new menu frame
	 */
	public Menu(){

		frame.setSize(616,638);//set frame size
		frame.setLayout(new FlowLayout());//flow layout
		frame.setResizable(false);//set non-resizable

		//set file name of button sound
		btnFile = "button.wav";
		btnSound = new SoundEffect();
		btnSound.setFile(btnFile);

		mainMenu(); //start main menu
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	/* Purpose of method: Set up main menu
	 * Accepts: None
	 * Returns: Window for main menu with buttons play, rules, control
	 */
	public void mainMenu(){
		frame.setSize(616,638);//setting frame size
		frame.setVisible(true); //setting frame visible

		contentPane = new JPanel(); //creating panel
		contentPane.setLayout(null);//setting panel layout null
		frame.setContentPane(contentPane);//setting content pane

		//PLAY button to start game
		JButton playBtn = new JButton("PLAY");
		playBtn.setBounds(225, 200, 150, 50);
		playBtn.setForeground(Color.BLACK);
		playBtn.setActionCommand("play");
		playBtn.addActionListener(this);	
		contentPane.add(playBtn); //add button to contentPane

		//RULES button to see rules menu
		JButton rulesBtn = new JButton("RULES");
		rulesBtn.setBounds(225, 310, 150, 50);
		rulesBtn.setForeground(Color.BLACK);
		rulesBtn.setActionCommand("rules");
		rulesBtn.addActionListener(this);
		contentPane.add(rulesBtn); //add button to contentPane

		//CONTROLS button to see controls menu
		JButton controlsBtn = new JButton("CONTROLS");
		controlsBtn.setBounds(225, 420, 150, 50);
		controlsBtn.setForeground(Color.BLACK);
		controlsBtn.setActionCommand("controls");
		controlsBtn.addActionListener(this);
		contentPane.add(controlsBtn); //add button to contentPane


		mainlbl.setBounds(0,0,600,600); //setting bounds of main menu background image label
		contentPane.add(mainlbl); //add background label to contentPane

	}
	/* Purpose of method: Set up rules menu
	 * Accepts: None
	 * Returns: Window with image for rules and back button to return to main menu
	 */
	public void rulesMenu(){
		frame.setSize(616,638);//setting frame size
		frame.setVisible(true); //setting frame visible

		contentPane = new JPanel(); //creating panel
		contentPane.setLayout(null);//setting panel layout null
		frame.setContentPane(contentPane);//setting content pane

		//BACK button to go back to main menu
		JButton backBtn = new JButton("BACK");
		backBtn.setBounds(20, 15, 150, 50);
		backBtn.setForeground(Color.BLACK);
		backBtn.setActionCommand("back");
		backBtn.addActionListener(this);	
		contentPane.add(backBtn);//add button to contentPane

		ruleslbl.setBounds(0,0,600,600); //setting bounds of rules background image label
		contentPane.add(ruleslbl);//add rules background label to contentPane

	}
	/* Purpose of method: Set up controls menu
	 * Accepts: None
	 * Returns: Window with image for controls and back button to return to main menu
	 */
	public void controlsMenu(){
		frame.setSize(616,638);//setting frame size
		frame.setVisible(true); //setting frame visible

		contentPane = new JPanel(); //creating panel
		contentPane.setLayout(null);//setting panel layout null
		frame.setContentPane(contentPane);//setting content pane

		//BACK button to go back to main menu
		JButton backBtn = new JButton("BACK");
		backBtn.setBounds(20, 15, 150, 50);
		backBtn.setForeground(Color.BLACK);
		backBtn.setActionCommand("back");
		backBtn.addActionListener(this);	
		contentPane.add(backBtn); 

		controlslbl.setBounds(0,0,600,600);//setting bounds of controls menu background image label
		contentPane.add(controlslbl);//add controls background label to contentPane
	}
	/* Purpose of method: Determines what happens if specific actions are performed
	 * Accepts: ActionEvent event.
	 * Returns: If an action has happened, its corresponding event will occur
	 */
	public void actionPerformed(ActionEvent event) {
		btnSound.play();//play button sound every time an action  happens in main menu (the only actions are buttons)
		String eventName = event.getActionCommand();//initialize String with action command of the event

		if(eventName.equals("rules")){//if the action command is "rules"
			frame.dispose(); //close current frame
			rulesMenu(); //open rules menu frame
		}
		if(eventName.equals("back")){//if the action command is "back"
			frame.dispose();//close current frame
			mainMenu();	 //open main menu frame
		}
		if(eventName.equals("play")){//if the action command is "play"
			frame.dispose();//close current frame
			newGame = new Game();//open new game frame
		}
		if(eventName.equals("controls")){//if the action command is "controls"
			frame.dispose();//close current frame
			controlsMenu();//open new controls menu frame
		}
	}
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
	}
}

