package braayy.lobby.listener;

import braayy.lobby.Configuration;
import braayy.lobby.inventory.SimpleInventory;
import braayy.lobby.inventory.lobby.ArmorInventory;
import braayy.lobby.inventory.lobby.ServerSelectorInventory;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.getInventory().setItem(4, Configuration.serverSelectorItem);
        player.getInventory().setItem(0, Configuration.armorItem);

        if (Configuration.supportbPets) {
            player.getInventory().setItem(8, Configuration.petsItem);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem().isSimilar(Configuration.serverSelectorItem)) {
                event.setCancelled(true);

                SimpleInventory serverSelectorInventory = new ServerSelectorInventory();
                serverSelectorInventory.setup(event.getPlayer());

                serverSelectorInventory.open(event.getPlayer());
            } else if (event.getItem().isSimilar(Configuration.armorItem)) {
                event.setCancelled(true);
                event.getPlayer().updateInventory();

                SimpleInventory armorInventory = new ArmorInventory();
                armorInventory.setup(event.getPlayer());

                armorInventory.open(event.getPlayer());
            } else if (Configuration.supportbPets && event.getItem().isSimilar(Configuration.petsItem)) {
                event.setCancelled(true);

                event.getPlayer().chat("/pets");
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("blobby.block")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("blobby.block")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setFoodLevel(20);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == event.getWhoClicked().getInventory() && !event.getWhoClicked().hasPermission("blobby.block")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerAttemptPickupItem(PlayerAttemptPickupItemEvent event) {
        event.setCancelled(true);
    }

}