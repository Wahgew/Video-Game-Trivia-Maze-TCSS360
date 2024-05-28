package Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameDataManger {
    private static final String SAVE_FILE_PATH = "src/Resource/save.json";

    public void saveGameData() {
        ObjectMapper om = new ObjectMapper();
        File saveFile = new File(SAVE_FILE_PATH);

        SimpleModule module = new SimpleModule();
        module.addSerializer(Maze.class, new Maze.MazeSerializer());
        module.addSerializer(Room.class, new Room.RoomSerializer());
        module.addSerializer(Door.class, new Door.DoorSerializer());
        om.registerModule(module);

        try {
            Player player = Player.getInstance();
            Maze maze = Maze.getInstance();

            HashMap<String, Object> gameData = new HashMap<>();
            gameData.put("mazeInstance", maze);
            gameData.put("playerInstance", player);
            om.writerWithDefaultPrettyPrinter().writeValue(saveFile, gameData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGameData() {
        ObjectMapper objectMapper = new ObjectMapper();
        File loadFile = new File(SAVE_FILE_PATH);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Door.class, new Door.DoorDeserializer());
        module.addDeserializer(Room.class, new Room.RoomDeserializer());
        module.addDeserializer(Maze.class, new Maze.MazeDeserializer());
        objectMapper.registerModule(module);

        try {
            Map<String, Object> gameData = objectMapper.readValue(loadFile, new TypeReference<>() {
            });
            Maze loadedMaze = objectMapper.convertValue(gameData.get("mazeInstance"), Maze.class);

            Maze.setMySingleton(loadedMaze);

            // Deserialize player
            Player loadedPlayer = objectMapper.convertValue(gameData.get("playerInstance"), Player.class);
            Player instance = Player.getInstance();
            instance.setMyLocationRow(loadedPlayer.getMyLocationRow());
            instance.setMyLocationCol(loadedPlayer.getMyLocationCol());
            instance.setMyMazeLayout(loadedPlayer.getMyMazeLayout());
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

    public static void deleteSavedGame() {
        File saveFile = new File(SAVE_FILE_PATH);
        if (saveFile.exists()) {
            if (saveFile.delete()) {
                System.out.println("Save file deleted successfully.");
            } else {
                System.out.println("Failed to delete the save file.");
            }
        } else {
            System.out.println("No save file to delete.");
        }
    }

    public static void checkAndHandleVictory() {
        Player player = Player.getInstance();
        if (player.getMyVictory()) {
            deleteSavedGame();
        }
    }
}
