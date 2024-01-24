package hypersquare.hypersquare;

import javax.swing.*;
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

public class DevSetup {

    public static void main(String[] args) {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("devsetup")) {
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
            new Thread(() -> {
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

    private static void devSetup(Consumer<String> statusMessage) {
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

            //Downloading fawe plugin
            statusMessage.accept("Downloading worldedit");
            request = HttpRequest.newBuilder(new URI("https://ci.athion.net/job/FastAsyncWorldEdit/lastSuccessfulBuild/artifact/artifacts/FastAsyncWorldEdit-Bukkit-2.8.5-SNAPSHOT-643.jar")).build();
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

            //Downloading worlds config
            statusMessage.accept("Downloading worlds config");
            request = HttpRequest.newBuilder(new URI("https://gist.githubusercontent.com/Chickensoup20/42fed7d04a195e70c824d66b9eb899aa/raw/25a6e92207b9ba8835dc354b2932e65300845239/worlds.yml")).build();
            byte[] worlds = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
            Path.of("plugins/SlimeWorldManager").toFile().mkdirs();
            Files.write(Path.of("plugins/SlimeWorldManager/worlds.yml"), worlds);
            statusMessage.accept("Downloaded worlds config");

            //Downloading server icon
            statusMessage.accept("Downloading server icon");
            request = HttpRequest.newBuilder(new URI("https://i.imgur.com/9MJpprp.png")).build();
            byte[] icon = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
            Files.write(Path.of("server-icon.png"), icon);
            statusMessage.accept("Downloaded server icon");

            //Downloading server properties
            statusMessage.accept("Downloading server properties");
            request = HttpRequest.newBuilder(new URI("https://gist.githubusercontent.com/Chickensoup20/38b20c00ab9a0adc3c676c8950cfa4b0/raw/0c7a2368031e25696b613659f6909d5c1fbc26fc/server.properties")).build();
            byte[] properties = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
            Files.write(Path.of("server.properties"), properties);
            statusMessage.accept("Downloaded server properties");


            statusMessage.accept("Done!");
        } catch (URISyntaxException | IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

}
