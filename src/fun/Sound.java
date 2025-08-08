package fun;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound 
{
    Clip clip;
    URL sound_url[] = new URL[30];

    public Sound()
    {
        sound_url[0] = getClass().getResource("/Music/InTheDesert.wav");
        sound_url[1] = getClass().getResource("/Music/InTheDungeon.wav");
        sound_url[2] = getClass().getResource("/Music/InTheForrest.wav");
        sound_url[3] = getClass().getResource("/Music/MainSong.wav");
        sound_url[4] = getClass().getResource("/Music/PickItem.wav");
        sound_url[5] = getClass().getResource("/Music/receivedamage.wav");
        sound_url[6] = getClass().getResource("/Music/hitmonster.wav");
        sound_url[7] = getClass().getResource("/Music/swordhit.wav");
    }

    public void set_file(int ni)
    {
        try 
        {
            AudioInputStream audio = AudioSystem.getAudioInputStream(sound_url[ni]);
            clip = AudioSystem.getClip();
            clip.open(audio);
        } 
        catch(Exception e) 
        {
            
        }
    }

    public void play()
    {
        clip.start();
    }

    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop()
    {
        clip.stop();
    }
}
