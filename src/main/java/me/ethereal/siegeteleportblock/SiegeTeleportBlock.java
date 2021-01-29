package me.ethereal.siegeteleportblock;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SiegeTeleportBlock extends JavaPlugin {

    private static SiegeTeleportBlock plugin;
    private MainListener listener;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();

        listener = new MainListener(this);
        getServer().getPluginManager().registerEvents(listener, this);

        Objects.requireNonNull(this.getCommand("stb")).setExecutor(new CommandRunner());

        System.out.println("[STB] Enabled Siege Teleport Block");
    }

    public MainListener getListener() {
        return listener;
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
