/** 
 * Name: Anjanan Thirumahan
 * Submitted To: Ms. Kapustina
 * Course Code: ICS4U1
 * Date: January 20, 2022
 */
package crossyRoad;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//This class handles all sound effects that will be used in the main code
//source: https://www.youtube.com/watch?v=qPVkRtuf9CQ&t=1150s

public class SoundEffect {
	Clip clip;//create new clip
	
	/* Purpose of method: Set up sound effect clip
	 * Accepts: String soundFileName
	 * Returns: Any desired sound file set up in the clip to be ready to play, loop, or stop
	 */
	public void setFile(String soundFileName){
		try{
			File file = new File(soundFileName);
			AudioInputStream sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);//opens the clip with the format and audio data of the provided audio input stream - clip becomes operational
		}
		catch(Exception e){			
		}
	}
	/* Purpose of method: Play sound effect
	 * Accepts: None
	 * Returns: Sound effect plays once
	 */
	public void play(){
		clip.setFramePosition(0);
		clip.start();
	}
	/* Purpose of method: Stops sound
	 * Accepts: None
	 * Returns: Sound effect stops
	 */
	public void stop(){
		clip.stop();
	}
	/* Purpose of method: loops sound
	 * Accepts: None
	 * Returns: Sound effect plays continuously
	 */
	public void loop(){
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
}
