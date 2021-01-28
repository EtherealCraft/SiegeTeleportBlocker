package me.ethereal.siegeteleportblock;

import org.bukkit.plugin.java.JavaPlugin;

public final class SiegeTeleportBlock extends JavaPlugin {

    private static SiegeTeleportBlock plugin;

    @Override
    public void onEnable() {
        plugin = this;

        MainListener listener = new MainListener();
        getServer().getPluginManager().registerEvents(listener, this);

        System.out.println("[STB] Enabled Siege Teleport Block");
    }

    @Override
    public void onDisable() {
        System.out.println("[STB] Disabling...");
        System.out.println("[STB] Bye!");
    }

    public static SiegeTeleportBlock getInstance() {
        return plugin;
    }
}
