package braayy.lobby.inventory.lobby;

import braayy.lobby.Configuration;
import braayy.lobby.Lobby;
import braayy.lobby.inventory.SimpleInventory;
import braayy.lobby.util.ItemStackNBTUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ServerSelectorInventory extends SimpleInventory {

    public ServerSelectorInventory() {
        super(Configuration.serverSelectorTitle, 9 * 6, true);
    }

    @Override
    public void setup(Player player) {
        for (int x = 1; x < (9 - 1); x++) {
            for (int y = 1; y < (6 - 1); y++) {
                this.handleClick(x, y, (serverItem) -> {
                    if (serverItem != null) {
                        Object tag = ItemStackNBTUtil.getTag(serverItem);

                        String serverName = ItemStackNBTUtil.getString(tag, "ServerName");

                        connect(player, serverName);
                    }
                });
            }
        }
    }

    @Override
    public void draw(Player player) {
        Iterator<Map.Entry<String, Integer>> iterator = Lobby.getInstance().getServerPlayersService().getServersPlayerCount().iterator();

        for (int x = 1; x < (9 - 1); x++) {
            for (int y = 1; y < (6 - 1); y++) {
                if (iterator.hasNext()) {
                    Map.Entry<String, Integer> entry = iterator.next();

                    ItemStack serverItem = Configuration.serverSelectorServerItemMap.get(entry.getKey());

                    if (serverItem != null) {
                        applyPlayersPlaceholder(serverItem, entry.getValue());

                        serverItem = applyServerName(serverItem, entry.getKey());

                        this.setItem(x, y, serverItem);
                    }
                }
            }
        }
    }

    private static void connect(Player player, String serverName) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream output = new DataOutputStream(outputStream);

            output.writeUTF("Connect");
            output.writeUTF(serverName);

            player.sendPluginMessage(Lobby.getInstance(), "BungeeCord", outputStream.toByteArray());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void applyPlayersPlaceholder(ItemStack stack, int players) {
        List<String> lore = stack.getLore();

        if (lore != null) {
            lore.replaceAll((string) -> string.replace("{players}", String.valueOf(players)));

            stack.setLore(lore);
        }
    }

    private static ItemStack applyServerName(ItemStack stack, String serverName) {
        Object tag = ItemStackNBTUtil.getTag(stack);

        ItemStackNBTUtil.setString(tag, "ServerName", serverName);

        return ItemStackNBTUtil.setTag(stack, tag);
    }
}