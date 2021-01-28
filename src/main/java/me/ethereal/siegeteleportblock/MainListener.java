package me.ethereal.siegeteleportblock;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.gmail.goosius.siegewar.SiegeController;
import com.gmail.goosius.siegewar.SiegeWar;
import com.gmail.goosius.siegewar.listeners.SiegeWarTownEventListener;
import com.gmail.goosius.siegewar.objects.Siege;
import com.gmail.goosius.siegewar.utils.SiegeWarBattleSessionUtil;
import com.gmail.goosius.siegewar.utils.SiegeWarBlockUtil;
import com.gmail.goosius.siegewar.utils.SiegeWarDistanceUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class MainListener implements Listener {
    private Essentials essentials;
    private int radius = 50;

    public MainListener() {
        this.essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
    }

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        String fullCommand = event.getMessage();
        String[] parts = fullCommand.split(" ");
        System.out.println("Length: " + parts.length);
        String command = parts[0].toLowerCase();

        if (command.equals("/home")) {
            User user = essentials.getUser(event.getPlayer());
            List<String> homeNames = user.getHomes();
            List<Location> homeLocations = new ArrayList<>();

            for (String home : homeNames) {
                try {
                    homeLocations.add(user.getHome(home));
                } catch (Exception e) {
                    System.out.println("[STB] Error adding home " + home + " to list of homes. This shouldn't happen, tell Bit!");
                }
            }
            System.out.println("home command run");
            List<Siege> sieges = SiegeController.getSieges();

            for (Siege siege : sieges) {
                for (Location loc : homeLocations) {
                    if (siege.getFlagLocation().distance(loc) < radius) {
                        user.sendMessage(ChatColor.RED + "That home is too close to an active siege flag! It needs to be " + radius + " blocks away.");
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }
}
