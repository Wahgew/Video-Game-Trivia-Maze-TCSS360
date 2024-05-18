package Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GameDataManger {
    private int myPlayerHealth;
    private int myPlayerScore;
    private int myScoreMultiplier;
    private int myCorrectTotal;
    private int myIncorrectTotal;
    private HashMap<Integer, Boolean> myAnsweredQuestion;
    private static final String SAVE_FILE_PATH = "src/Resource/save.json";

    @JsonCreator
    //@JsonDeserialize(as = GameDataManger.class)
//    public GameDataManger(@JsonProperty("playerHealth") int thePlayerHealth,
//                          @JsonProperty("playerScore") int thePlayerScore,
//                          @JsonProperty("playerMultiplier") int thePlayerMulti,
//                          @JsonProperty("correctCount") int theCorrectCount,
//                          @JsonProperty("incorrectCount") int theIncorrectCount,
//                          @JsonProperty("answeredQuestions") HashMap<Integer, Boolean> theAnsweredQuestions) {
//        myPlayerHealth = thePlayerHealth;
//        myPlayerScore = thePlayerScore;
//        myScoreMultiplier = thePlayerMulti;
//        myCorrectTotal = theCorrectCount;
//        myIncorrectTotal = theIncorrectCount;
//        myAnsweredQuestion = theAnsweredQuestions;
//    }

    public void saveGameData() {
        ObjectMapper om = new ObjectMapper();
        File saveFile = new File(SAVE_FILE_PATH);
        try {
            om.writeValue(saveFile, Player.getInstance());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGameData() {
        ObjectMapper objectMapper = new ObjectMapper();
        File loadFile = new File(SAVE_FILE_PATH);

        try {
            Player loadedPlayer = objectMapper.readValue(loadFile, Player.class);
            Player instance = Player.getInstance();
            instance.setMyLocationRow(loadedPlayer.getMyLocationRow());
            instance.setMyLocationCol(loadedPlayer.getMyLocationCol());
            instance.setMyScore(loadedPlayer.getMyScore());
            instance.setMyDirection(loadedPlayer.getMyDirection());
            instance.setMyCorrectTotal(loadedPlayer.getMyCorrectTotal());
            instance.setMyIncorrectTotal(loadedPlayer.getMyIncorrectTotal());
            instance.setMyVictory(loadedPlayer.getMyVictory());
            instance.setMyHealth(loadedPlayer.getMyHealth());
            instance.setMyQuestionsAnswered(loadedPlayer.getMyQuestionsAnswered());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
