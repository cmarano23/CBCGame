import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

//Per the advice of Professor Alfeld, we followed the guide found at https://www.geeksforgeeks.org/play-audio-file-using-java/ in order to implement our audio

class AudioPlayer{
    Clip clip;
    String status;
    AudioInputStream audioInputStream;
    static String filePath;
    static boolean laughing;
    static AudioPlayer audioPlayer;

    public AudioPlayer() throws UnsupportedAudioFileException,IOException,LineUnavailableException{
        audioInputStream=AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip=AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public static void playMusic(){
        try{

            //Royalty Free, non-copyrighted song found at https://files.freemusicarchive.org/storage-freemusicarchive-org/music/ccCommunity/Chad_Crouch/Arps/Chad_Crouch_-_Algorithms.mp3
            filePath = "Chad_Crouch_-_Algorithms.au";
            audioPlayer= new AudioPlayer();
            audioPlayer.play();

        }
        catch (Exception e){
            System.out.print("error");
            e.printStackTrace();
        }
    }

    public static void playLaugh(){
        audioPlayer.pause();
        laughing=true;
        try{
            //Royalty Free, non-copyrighted Laugh track found at https://freesound.org/people/engreitz/sounds/79769/
            filePath = "laugh.au";
            AudioPlayer laugh= new AudioPlayer();
            laugh.play();

        }
        catch (Exception e){
            System.out.print("error");
            e.printStackTrace();
        }
    }
    public void pause(){
        clip.stop();
    }
    public void play(){
        clip.start();
        status="play";
    }

}

