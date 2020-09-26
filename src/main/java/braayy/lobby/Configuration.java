package braayy.lobby;

import braayy.lobby.inventory.SimpleInventory;
import braayy.lobby.util.Config;
import braayy.lobby.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {

    public static ItemStack serverSelectorItem;
    public static String serverSelectorTitle;

    public static List<String> serverSelectorServers;
    public static int serverSelectorRefreshRate;

    public static Config serverSelectorConfig;
    public static Map<String, ItemStack> serverSelectorServerItemMap;

    public static String armorTitle;

    public static ItemStack armorItem;
    public static ItemStack armorClearItem;

    public static boolean supportbPets;
    public static ItemStack petsItem;

    public static boolean doubleJump;

    public static void load(Plugin plugin) {
        serverSelectorConfig = new Config("server-selector.yml");

        serverSelectorItem = Util.loadItemStack(plugin.getConfig().getConfigurationSection("item.server-selector"));
        serverSelectorTitle = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("title.server-selector"));

        serverSelectorServers = serverSelectorConfig.getStringList("servers");
        serverSelectorRefreshRate = serverSelectorConfig.getInt("refresh-rate");

        serverSelectorServerItemMap = new HashMap<>();

        for (String serverName : serverSelectorConfig.getConfigurationSection("item").getKeys(false)) {
            ItemStack serverItem = Util.loadItemStack(serverSelectorConfig.getConfigurationSection("item." + serverName));

            serverSelectorServerItemMap.put(serverName, serverItem);
        }

        armorTitle = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("title.armor"));

        armorItem = Util.loadItemStack(plugin.getConfig().getConfigurationSection("item.armor"));
        armorClearItem = Util.loadItemStack(plugin.getConfig().getConfigurationSection("item.armor.clear"));

        supportbPets = plugin.getConfig().getBoolean("support.bPets") && plugin.getServer().getPluginManager().isPluginEnabled("bPets");

        if (supportbPets) {
            petsItem = Util.loadItemStack(plugin.getConfig().getConfigurationSection("item.pets"));
        }

        doubleJump = plugin.getConfig().getBoolean("feature.double-jump");
    }

}