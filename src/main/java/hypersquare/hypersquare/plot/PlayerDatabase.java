package hypersquare.hypersquare.plot;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.UUID;

public class PlayerDatabase {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> playerCollection;

    public PlayerDatabase() {
        mongoClient = MongoClients.create("mongodb+srv://chicken:e-Rdk6NUjCJM%5DBy0@loginpage.ltn5olm.mongodb.net/?retryWrites=true&w=majority"); // MongoDB server address
        database = mongoClient.getDatabase("chicken_plots");
        playerCollection = database.getCollection("players");
    }

    public static void addPlayer(UUID playerUUID, int basicPlots,int largePlots,int massivePlots,int hugePlots,int giganticPlots){
        Document plotDocument = new Document("player", playerUUID)
                .append("uuid", playerUUID)
                .append("max_basic", basicPlots)
                .append("max_large", largePlots)
                .append("max_massive", massivePlots)
                .append("max_huge", hugePlots)
                .append("max_gigantic", giganticPlots)
                .append("basic", 0)
                .append("large", 0)
                .append("massive", 0)
                .append("huge",0)
                .append("gigantic", 0);
        playerCollection.insertOne(plotDocument);
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
            }
        }
    }
}