package braayy.lobby.service;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServerPlayersService implements PluginMessageListener {

    private final Map<String, Integer> playerCountMap;

    public ServerPlayersService() {
        this.playerCountMap = new HashMap<>();
    }

    public void clear() {
        this.playerCountMap.clear();
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(message);
        DataInputStream input = new DataInputStream(inputStream);

        try {
            String subchannel = input.readUTF();

            if (subchannel.equals("PlayerCount")) {
                String serverName = input.readUTF();
                int playerCount = input.readInt();

                this.playerCountMap.put(serverName, playerCount);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Set<Map.Entry<String, Integer>> getServersPlayerCount() {
        return this.playerCountMap.entrySet();
    }

}
