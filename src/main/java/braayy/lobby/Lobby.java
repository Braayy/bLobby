package braayy.lobby;

import braayy.lobby.inventory.SimpleInventoryListener;
import braayy.lobby.listener.DoubleJumpListener;
import braayy.lobby.listener.EntityListener;
import braayy.lobby.listener.PlayerListener;
import braayy.lobby.listener.WeatherListener;
import braayy.lobby.service.ServerPlayersService;
import braayy.lobby.timer.ServerPlayersTimer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin {

    private static Lobby instance;

    private ServerPlayersService serverPlayersService;

    @Override
    public void onEnable() {
        instance = this;

        SimpleInventoryListener.register(this);

        saveDefaultConfig();

        Configuration.load(this);

        this.serverPlayersService = new ServerPlayersService();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this.serverPlayersService);

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new WeatherListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityListener(), this);

        if (Configuration.doubleJump) {
            this.getServer().getPluginManager().registerEvents(new DoubleJumpListener(), this);
        }

        new ServerPlayersTimer().runTaskTimerAsynchronously(this, 0, 20 * Configuration.serverSelectorRefreshRate);

        this.fixWorldsTime();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        this.getServer().getScheduler().cancelTasks(this);

        this.serverPlayersService.clear();
    }

    private void fixWorldsTime() {
        for (World world : Bukkit.getWorlds()) {
            world.setTime(0);
            world.setGameRuleValue("doDaylightCycle", "false");
        }
    }

    public ServerPlayersService getServerPlayersService() {
        return serverPlayersService;
    }

    public static Lobby getInstance() {
        return instance;
    }
}