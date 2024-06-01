package View;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
/**
 * A user interface for controlling music playback and volume.
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
public class MusicUI {
    /**
     * The sound manager responsible for music playback.
     */
    private final SoundManager mySoundManager;
    /**
     * The slider for adjusting volume.
     */
    private JSlider mySlider;
    /**
     * The frame containing the music UI.
     */
    private static JFrame myFrame;
    /**
     * The current volume level.
     */
    private float currentVolume = 0;
    /**
     * The FloatControl object for volume adjustment.
     */
    private FloatControl floatControl;

    /**
     * Constructs a MusicUI object with the specified sound manager.
     *
     * @param theSoundManager the sound manager responsible for music playback
     */
    MusicUI(SoundManager theSoundManager) {
        if (myFrame != null) {
            myFrame.dispose();
        }
        mySoundManager = theSoundManager;
        initializeUI();
    }

    /**
     * Displays the music UI.
     */
    public void showUI() {
        if (myFrame != null) {
            myFrame.dispose();
        }
        initializeUI();
    }

    /**
     * Hides the music UI.
     */
    public void hideUI() {
        if (myFrame != null) {
            myFrame.dispose();
            myFrame = null;
        }
    }

    /**
     * Initializes the music UI components.
     */
    private void initializeUI() {
        myFrame = new JFrame("Music UI");
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setLayout(new GridLayout(1, 3));
        myFrame.setResizable(false);

        JButton playButton = new JButton("PLAY");
        JButton stopButton = new JButton("STOP");
        JButton muteButton = new JButton("MUTE");

        myFrame.add(playButton);
        myFrame.add(stopButton);
        myFrame.add(muteButton);

        playButton.addActionListener(e -> {
            mySoundManager.play();
            floatControl = mySoundManager.getMyFloatControl();
            mySlider.setEnabled(true);
        });

        stopButton.addActionListener(e -> {
            mySoundManager.stop();
            mySlider.setEnabled(false);
        });

        muteButton.addActionListener(e -> mySoundManager.mute());

        mySlider = new JSlider(-40, 6);
        mySlider.setEnabled(false); // Initially disable the slider
        mySlider.addChangeListener(e -> {
            if (floatControl != null) {
                currentVolume = mySlider.getValue();
                if (currentVolume == -40) {
                    currentVolume = -80;
                }
                System.out.println("Current Volume: " + currentVolume);
                floatControl.setValue(currentVolume);
                mySoundManager.setVolume(currentVolume);
                SoundManager.getInstance().setSlider(mySlider);
            }
        });
        myFrame.add(mySlider);

        myFrame.pack();
        myFrame.setVisible(true);
    }
}