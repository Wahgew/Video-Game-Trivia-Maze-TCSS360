package Model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

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
        try {
            System.out.println("Debug AUDI0: " + "src/" + myAudioPath);
            File musicPath = new File("src/" + myAudioPath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip audioClip = AudioSystem.getClip();
                audioClip.open(audioInput);
                return audioClip;
            } else {
                System.out.println("Can't find file at " + myAudioPath);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
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
