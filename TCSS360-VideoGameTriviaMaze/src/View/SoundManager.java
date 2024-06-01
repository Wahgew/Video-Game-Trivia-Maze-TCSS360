package View;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;


/**
 * Manages sound effects and background music for the game.
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
public class SoundManager {

    /**
     * Represents the audio clip
     */
    private Clip myClip;

    /**
     * Stores the previous volume level
     */
    private float myPreviousVolume = 0;

    /**
     * Stores the current volume level
     */
    private float myCurrentVolume = 0;

    /**
     * Controls the volume level
     */
    private FloatControl myFloatControl;

    /**
     *  Indicates whether the sound is muted
     */
    private boolean myMute = false;

    /**
     *  Represents a slider for volume control
     */
    private JSlider mySlider;

    /**
     * Array of sound file URLs
     */
    private final String[] mySoundURL;

    /**
     * Singleton instance of SoundManager
     */
    private static SoundManager mySingletonMusic;

    /**
     * Constructs a SoundManager object.
     */
    private SoundManager() {
        mySoundURL = new String[5];
        mySoundURL[0] = "/Resource/Sounds2/IntroGame.wav";
        mySoundURL[1] = "/Resource/Sounds2/LongTime.wav";
        mySoundURL[2] = "/Resource/Sounds2/GamePlay.wav";
        mySoundURL[3] = "/Resource/Sounds2/Lose.wav";
        mySoundURL[4] = "/Resource/Sounds2/Miami_original_mix.wav";
    }

    /**
     * Returns the singleton instance of SoundManager.
     *
     * @return The singleton instance of SoundManager.
     */
    public static SoundManager getInstance() {
        if (mySingletonMusic == null) {
            mySingletonMusic = new SoundManager();
        }
        return mySingletonMusic;
    }

    /**
     * Sets the audio file to be played.
     *
     * @param theFile The index of the sound file.
     */
    public void setFile(final int theFile) {
        try {
            InputStream is = getClass().getResourceAsStream(mySoundURL[theFile]);
            AudioInputStream sound = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            myClip = AudioSystem.getClip();
            myClip.open(sound);
            myFloatControl = (FloatControl) myClip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception exception) {
            System.out.println("Error: Audio file could not be opened.");
            exception.printStackTrace();
        }
    }

    /**
     * Sets the slider for volume control.
     *
     * @param theSlider The slider component.
     */
    public void setSlider(JSlider theSlider) {
        mySlider = theSlider;
    }

    /**
     * Plays the audio clip.
     */
    public void play() {
        myFloatControl.setValue(myCurrentVolume);
        if (myClip != null) {
            myClip.setFramePosition(0);
            myClip.start();
        } else {
            System.out.println("Clip is not initialized for play.");
        }
    }

    /**
     * Loops the audio clip continuously.
     */
    public void loop() {
        if (myClip != null) {
            myClip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.out.println("Clip is not initialized for loop.");
        }
    }

    /**
     * Stops the audio clip.
     */
    public void stop() {
        if (myClip != null) {
            myClip.stop();
        } else {
            System.out.println("Clip is not initialized for stop.");
        }
    }

    /**
     * Sets the volume level.
     *
     * @param volume The volume level.
     */
    public void setVolume(float volume) {
        myCurrentVolume = volume;
        if (myFloatControl != null) {
            myFloatControl.setValue(myCurrentVolume);
        }
    }


    /**
     * Increases the volume level.
     */
    public void volumeUp() {
        if (myFloatControl != null) {
            myCurrentVolume += 1.0f;
            if (myCurrentVolume > 6.0f) {
                myCurrentVolume = 6.0f;
            }
            myFloatControl.setValue(myCurrentVolume);
            System.out.println("Current Volume: " + myCurrentVolume);
        }
    }


    /**
     * Decreases the volume level.
     */
    public void volumeDown() {
        myCurrentVolume -= 1.0f;
        System.out.println("Current Volume:" + myCurrentVolume);
        if (myCurrentVolume < -80.0f) {
            myCurrentVolume = -80.0f;
        }
        myFloatControl.setValue(myCurrentVolume);
    }

    /**
     * Mutes or unmutes the audio.
     */
    public void mute() {
        if (!myMute) {
            myPreviousVolume = myCurrentVolume;
            System.out.println("Current Volume:" + myCurrentVolume);
            myCurrentVolume = -80.0f;
            if (myFloatControl != null) {
                myFloatControl.setValue(myCurrentVolume);
            }
            myMute = true;

            if (mySlider != null) {
                mySlider.setValue(mySlider.getMinimum());
            }
        } else {
            myCurrentVolume = myPreviousVolume;
            if (mySlider != null) {
                mySlider.setValue((int) myCurrentVolume);
            }
            System.out.println("Current Volume:" + myCurrentVolume);
            if (myFloatControl != null) {
                myFloatControl.setValue(myCurrentVolume);
            }
            myMute = false;
        }
    }

    /**
     * Plays background music.
     *
     * @param theIndex  The index of the sound file.
     * @param theVolume The volume level.
     */
    public void playMusic(final int theIndex, final float theVolume) {
        setFile(theIndex);
        setVolume(theVolume);
        play();
        loop();
    }


    /**
     * Returns the FloatControl object for volume adjustment.
     *
     * @return The FloatControl object.
     */
    public FloatControl getMyFloatControl() {
        return myFloatControl;
    }
}
