package me.ethereal.siegeteleportblock;

import com.gmail.goosius.siegewar.SiegeController;
import com.gmail.goosius.siegewar.objects.Siege;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

public class MainListener implements Listener {
    private int radius;
    private String denyMessage;

    public MainListener(SiegeTeleportBlock plugin) {
        setValuesFromConfig(plugin);
    }

    private void setValuesFromConfig(SiegeTeleportBlock plugin) {
        radius = plugin.getConfig().getInt("max-teleport-radius");
        String configString = plugin.getConfig().getString("deny-message");
        assert configString != null;
        denyMessage = ChatColor.translateAlternateColorCodes('&', configString.replace("{radius}", String.valueOf(radius)));
    }

    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
        Location location = event.getTo();
        Player player = event.getPlayer();
        if (!player.hasPermission("stb.bypass")) {
            // check sieges
            List<Siege> sieges = SiegeController.getSieges();
            for (Siege siege : sieges) {
                if (isTooCloseToSiege(location, siege.getFlagLocation())) {
                    player.sendMessage(denyMessage);
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    private boolean isTooCloseToSiege(Location homeLocation, Location siegeLocation) {
        return siegeLocation.distance(homeLocation) <= radius;
    }

    public void onReload(SiegeTeleportBlock plugin) {
        setValuesFromConfig(plugin);
    }
}
