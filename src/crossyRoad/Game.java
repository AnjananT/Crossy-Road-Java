/** 
 * Name: Anjanan Thirumahan
 * Submitted To: Ms. Kapustina
 * Course Code: ICS4U1
 * Date: January 17, 2022
 */
package crossyRoad;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener, ActionListener {	

	JFrame frame; //frame for the game

	//Images
	Image background, border; //images for background 
	Image chicken, playerUp, playerDown, playerLeft, playerRight; //images for the player controlled sprite
	Image car1, car2, car3, car4, carRight, carLeft; //images for the cars
	Image[] coin = new Image[10]; //image array for coins

	//int variables
	int chickenX, chickenY; //player sprite coordinates
	int car1X, car1Y, car1Speed, car2X, car2Y, car2Speed, car3X, car3Y, car3Speed, car4X, car4Y, car4Speed; //car coordinates and speed variables, car1 is in lane 1, car2 in lane 2 etc...
	int random; //integer to hold random number produced by Random 
	int scoreCount; //integer to keep track of player score
	int coinCount; //integer to keep track of coins collected by the player
	int livesCount; //integer to keep track of lives left for the player
	int[] coinX = new int[10]; //int array for the coin X coordinates
	int[] coinY = new int[10];//int array for the coin Y coordinates

	//Rectangle2D
	Rectangle2D.Double chickenBox; //2D Rectangle object that'll move according to the player's coordinates for collisions
	Rectangle2D.Double car1Box, car2Box, car3Box, car4Box; //2D rectangle objects that'll move according to their corresponding car coordinates for collisions with the player
	ArrayList<Rectangle2D> coinBox; //ArrayList of Rectangle2D objects for the coins to detect collisions
	
	//Misc
	Font newFont; //new font for text that will be displayed on the screen
	Color coinCountColor; //color for coin count text
	Color livesCountColor;//color for lives count text
	Color scoreCountColor; //color for score count text
	Timer t; //timer for ActionListener
	Random rndm;//random for the car speed values
	String deathFile, coinFile, crossFile, moveFile;//file names for audio files
	Clip clip;//audio clip
	SoundEffect deathSound, coinSound, crossSound, moveSound;//sound effects
	private boolean coinIntersect;//boolean for when player intersects with a coin

	public Game(){
		//setting up JFrame for the game
		frame = new JFrame("Crossy Road");
		frame.setSize(616, 638);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setResizable(false);

		//Toolkit to acquire images
		Toolkit kit = Toolkit.getDefaultToolkit();
		playerUp = kit.getImage("playerUp.png"); 
		playerDown = kit.getImage("playerDown.png");
		playerRight = kit.getImage("playerRight.png");
		playerLeft = kit.getImage("playerLeft.png");
		Image img = kit.getImage("gameMap.png");
		Image img2 = kit.getImage("border.png");
		Image img3 = kit.getImage("coin.png");
		background = img.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH ) ; 
		border = img2.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH );
		carLeft = kit.getImage("carLeft.png");
		carRight = kit.getImage("carRight.png");	
		chicken = playerUp;//setting default image for player

		chickenX = 300; //initial X coordinate of player
		chickenY = 500;//initial Y coordinate of player

		car1X = 600;//initial X coordinate of car1
		car1Y = 420;//initial Y coordinate of car1
		car1Speed = 5; //initial speed of car1
		car1 = carLeft;//setting image of car1 to carLeft

		car2X = -150;//initial X coordinate of car2
		car2Y = 290;//initial Y coordinate of car2
		car2Speed = 6; //initial speed of car2
		car2 = carRight;//setting image of car2 to carRight

		car3X = 600;//initial X coordinate of car3
		car3Y = 175;//initial Y coordinate of car3
		car3Speed = 7; //initial speed of car3
		car3 = carLeft;//setting image of car3 to carLeft

		car4X = -150;//initial X coordinate of car4
		car4Y = 75;//initial Y coordinate of car4
		car4Speed = 10; //initial speed of car4
		car4 = carRight;//setting image of car4 to carRight

		scoreCount = 0; //initial value of player score
		coinCount = 0;//initial value of coins collected count
		livesCount = 3;//initial value of player lives, player starts with 3 lives

		newFont = new Font("Atomic", Font.BOLD, 60); //setting newFont to desired font specifications to display text
		coinCountColor = new Color(248,231,77,255);//(yellow-gold) color for coin count text 
		livesCountColor = new Color(199, 70, 70);//(red) color for lives count text
		scoreCountColor = new Color(17, 103, 177);//(darkish blue) for score count text

		chickenBox = new Rectangle2D.Double(chickenX, chickenY, 50, 50);//initializing Rectangle2D object with coordinates of player for collisions
		car1Box = new Rectangle2D.Double(car1X+27, car1Y+15, 45, 25); //initializing Rectangle2D object with coordinates of car1 for collisions, the +27 and +15 are slight modifications to fit more accurately
		car2Box = new Rectangle2D.Double(car2X+27, car2Y+15, 45, 25); //initializing Rectangle2D object with coordinates of car2 for collisions, the +27 and +15 are slight modifications to fit more accurately
		car3Box = new Rectangle2D.Double(car3X+27, car3Y+15, 45, 25); //initializing Rectangle2D object with coordinates of car3 for collisions, the +27 and +15 are slight modifications to fit more accurately
		car4Box = new Rectangle2D.Double(car4X+27, car4Y+15, 45, 25); //initializing Rectangle2D object with coordinates of car4 for collisions, the +27 and +15 are slight modifications to fit more accurately

		coinBox = new ArrayList<Rectangle2D>(); //Rectangle2D ArrayList for coins
		initializeCoins(img3);//Initialize coin locations and set image for every coin in image array 

		coinIntersect = false; //set initial value of boolean coinIntersect
		frame.addKeyListener(this); //add KeyListener to frame
		frame.setVisible(true); //make frame visible
		
		rndm = new Random();//initialize rndm
		random = 0;//int to hold rndm
		
		//set filename of deathSound
		deathFile = "death.wav";
		deathSound = new SoundEffect();
		deathSound.setFile(deathFile);
		
		//set filename of coinSound
		coinFile = "coin.wav";
		coinSound = new SoundEffect();
		coinSound.setFile(coinFile);
		
		//set filename of crossSound
		crossFile = "cross.wav";
		crossSound = new SoundEffect();
		crossSound.setFile(crossFile);
		
		//set filename of movesound
		moveFile = "move.wav";
		moveSound = new SoundEffect();
		moveSound.setFile(moveFile);
			
		t = new Timer(5, this);//initialize timer with 5 millisecond delay and ActionListener
		t.start();//start timer
		
		//backgroundSound.play();//start playing background music
	}
	/* Purpose of method: Set image and X Y coordinates of all elements in coin image array 
	 * Accepts: Image img
	 * Returns: coin[] array full with coin images and corresponding x y coordinates for each
	 */
	public void initializeCoins(Image img){
		for(int i = 0; i<coin.length;i++){
			coin[i]= img.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
			coinX[i] = (int)(Math.random()*550 +5);
			coinY[i] = (int)(Math.random()*415 + 55);
			coinBox.add(new Rectangle2D.Double(coinX[i],coinY[i], 40, 40));
		}
	}
	/* Purpose of method: Generate random number from 3-7
	 * Accepts: int random
	 * Returns: integer with random number from 3-7
	 */
	public int getRandomNum(int random){
		random = rndm.nextInt(5)+3;
		return random;
	}
	/* Purpose of method: Automatically move cars from left to right as soon as program starts. 
	 * Accepts: ActionEvent e
	 * Returns: Car1, Car2, Car3, Car4 speeds randomized and X coordinates changed to move left to right. out of bounds to in bounds
	 */
	public void actionPerformed(ActionEvent e) {
		car1X-= car1Speed;//move car1 left
		car2X+= car2Speed;//move car2 right
		car3X-= car3Speed;//move car3 left
		car4X+= car4Speed;//move car4 right
		repaint();//calls paint component method until the program stops
		//if car1 moves out of bounds
		if(car1X<=-150){
			car1Speed = getRandomNum(random)+1;//set new random speed
			car1X = 600;//reset position
		}
		//if car2 moves out of bounds
		else if(car2X>=600){
			car2Speed = (getRandomNum(random)+2);//set new random speed
			car2X = -150;//reset position
		}
		//if car3 moves out of bounds
		else if(car3X<=-150){
			car3Speed = (getRandomNum(random)+3);//set new random speed
			car3X = 600;//reset position
		}
		//if car4 moves out of bounds
		else if(car4X>=600){
			car4Speed = (getRandomNum(random)+5);//set new random speed
			car4X = -150;//reset position
		}
		gameEnd();// checks if game has ended
	}
	/* Purpose of method: Determines which direction player moves if certain keys are pressed 
	 * Accepts: KeyEvent event
	 * Returns: Movement of Player depending on what key is pressed
	 */
	public void keyPressed(KeyEvent event) {
		moveSound.play();
		//if right arrow key pressed
		if (event.getKeyCode() == KeyEvent.VK_RIGHT){
			chickenX+= 50; //increase playerX coordinate by 50, moves to the right
			chicken = playerRight; //chicken image becomes the one thats facing to the right
			repaint();//calls paint component to repaint player with updated position and/or image
		}
		//if left arrow key pressed
		else if (event.getKeyCode() == KeyEvent.VK_LEFT){
			chickenX-= 50;//decrease playerX coordinate by 50, moves to the left
			chicken = playerLeft;//chicken image becomes the one thats facing to the left
			repaint();//calls paint component to repaint player with updated position and/or image
		}
		//if up arrow key pressed
		else if (event.getKeyCode() == KeyEvent.VK_UP){
			chickenY-= 50;//decrease playerY coordinate by 50, moves down
			chicken = playerUp;//chicken image becomes the one thats facing up
			repaint();//calls paint component to repaint player with updated position and/or image
		}
		//if up down key pressed
		else if (event.getKeyCode() == KeyEvent.VK_DOWN){
			chickenY+= 50;//increase playerY coordinate by 50, moves up
			chicken = playerDown;//chicken image becomes the one thats facing down
			repaint();//calls paint component to repaint player with updated position and/or image
		}
	}
	/* Purpose of method: Paints images on the screen.
	 * Accepts: Graphics g.
	 * Returns: The objects and sprites are drawn on the screen.
	 */
	public void paintComponent(Graphics g){
		//Graphics2D ga = (Graphics2D)g; //new Graphics2D for the rectangle hitboxes - uncomment to see rectangles
		//ga.setPaint(Color.black); - uncomment to see rectangles
		
		coinIntersect = false; //reset coinIntersect every time paintComponent is called
		g.setFont(newFont);//setting font of g to the font I created		
		g.drawImage(background,0,0, 600, 600, this);//draw background

		g.setColor(livesCountColor);//set lives text color
		g.drawString(Integer.toString(livesCount), 310, 50);//draw lives count text

		g.setColor(coinCountColor);//set score text color
		g.drawString(Integer.toString(coinCount), 500, 50);//draw coin count text

		g.setColor(scoreCountColor);//set score text color
		g.drawString(Integer.toString(scoreCount), 130, 50);//draw score count text

		g.drawImage(chicken, chickenX, chickenY, 75, 75, this);//draw player

		//loop through elements in coin image array
		for(int i = 0; i<coin.length;i++){
			//ga.fill(coinBox.get(i)); - uncomment to see rectangles
			g.drawImage(coin[i], coinX[i], coinY[i], this);//draw coin with coordinates created at the start of the game
			if (chickenBox.intersects(coinBox.get(i))){//if player rectangle intersects with a coin rectangle
				coinSound.play();//play coin sound
				coinX[i] = -1000;//put position out of frame so it disappears
				coinY[i] = -1000;//put position out of frame so it disappears
				coinBox.get(i).setFrame(-1000, -1000, 0, 0);//put rectangle position out of frame so player cant intersect with it more than once
				coinIntersect = true;//coin intersect true
			}
		}
		//checks if player has intersected with a coin
		if(coinIntersect == true){
			scoreCount++;//increases score count by 1
			coinCount++;//increases coin count by 1
		}

		car1Box.setFrame(car1X+27,car1Y+12,55,35);//updates car1 rectangle2D
		g.drawImage(car1, car1X, car1Y, 110, 60, this);//draws car1 image in updated location

		car2Box.setFrame(car2X+25, car2Y+15, 55, 25);//updates car2 rectangle2D
		g.drawImage(car2, car2X, car2Y, 110, 60, this);//draws car2 image in updated location

		car3Box.setFrame(car3X+25, car3Y+16, 55, 25);//updates car3 rectangle2D
		g.drawImage(car3, car3X, car3Y, 110, 60, this);//draws car3 image in updated location

		car4Box.setFrame(car4X+25, car4Y+15, 55, 35);//updates car4 rectangle2D
		g.drawImage(car4, car4X, car4Y, 110, 60, this);//draws car4 image in updated location

		//these are to view the hitbox of objects - uncomment to see rectangles
		//ga.setPaint(Color.black);
		//ga.fill(car1Box);
		//ga.fill(car2Box);
		//ga.fill(car3Box);
		//ga.fill(car4Box);
		//ga.fill(chickenBox);

		chickenBox.setFrame(chickenX+15,chickenY+15, 45,45);//updates player rectangle2D
		//if player rectangle intersects with any of the car rectangles
		if (chickenBox.intersects(car1Box) || chickenBox.intersects(car2Box) || chickenBox.intersects(car3Box) || chickenBox.intersects(car4Box)){  
			deathSound.play();//play death sound
			livesCount--;//decrease lives by 1
			chickenX = 300;//reset player X coordinate
			chickenY = 500;//reset player Y coordinate          
			chickenBox.setFrame(chickenX,chickenY, 50,50);//update player rectangle
			g.drawImage(chicken, chickenX, chickenY, 50, 50, this);//redraws player with updated coordinates
		}
		//if player X coordinate goes out of bounds
		if (chickenX >= 600){
			chickenX-= 50;//move player X coordinate back in frame
			g.drawImage(chicken, chickenX, chickenY, 50, 50, this);//redraws player with updated coordinates
		}
		//if player X coordinate goes out of bounds
		else if(chickenX <= -50){
			chickenX+= 50;chickenX-= 50;//move player X coordinate back in frame
			g.drawImage(chicken, chickenX, chickenY, 50, 50, this);//redraws player with updated coordinates
		}
		//if player Y coordinate goes out of bounds
		else if(chickenY >= 600){
			chickenY-= 50;//move player Y coordinate back in frame
			g.drawImage(chicken, chickenX, chickenY, 50, 50, this);//redraws player with updated coordinates
		}
		//if player Y coordinate reaches end (other side of the road)
		else if(chickenY <= 30){
			crossSound.play();//play cross sound
			scoreCount+=5; //increase score by 5
			chickenX = 300; //reset X coordinate of player
			chickenY = 500; //reset Y coordinate of player
			g.drawImage(chicken, chickenX, chickenY, 50, 50, this);//redraws player with updated coordinates

		}
		//draws black border image 
		g.drawImage(border, 0,0,600,600,this);
	}
	//
	public void gameEnd(){
		if(livesCount<=0){
			//source: Nivetha Srikanthan - ScubaDiving game in ExamplesCPT
			Object[] options = {"Exit", "Main Menu"};//Options that appear on the screens once the game ends
			//Option pane to pop up and present two options "YES" = "Exit", "NO" = "Main Menu"
			int n = JOptionPane.showOptionDialog(this,
					"You lost all your lives! \n"+"Score: "+scoreCount+"\nCoins: "+(int)coinCount, //show lose message
					"Game Over", //option pane name
					JOptionPane.YES_NO_OPTION,//"Exit" option
					JOptionPane.PLAIN_MESSAGE,//"Main Menu" option
					null,     //do not use a custom Icon
					options,  //the titles of buttons
					options[0]);
			//if player chooses "NO" / "Main Menu"
			if (n == JOptionPane.NO_OPTION){
				Menu.btnSound.play();//play button sound
				frame.dispose(); //dispose game frame
				livesCount=3; //reset lives count to prevent gameEnd() from errors
				new Menu();//open new main menu
			}
			//if player uses "YES" / "Exit"
			else if (n == JOptionPane.YES_OPTION){
				Menu.btnSound.play();//play button sound
				System.exit(n); //exit program
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
