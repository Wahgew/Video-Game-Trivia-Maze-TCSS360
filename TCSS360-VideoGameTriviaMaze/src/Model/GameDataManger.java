package Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class GameDataManger {
    private static final String SAVE_FILE_PATH = "src/Resource/save.json";

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
