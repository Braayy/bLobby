package braayy.lobby.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Config extends YamlConfiguration {

    private final File file;

    public Config(String filename) {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(Config.class);

        this.file = new File(plugin.getDataFolder(), filename);

        if (!this.file.exists()) {
            plugin.saveResource(filename, false);
        }

        try {
            this.load(this.file);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not load " + filename + " file");

            exception.printStackTrace();
        }
    }

    public void save() {
        try {
            this.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}