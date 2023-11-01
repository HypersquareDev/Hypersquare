package hypersquare.hypersquare.plot;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import hypersquare.hypersquare.Hypersquare;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerDatabase {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> playerCollection;

    public PlayerDatabase() {
        mongoClient = MongoClients.create(Hypersquare.DB_PASS);
        database = mongoClient.getDatabase("chicken_plots");
        playerCollection = database.getCollection("players");
    }

    public static void addPlayer(UUID playerUUID, int basicPlots,int largePlots,int hugePlots,int massivePlots,int giganticPlots){
        Document plotDocument = new Document("player", playerUUID)
                .append("uuid", playerUUID)
                .append("max_basic", basicPlots)
                .append("max_large", largePlots)
                .append("max_huge", hugePlots)
                .append("max_massive", massivePlots)
                .append("max_gigantic", giganticPlots)
                .append("basic", 0)
                .append("large", 0)
                .append("huge", 0)
                .append("massive",0)
                .append("gigantic", 0);
        playerCollection.insertOne(plotDocument);
        updateLocalPlayerData(Bukkit.getPlayer(playerUUID));
    }

    public static int getRemainingPlots(UUID playerUUID, String plotSize) {
        Document query = new Document("uuid", playerUUID);
        Document playerDocument = playerCollection.find(query).first();

        if (playerDocument != null) {
            int maxPlots = playerDocument.getInteger("max_" + plotSize);
            int usedPlots = playerDocument.getInteger(plotSize);
            return maxPlots - usedPlots;
        } else {
            return -1;
        }
    }

    public static boolean playerExists(UUID playerUUID) {
        Document query = new Document("uuid", playerUUID);
        Document playerDocument = playerCollection.find(query).first();
        return playerDocument != null;
    }

    public static int getMaxPlots(UUID playerUUID, String plotSize) {
        Document query = new Document("uuid", playerUUID);
        Document playerDocument = playerCollection.find(query).first();

        if (playerDocument != null) {
            return playerDocument.getInteger("max_" + plotSize);
        } else {
            return -1;
        }
    }

    public static int getUsedPlots(UUID playerUUID, String plotSize) {
        Document query = new Document("uuid", playerUUID);
        Document playerDocument = playerCollection.find(query).first();

        if (playerDocument != null) {
            return playerDocument.getInteger(plotSize);
        } else {
            return -1;
        }
    }

    public static void addPlot(UUID playerUUID, String plotSize) {
        Document query = new Document("uuid", playerUUID);
        Document playerDocument = playerCollection.find(query).first();

        if (playerDocument != null) {
            int currentPlots = playerDocument.getInteger(plotSize, 0); // Get the current plot count or default to 0
            playerDocument.put(plotSize, currentPlots + 1); // Increment the plot count
            playerCollection.updateOne(query, new Document("$set", playerDocument)); // Update the document in the collection
            updateLocalPlayerData(Bukkit.getPlayer(playerUUID));
        }
    }

    public static void removePlot(UUID playerUUID, String plotSize) {
        Document query = new Document("uuid", playerUUID);
        Document playerDocument = playerCollection.find(query).first();

        if (playerDocument != null) {
            int currentPlots = playerDocument.getInteger(plotSize, 0); // Get the current plot count or default to 0
            if (currentPlots > 0) {
                playerDocument.put(plotSize, currentPlots - 1); // Decrement the plot count (if it's greater than 0)
                playerCollection.updateOne(query, new Document("$set", playerDocument)); // Update the document in the collection
                updateLocalPlayerData(Bukkit.getPlayer(playerUUID));
            }
        }
    }

    public static void updateLocalPlayerData(Player player){
        Thread thread = new Thread(() -> {
            HashMap<String, Integer> playerData = new HashMap<>();
            playerData.put("usedBasic", PlayerDatabase.getUsedPlots(player.getUniqueId(), "basic"));
            playerData.put("usedLarge", PlayerDatabase.getUsedPlots(player.getUniqueId(), "large"));
            playerData.put("usedhuge", PlayerDatabase.getUsedPlots(player.getUniqueId(), "huge"));
            playerData.put("usedmassive", PlayerDatabase.getUsedPlots(player.getUniqueId(), "massive"));
            playerData.put("usedGigantic", PlayerDatabase.getUsedPlots(player.getUniqueId(), "gigantic"));
            playerData.put("maxBasic", PlayerDatabase.getMaxPlots(player.getUniqueId(), "basic"));
            playerData.put("maxLarge", PlayerDatabase.getMaxPlots(player.getUniqueId(), "large"));
            playerData.put("maxhuge", PlayerDatabase.getMaxPlots(player.getUniqueId(), "huge"));
            playerData.put("maxmassive", PlayerDatabase.getMaxPlots(player.getUniqueId(), "massive"));
            playerData.put("maxGigantic", PlayerDatabase.getMaxPlots(player.getUniqueId(), "gigantic"));
            Hypersquare.localPlayerData.put(player.getUniqueId(), playerData);
        });
        thread.start();
    }

    public static void deleteAllPlayers() {
        Document query = new Document(); // Empty query matches all documents
        playerCollection.deleteMany(query);
    }
    public static void increaseMaxPlots(UUID playerUUID, String plotSize, int additionalPlots) {
        Document query = new Document("uuid", playerUUID);
        Document playerDocument = playerCollection.find(query).first();

        if (playerDocument != null) {
            int currentMaxPlots = playerDocument.getInteger("max_" + plotSize, 0);
            int newMaxPlots = currentMaxPlots + additionalPlots;

            playerDocument.put("max_" + plotSize, newMaxPlots);
            playerCollection.updateOne(query, new Document("$set", playerDocument));
            updateLocalPlayerData(Bukkit.getPlayer(playerUUID));
        }
    }

}