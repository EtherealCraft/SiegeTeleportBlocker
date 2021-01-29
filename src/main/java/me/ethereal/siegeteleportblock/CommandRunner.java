package me.ethereal.siegeteleportblock;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandRunner implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                SiegeTeleportBlock plugin = SiegeTeleportBlock.getInstance();

                plugin.reloadConfig();
                MainListener listener = plugin.getListener();
                listener.onReload(plugin);
                sender.sendMessage(ChatColor.GREEN + "Plugin reloaded!");
                return false;
            }
        }
        sender.sendMessage("usage: /stb reload");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission("stb.admin")) {
            return new ArrayList<>();
        }
        if (args.length == 1) {
            if ("reload".startsWith(args[0])) {
                return Collections.singletonList("reload");
            }
        }
        return new ArrayList<>();
    }
}
