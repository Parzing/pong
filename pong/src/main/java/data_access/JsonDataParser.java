package data_access;

import com.google.gson.JsonParser;
import entity.user.CommonUserFactory;
import entity.user.User;

import com.google.gson.JsonObject;

public class JsonDataParser implements DataParser {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String TIME_PLAYED = "time played";
    private static final String NUM_GAMES = "games";
    private static final String WINS = "wins";

    @Override
    public String save(User user) {
        JsonObject json = new JsonObject();
        saveUsername(json, user);
        savePassword(json, user);
        saveTimePlayed(json, user);
        saveNumGames(json, user);
        saveNumWins(json, user);
        return json.toString();
    }

    // A list of attribute adders that add data to the JsonObject.

    private void saveUsername(JsonObject jsonObject, User user) {
        jsonObject.addProperty(USERNAME, user.getName());
    }
    private void savePassword(JsonObject jsonObject, User user) {
        jsonObject.addProperty(PASSWORD, user.getPassword());
    }
    private void saveTimePlayed(JsonObject jsonObject, User user) {
        jsonObject.addProperty(TIME_PLAYED, user.getDataSet().getPlayTime());
    }
    private void saveNumGames(JsonObject jsonObject, User user) {
        jsonObject.addProperty(NUM_GAMES, user.getDataSet().getNumberOfGames());
    }
    private void saveNumWins(JsonObject jsonObject, User user) {
        jsonObject.addProperty(WINS, user.getDataSet().getNumberOfWins());
    }


    @Override
    public User load(String data) {
        JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
        User user = new CommonUserFactory().create();
        loadUsername(user, jsonObject);
        loadPassword(user, jsonObject);
        loadTimePlayed(user, jsonObject);
        loadNumGames(user, jsonObject);
        loadNumWins(user, jsonObject);
        return user;
    }

    private void loadUsername(User user, JsonObject jsonObject) {
        if (jsonObject.has(USERNAME)) {
            user.setName(jsonObject.get(USERNAME).getAsString());
        }
    }
    private void loadPassword(User user, JsonObject jsonObject) {
        if (jsonObject.has(PASSWORD)) {
            user.setPassword(jsonObject.get(PASSWORD).getAsString());
        }
    }
    private void loadTimePlayed(User user, JsonObject jsonObject) {
        if (jsonObject.has(TIME_PLAYED)) {
            user.getDataSet().setPlayTime(jsonObject.get(TIME_PLAYED).getAsLong());
        }
    }
    private void loadNumGames(User user, JsonObject jsonObject) {
        if (jsonObject.has(NUM_GAMES)) {
            user.getDataSet().setNumberOfGames(jsonObject.get(NUM_GAMES).getAsInt());
        }
    }
    private void loadNumWins(User user, JsonObject jsonObject) {
        if (jsonObject.has(WINS)) {
            user.getDataSet().setNumberOfWins(jsonObject.get(WINS).getAsInt());
        }
    }

}
