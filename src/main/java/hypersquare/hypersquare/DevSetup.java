package hypersquare.hypersquare;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DevSetup {

    public static void main(String[] args) {

        if (args.length >= 1){
            if (args[0].equalsIgnoreCase("devsetup")){
                System.out.println("Accepted Minecraft EULA");
                devSetup(System.out::println);
                return;
            }
        }

        JFrame frame = new JFrame("Hypersquare installation");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        JButton button = new JButton("Setup Server and accept Minecraft EULA");
        frame.add(button);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        button.addActionListener(e -> {
            button.setEnabled(false);
            new Thread(()->{
                devSetup(button::setText);
                try {
                    Thread.sleep(1000);
                    System.exit(0);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }).start();

        });

    }

    private static void devSetup(Consumer<String> statusMessage){

        HttpClient client = HttpClient.newHttpClient();
        try {
            //Downloading server
            statusMessage.accept("Downloading server");
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.infernalsuite.com/v1/projects/asp/59ba598f-86bb-459f-a299-b75c4bb13244/download/eb461be8-fb84-4ee7-8ee2-5d3fa6f24405")).build();
            byte[] server = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
            Files.write(Path.of("server.jar"), server);
            statusMessage.accept("Downloaded server");

            //Downloading awsm plugin
            statusMessage.accept("Downloading awsm plugin");
            request = HttpRequest.newBuilder(new URI("https://api.infernalsuite.com/v1/projects/asp/59ba598f-86bb-459f-a299-b75c4bb13244/download/8cec5ac7-2032-46c1-9e33-535a27e0327e")).build();
            byte[] awsmPlugin = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
            Files.createDirectory(Path.of("plugins"));
            Files.write(Path.of("plugins/awsm-plugin.jar"), awsmPlugin);
            statusMessage.accept("Downloaded awsm plugin");

            //Downloading luckperms plugin
            statusMessage.accept("Downloading luckperms plugin");
            request = HttpRequest.newBuilder(new URI("https://download.luckperms.net/1529/bukkit/loader/LuckPerms-Bukkit-5.4.116.jar")).build();
            byte[] luckperms = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
            Files.write(Path.of("plugins/luckperms.jar"), luckperms);
            statusMessage.accept("Downloaded luckperms plugin");

            //Downloading nbt api plugin
            statusMessage.accept("Downloading nbt api plugin");
            request = HttpRequest.newBuilder(new URI("https://cdn.modrinth.com/data/nfGCP9fk/versions/fiPNOHqu/item-nbt-api-plugin-2.12.2.jar")).build();
            byte[] nbtapi = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
            Files.write(Path.of("plugins/nbtapi.jar"), nbtapi);
            statusMessage.accept("Downloaded nbt api plugin");

            //Downloading fawe plugin
            statusMessage.accept("Downloading worldedit");
            request = HttpRequest.newBuilder(new URI("https://ci.athion.net/job/FastAsyncWorldEdit/lastSuccessfulBuild/artifact/artifacts/FastAsyncWorldEdit-Bukkit-2.8.5-SNAPSHOT-641.jar")).build();
            byte[] fawe = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
            Files.write(Path.of("plugins/fawe.jar"), fawe);
            statusMessage.accept("Downloaded worldedit");

            //Hypersquare plugin
            statusMessage.accept("Downloading hypersquare");
            Files.copy(new File(DevSetup.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toPath(), Path.of("plugins/hypersquare-0.jar"));
            statusMessage.accept("Downloaded hypersquare");

            //Creating bat file
            Files.writeString(Path.of("start.bat"), "java -jar server.jar --nogui");
            Files.writeString(Path.of("eula.txt"), "eula=true");

            //Downloading schematics
            statusMessage.accept("Downloading Schematics");
            request = HttpRequest.newBuilder(new URI("https://dl.dropboxusercontent.com/scl/fi/s10scv4bur1suumauw3hb/schematics.zip?rlkey=okhn1bnk2j2pyp5ad6muqwfgb&dl=1&raw=1")).build();
            byte[] schematics = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
            Files.write(Path.of("debug.zip"),schematics);
            ByteArrayInputStream byteStream = new ByteArrayInputStream(schematics);
            ZipInputStream zipStream = new ZipInputStream(byteStream);
            ZipEntry zipEntry = zipStream.getNextEntry();
            Path.of("plugins/FastAsyncWorldEdit/schematics").toFile().mkdirs();
            while (zipEntry != null){
                System.out.println(zipEntry.getName());
                Files.copy(zipStream, Path.of("plugins/FastAsyncWorldEdit/schematics/" + zipEntry.getName()));
                zipEntry = zipStream.getNextEntry();
            }
            zipStream.close();
            statusMessage.accept("Downloaded Schematics");


            statusMessage.accept("Done!");
        } catch (URISyntaxException | IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

}
