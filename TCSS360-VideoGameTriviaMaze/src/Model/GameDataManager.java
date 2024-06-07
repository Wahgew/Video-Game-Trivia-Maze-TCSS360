package Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * GameDataManger class provides functionality to save, load, and manage game data
 * including the maze and player instances.
 * It also provides utility methods to handle game
 * data files.
 * This class uses Jackson for JSON serialization and deserialization of the game data.
 * The game data is stored in a JSON file
 *
 * @author Peter Madin
 * @author Ken Egawa
 * @author Sopheanith Ny
 * @version 0.0.8 May 28, 2024
 */
public class GameDataManager {
    private static final String SAVE_FILE_PATH = "/Resource/save.json";
    private static final String WRITABLE_SAVE_FILE_PATH = "Resource/save.json";

    /**
     * Saves the current game data to a JSON file.
     *
     * This method serializes the current instances of Player and Maze and writes
     * them to a JSON file.
     */
    public void saveGameData() {
        ObjectMapper om = new ObjectMapper();
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

            Path saveFilePath = Paths.get(WRITABLE_SAVE_FILE_PATH);
            Files.createDirectories(saveFilePath.getParent());
            try (OutputStream os = new FileOutputStream(saveFilePath.toFile())) {
                om.writerWithDefaultPrettyPrinter().writeValue(os, gameData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the game data from a JSON file.
     *
     * This method deserializes the JSON file to restore
     * the instances of Player and Maze.
     */
    public void loadGameData() {
        ObjectMapper om = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Door.class, new Door.DoorDeserializer());
        module.addDeserializer(Room.class, new Room.RoomDeserializer());
        module.addDeserializer(Maze.class, new Maze.MazeDeserializer());
        om.registerModule(module);

        // First, try loading from the writable file
        Path saveFilePath = Paths.get(WRITABLE_SAVE_FILE_PATH);
        if (Files.exists(saveFilePath)) {
            loadFromFile(om, saveFilePath.toFile());
        } else {
            // If writable file doesn't exist, load from classpath
            try (InputStream is = GameDataManager.class.getResourceAsStream(SAVE_FILE_PATH)) {
                if (is != null) {
                    deserializeGameData(om, is);
                } else {
                    System.out.println("No save file found. Starting a new game.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadFromFile(ObjectMapper om, File file) {
        try (InputStream is = new FileInputStream(file)) {
            deserializeGameData(om, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deserializeGameData(ObjectMapper om, InputStream is) throws IOException {
        HashMap<String, Object> gameData = om.readValue(is, new HashMap<String, Object>().getClass());
        Maze loadedMaze = om.convertValue(gameData.get("mazeInstance"), Maze.class);
        Maze.setMySingleton(loadedMaze);

        Player loadedPlayer = om.convertValue(gameData.get("playerInstance"), Player.class);
        Player instance = Player.getInstance();
        instance.setMyLocationRow(loadedPlayer.getMyLocationRow());
        instance.setMyLocationCol(loadedPlayer.getMyLocationCol());
        instance.setMyMazeLayout(loadedPlayer.getMyMazeLayout());
        instance.setMyScore(loadedPlayer.getMyScore());
        instance.setMyDirection(loadedPlayer.getMyDirection());
        instance.setMyCorrectTotal(loadedPlayer.getMyCorrectTotal());
        instance.setMyIncorrectTotal(loadedPlayer.getMyIncorrectTotal());
        instance.setMyConsecutiveAns(loadedPlayer.getMyConsecutiveAns());
        instance.setMyCheat(loadedPlayer.getMyCheat());
        instance.setMyVictory(loadedPlayer.getMyVictory());
        instance.setMyHealth(loadedPlayer.getMyHealth());
        instance.setMyQuestionsAnswered(loadedPlayer.getMyQuestionsAnswered());
    }

    /**
     * Deletes the saved game data file if it exists.
     *
     * This method checks if the save file exists and
     * deletes it if present.
     * Deletion only happens when the player beats the maze.
     */
    public static void deleteSavedGame() {
        File saveFile = new File(WRITABLE_SAVE_FILE_PATH);
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

    /**
     * Checks the player's victory status and handles it accordingly.
     *
     * If the player has achieved victory, this method can perform necessary actions like deleting
     * the save file.
     */
    public static void checkAndHandleVictory() {
        Player player = Player.getInstance();
        if (player.getMyVictory()) {
            deleteSavedGame();
        }
    }
}
