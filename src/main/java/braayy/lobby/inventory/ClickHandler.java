package braayy.lobby.inventory;

import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface ClickHandler {

    void handle(ItemStack stack);

}