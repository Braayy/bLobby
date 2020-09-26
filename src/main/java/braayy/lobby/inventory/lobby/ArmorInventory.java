package braayy.lobby.inventory.lobby;

import braayy.lobby.Configuration;
import braayy.lobby.inventory.NamedItemStack;
import braayy.lobby.inventory.SimpleInventory;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ArmorInventory extends SimpleInventory {

    public ArmorInventory() {
        super(Configuration.armorTitle, 9 * 6, true);
    }

    @Override
    public void setup(Player player) {
        this.handleClick(0, 0, () -> {
            player.getInventory().setArmorContents(null);

            this.redraw(player);
        });

        for (int x = 1; x < 9; x += 2) {
            for (int i = 1; i < 5; i++) {
                this.handleClick(x, i, (armorItem) -> {
                    if (armorItem.getType().name().endsWith("HELMET")) {
                        if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getType() == armorItem.getType()) {
                            player.getInventory().setHelmet(null);
                        } else {
                            player.getInventory().setHelmet(armorItem);
                        }
                    } else if (armorItem.getType().name().endsWith("CHESTPLATE")) {
                        if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getType() == armorItem.getType()) {
                            player.getInventory().setChestplate(null);
                        } else {
                            player.getInventory().setChestplate(armorItem);
                        }
                    } else if (armorItem.getType().name().endsWith("LEGGINGS")) {
                        if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getType() == armorItem.getType()) {
                            player.getInventory().setLeggings(null);
                        } else {
                            player.getInventory().setLeggings(armorItem);
                        }
                    } else if (armorItem.getType().name().endsWith("BOOTS")) {
                        if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getType() == armorItem.getType()) {
                            player.getInventory().setBoots(null);
                        } else {
                            player.getInventory().setBoots(armorItem);
                        }
                    }

                    this.redraw(player);
                });
            }
        }
    }

    @Override
    public void draw(Player player) {
        this.setItem(0, 0, Configuration.armorClearItem);

        int armorIndex = 0;
        for (int x = 1; x < 9; x += 2) {
            ArmorType type = ArmorType.values()[armorIndex++];

            for (int i = 1; i < 5; i++) {
                ArmorPiece piece = ArmorPiece.values()[i - 1];

                ItemStack armorItem = new ItemStack(Material.valueOf(type.name() + "_" + piece.name()));

                ItemStack playerArmor;
                switch (piece) {
                    case HELMET: playerArmor = player.getInventory().getHelmet(); break;
                    case CHESTPLATE: playerArmor = player.getInventory().getChestplate(); break;
                    case LEGGINGS: playerArmor = player.getInventory().getLeggings(); break;
                    case BOOTS: playerArmor = player.getInventory().getBoots(); break;

                    default: playerArmor = null; break;
                }

                if (playerArmor != null && playerArmor.getType() == armorItem.getType()) {
                    armorItem.addEnchantment(Enchantment.DURABILITY, 1);
                    armorItem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }

                this.setItem(x, i, armorItem);
            }
        }
    }

    public enum ArmorType {
        CHAINMAIL,
        IRON,
        GOLD,
        DIAMOND
    }

    public enum ArmorPiece {
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS
    }

}
