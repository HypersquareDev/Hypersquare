package hypersquare.hypersquare.plot;

import com.fastasyncworldedit.core.FaweAPI;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimeProperties;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import hypersquare.hypersquare.Hypersquare;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static hypersquare.hypersquare.Hypersquare.*;

public class PlotDatabase {
    private static MongoDatabase database;
    private static MongoCollection<Document> plotsCollection;
    private static MongoCollection<Document> additionalCollection;
    private static MongoCollection<Document> templatesCollection;

    public static void init() {
        database = Hypersquare.mongoClient.getDatabase(DB_NAME);
        plotsCollection = database.getCollection("plots");
        additionalCollection = database.getCollection("additional_info");
        templatesCollection = database.getCollection("templates");
        createTemplates("plot_template_basic", "basic_plot.schem");
        createTemplates("plot_template_large", "large_plot.schem");
        createTemplates("plot_template_huge", "huge_plot.schem");
        createTemplates("plot_template_massive", "massive_plot.schem");
        createTemplates("plot_template_gigantic", "gigantic_plot.schem");
        createTemplates("dev_template", "dev_template.schem");
    }

    public static void createTemplates(String worldName, String schematicName) {
        Document templateDoc = new Document(worldName, schematicName);
        SlimeWorld world = slimePlugin.getWorld(worldName);

        if (world == null) {

            String schematicsPath = "plugins/FastAsyncWorldEdit/schematics/";
            if (!Files.exists(Path.of(schematicsPath + schematicName))) {
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder(new URI("https://dl.dropboxusercontent.com/scl/fi/va3kne1vo7x1nc5qi2jd4/schematics.zip?rlkey=s2ze6j7jf1y9h8ofafakhmffm&dl=1")).build();
                    byte[] schematics = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
                    ByteArrayInputStream byteStream = new ByteArrayInputStream(schematics);
                    ZipInputStream zipStream = new ZipInputStream(byteStream);
                    ZipEntry zipEntry = zipStream.getNextEntry();
                    Path.of("plugins/FastAsyncWorldEdit/schematics").toFile().mkdirs();
                    while (zipEntry != null) {
                        System.out.println(zipEntry.getName());
                        Files.copy(zipStream, Path.of(schematicsPath + zipEntry.getName()));
                        zipEntry = zipStream.getNextEntry();
                    }
                    zipStream.close();
                } catch (Exception err) {
                    Bukkit.getLogger().warning(err.toString());
                }
            }

            SlimeLoader file = Hypersquare.slimePlugin.getLoader("mongodb");
            SlimePropertyMap properties = new SlimePropertyMap();

            properties.setValue(SlimeProperties.SPAWN_X, 0);
            properties.setValue(SlimeProperties.SPAWN_Y, 0);
            properties.setValue(SlimeProperties.SPAWN_Z, 0);

            try {
                world = Hypersquare.slimePlugin.createEmptyWorld(file, worldName, false, properties);
                Hypersquare.slimePlugin.loadWorld(world);
                Clipboard clipboard;

                File schematic = Path.of(schematicsPath + schematicName).toFile();

                ClipboardFormat format = ClipboardFormats.findByFile(schematic);
                try (ClipboardReader reader = format.getReader(new FileInputStream(schematic))) {
                    clipboard = reader.read();
                }

                try (EditSession editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld(world.getName()))) {
                    Operation operation = new ClipboardHolder(clipboard)
                            .createPaste(editSession)
                            .to(BlockVector3.at(0, 0, 0))
                            .build();
                    Operations.complete(operation);
                }

                Bukkit.getWorld(world.getName()).save();
            } catch (Exception e) {
                return;
            }
            if (templatesCollection.countDocuments(templateDoc) == 0) {
                templatesCollection.insertOne(templateDoc);
            }
        }
    }

    public static void addPlot(int plotID, String ownerUUID, String icon, String name, int node, String tags, int votes, String size, int version) {
        Document plotDocument = new Document("plotID", plotID)
                .append("owner", ownerUUID)
                .append("devs", ownerUUID) // Consider using an array for devs and builders
                .append("builders", ownerUUID) // Consider using an array for devs and builders
                .append("icon", icon)
                .append("name", name)
                .append("node", node)
                .append("tags", tags)
                .append("votes", votes)
                .append("size", size)
                .append("version", version);
        plotsCollection.insertOne(plotDocument);
    }

    public static List<Document> getPlot(String ownerUUID) {
        List<Document> info = new ArrayList<>();

        Document query = new Document("owner", ownerUUID);

        try (MongoCursor<Document> cursor = plotsCollection.find(query).iterator()) {
            while (cursor.hasNext()) {
                Document plotDocument = cursor.next();
                info.add(plotDocument);
            }
        }

        return info;
    }

    public static void changePlotName(int plotID, String newName) {
        Document filter = new Document("plotID", plotID);
        Document update = new Document("$set", new Document("name", newName));
        plotsCollection.updateOne(filter, update);
    }

    public static int getRecentPlotID() {
        Document filter = new Document(); // an empty filter to get all documents
        Document result = additionalCollection.find(filter).first();
        if (result != null) {
            return result.getInteger("plotID");
        } else {
            Document newDocument = new Document("plotID", 1);
            additionalCollection.insertOne(newDocument);
            return 1;
        }
    }

    public static void setRecentPlotID(int plotID) {
        Document update = new Document("$set", new Document("plotID", plotID));
        additionalCollection.updateOne(new Document(), update);
    }

    public static void changePlotIcon(int plotID, String newIcon) {
        Document filter = new Document("plotID", plotID);
        Document update = new Document("$set", new Document("icon", newIcon));
        plotsCollection.updateOne(filter, update);
    }

    public static String getPlotName(int plotID) {
        Document query = new Document("plotID", plotID);
        Document result = plotsCollection.find(query).first();
        if (result != null) {
            return result.getString("name");
        }
        return null;
    }

    public static Integer getPlotVersion(int plotID) {
        Document query = new Document("plotID", plotID);
        Document result = plotsCollection.find(query).first();
        if (result != null) {
            return result.getInteger("version");
        }
        return null;
    }

    public static int getPlotNode(int plotID) {
        Document query = new Document("plotID", plotID);
        Document result = plotsCollection.find(query).first();
        if (result != null) {
            return result.getInteger("node", -1);
        }
        return -1;
    }

    public static String getPlotOwner(int plotID) {
        Document query = new Document("plotID", plotID);
        Document result = plotsCollection.find(query).first();
        if (result != null) {
            return result.getString("owner");
        }
        return null;
    }

    public static void updateLocalData(int plotID) {
        Document query = new Document("plotID", plotID);
        Document result = plotsCollection.find(query).first();
        if (result != null) {
            List<Object> data = new ArrayList<>();
            data.add(result.getString("name"));
            data.add(result.getString("owner"));
            data.add(result.getInteger("node", -1));
        }
    }

    public static List<Object> getPlotData(int plotID) {
        Document query = new Document("plotID", plotID);
        Document result = plotsCollection.find(query).first();
        if (result != null) {
            List<Object> data = new ArrayList<>();
            data.add(result.getString("name"));
            data.add(result.getString("owner"));
            data.add(result.getInteger("node", -1));
            return data;
        }
        return null;
    }

    public static List<Document> getPlotsByOwner(String ownerUUID) {
        List<Document> plots = new ArrayList<>();
        MongoCollection<Document> plotsCollection = database.getCollection("plots");

        // Use Filters.eq to find documents with the matching ownerUUID
        FindIterable<Document> plotDocuments = plotsCollection.find(Filters.eq("owner", ownerUUID));

        for (Document plotDocument : plotDocuments) {
            plots.add(plotDocument);
        }

        return plots;
    }

    public static String[] getPlotDevs(int plotID) {
        Document query = new Document("plotID", plotID);
        Document result = plotsCollection.find(query).first();
        if (result != null) {
            String devsString = result.getString("devs");
            if (devsString != null) {
                return devsString.split(",");
            }
        }
        return new String[0];
    }

    public static String getRawDevs(int plotID) {
        Document query = new Document("plotID", plotID);
        Document result = plotsCollection.find(query).first();
        if (result != null) {
            return result.getString("devs");
        }
        return null;
    }

    public static void addDev(int plotID, UUID playerID) {
        Document query = new Document("plotID", plotID);
        Document result = plotsCollection.find(query).first();
        if (result != null) {
            String currentDevs = result.getString("devs");
            String newDevs = currentDevs + "," + playerID.toString();
            Document update = new Document("$set", new Document("devs", newDevs));
            plotsCollection.updateOne(query, update);
        }
    }

    public static void removeDev(int plotID, UUID playerID) {
        Document query = new Document("plotID", plotID);
        Document result = plotsCollection.find(query).first();

        if (result != null) {
            String currentDevs = result.getString("devs");

            // Split the currentDevs string into an array of player IDs
            String[] devsArray = currentDevs.split(",");

            // Create a StringBuilder to construct the newDevs string
            StringBuilder newDevsBuilder = new StringBuilder();

            for (String dev : devsArray) {
                if (!dev.equals(playerID.toString())) {
                    if (!newDevsBuilder.isEmpty()) {
                        newDevsBuilder.append(",");
                    }
                    newDevsBuilder.append(dev);
                }
            }

            String newDevs = newDevsBuilder.toString();

            // Update the document with the newDevs string
            Document update = new Document("$set", new Document("devs", newDevs));
            plotsCollection.updateOne(query, update);
        }
    }

    public static void deleteAllPlots() {
        Document query = new Document(); // Empty query matches all documents
        plotsCollection.deleteMany(query);
    }

    public static void deletePlot(int plotID) {
        Document query = new Document("plotID", plotID);
        plotsCollection.deleteOne(query);
    }

    public static void addEvents(int plotID, Map<String, String> events) {
        Document filter = new Document("plotID", plotID);
        Document update = new Document();

        for (Map.Entry<String, String> entry : events.entrySet()) {
            String eventKey = entry.getKey();
            String eventValue = entry.getValue();
            update.append("events." + eventKey, eventValue);
        }

        plotsCollection.updateOne(filter, new Document("$set", update));
    }


    public static boolean eventValueExistsInPlot(int plotID, String eventValue) {
        Document query = new Document("plotID", plotID);
        query.append("events", new Document("$elemMatch", new Document("$eq", eventValue)));

        Document result = plotsCollection.find(query).first();

        return result != null;
    }

    public static HashMap<String, String> getAllUniqueEventsInPlot(int plotID) {
        HashMap<String, String> uniqueEvents = new HashMap<>();

        Document query = new Document("plotID", plotID);
        Document result = plotsCollection.find(query).first();

        if (result != null) {
            Document eventsDocument = result.get("events", Document.class);

            if (eventsDocument != null) {
                for (String eventKey : eventsDocument.keySet()) {
                    String eventValue = eventsDocument.getString(eventKey);
                    uniqueEvents.put(eventKey, eventValue);
                }
            }
        }

        return uniqueEvents;
    }


    public static void updateEventsCache(int plotID) {
        eventCache.put(plotID, PlotDatabase.getAllUniqueEventsInPlot(plotID));
    }

    public static void removeEventByKey(int plotID, String eventKeyToRemove) {
        Document filter = new Document("plotID", plotID);
        Document update = new Document("$unset", new Document("events." + eventKeyToRemove, ""));

        plotsCollection.updateOne(filter, update);
    }


}
