package braayy.lobby.timer;

import braayy.lobby.Configuration;
import braayy.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ServerPlayersTimer extends BukkitRunnable {

    @Override
    public void run() {
        for (String server : Configuration.serverSelectorServers) {
            requestPlayerCount(server);
        }
    }

    private static void requestPlayerCount(String serverName) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream output = new DataOutputStream(outputStream);

            output.writeUTF("PlayerCount");
            output.writeUTF(serverName);

            Bukkit.getServer().sendPluginMessage(Lobby.getInstance(), "BungeeCord", outputStream.toByteArray());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
