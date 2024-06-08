package Model;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * AuditoryQuestion represents a question that includes audio content.
 *
 * @author Peter Madin
 * @author Ken Egawa
 * @author Sopheanith Ny
 * @version 0.0.1 April 28, 2024
 */
public class AuditoryQuestion extends MultipleChoiceQuestion {

    /**
     * The path to the audio file associated with the question.
     */
    private final String myAudioPath;

    /**
     * Constructs an AuditoryQuestion object with the specified question text, answer data,
     * audio path, and question type.
     *
     * @param theQuestion   the text of the question
     * @param theAnswer     the data representing the answer choices and correct index
     * @param theAudioPath  the path to the audio file associated with the question
     * @param theType       the type of the question
     */
    public AuditoryQuestion(String theQuestion, AnswerData theAnswer, String theAudioPath, String theType, int theID) {
        super(theQuestion, theAnswer, theType, theID);
        myAudioPath = theAudioPath;
    }

    /**
     * Code comes from:
     * https://www.youtube.com/watch?v=wJO_cq5XeSA
     */
    public Clip playMusic() {
        Clip audioClip = null;
        try {
            String resourcePath = getResourcePath(myAudioPath);
            InputStream audioInputStream = AuditoryQuestion.class.getResourceAsStream(resourcePath);
            if (audioInputStream != null) {
                try (AudioInputStream audioInput = AudioSystem.getAudioInputStream(new BufferedInputStream(audioInputStream))) {
                    audioClip = AudioSystem.getClip();
                    audioClip.open(audioInput);
                }
            } else {
                System.out.println("Can't find resource at " + myAudioPath);
            }
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported audio file format: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading audio resource: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println("Audio line is unavailable: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error playing audio: " + e.getMessage());
        }
        return audioClip;
    }

    /**
     * Helper method to get a resource path, with or without /
     * @param path the file path
     * @return path string with or without /
     */
    private String getResourcePath(String path) {
        if (path.startsWith("/")) {
            return path;
        } else {
            return "/" + path;
        }
    }

    /**
     * Gets the path to the audio file associated with the question.
     *
     * @return the audio path
     */
    public String getAudioPath() {
        return myAudioPath;
    }

    /**
     * Gets the type of the question.
     *
     * @return the type of the question
     */
    @Override
    public String getType() {
        return super.getType();
    }

    /**
     * Gets the text of the question.
     *
     * @return the text of the question
     */
    @Override
    public String getQuestion() {
        return super.getQuestion();
    }

    /**
     * Gets the answer data associated with the question.
     *
     * @return the answer data
     */
    @Override
    public AnswerData getAnswers() {
        return super.getAnswers();
    }
}
