package Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GameDataManger {
    private int myPlayerHealth;
    private int myPlayerScore;
    private int myScoreMultiplier;
    private int myCorrectCount;
    private int myIncorrectCount;
    private HashMap<Integer, Boolean> myAnsweredQuestion;

    @JsonCreator
    public GameDataManger(@JsonProperty("playerHealth") int thePlayerHealth,
                          @JsonProperty("playerScore") int thePlayerScore,
                          @JsonProperty("playerMultiplier") int thePlayerMulti,
                          @JsonProperty("correctCount") int theCorrectCount,
                          @JsonProperty("incorrectCount") int theIncorrectCount,
                          @JsonProperty("answeredQuestions") HashMap<Integer, Boolean> theAnsweredQuestions) {
        myPlayerHealth = thePlayerHealth;
        myPlayerScore = thePlayerScore;
        myScoreMultiplier = thePlayerMulti;
        myCorrectCount = theCorrectCount;
        myIncorrectCount = theIncorrectCount;
        myAnsweredQuestion = theAnsweredQuestions;
    }

    public static void saveGameData(GameDataManger theGameData, File theSaveFile) {
        ObjectMapper om = new ObjectMapper();

        try {
            om.writeValue(theSaveFile, theGameData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameDataManger loadGameData(File saveFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(saveFile, GameDataManger.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
