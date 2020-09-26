package braayy.lobby.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Util {

    public static ItemStack loadItemStack(ConfigurationSection section) {
        Material type = Material.matchMaterial(section.getString("type"));

        if (type == null) {
            throw new IllegalArgumentException(section.getString("type") + " is not a material type");
        }

        ItemStack stack = new ItemStack(type);
        ItemMeta meta = stack.getItemMeta();

        if (section.contains("name")) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', section.getString("name")));
        }

        if (section.contains("lore")) {
            List<String> lore = section.getStringList("lore");

            lore.replaceAll((string) -> ChatColor.translateAlternateColorCodes('&', string));

            meta.setLore(lore);
        }

        stack.setItemMeta(meta);

        return stack;
    }

}