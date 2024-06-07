/** 
 * Name: Anjanan Thirumahan
 * Submitted To: Ms. Kapustina
 * Course Code: ICS4U1
 * Date: January 21, 2022
 */
package crossyRoad;
import javax.sound.sampled.Clip;
//class to launch the menu from so that background music plays on loop while the game is open
public class Launch {

	static String backgroundFile;//file name for background music audio file
	Clip clip;//audio clip
	public static SoundEffect backgroundSound;//sound effect for background music
	
	public static void main(String[] args) {
		//set up and initialize background music sound effect
		backgroundFile = "crossyMusic.wav";
		backgroundSound = new SoundEffect();
		backgroundSound.setFile(backgroundFile);
		backgroundSound.loop(); //play background music on loop
		new Menu(); //load menu
	}

}
