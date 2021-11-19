package me.OnlineMetlesley.com;

import java.util.Iterator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player player = (Player)sender;
        final FileConfiguration config = this.getConfig();
        if (cmd.getName().equalsIgnoreCase("bug") && sender instanceof Player) {
            if (args.length >= 1) {
                String bugMessage = "";
                for (final String arg : args) {
                    bugMessage = String.valueOf(bugMessage) + arg + " ";
                }
                if (!config.contains(player.getName().toLowerCase())) {
                    config.set(player.getName().toLowerCase(), (Object)bugMessage);
                    player.sendMessage(ChatColor.GREEN + "(!) Bug reported successfully!");
                    this.saveConfig();
                }
                else {
                    player.sendMessage(ChatColor.RED + "(!) Bug already submitted!");
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Enter your report after the /bug command.");
            }
            return true;
        }
        if (!cmd.getName().equalsIgnoreCase("bugs") || !(sender instanceof Player)) {
            if (cmd.getName().equalsIgnoreCase("delbug") && sender instanceof Player) {
                if (args.length == 1 && player.hasPermission("reports.delbugs")) {
                    if (config.contains(args[0].toLowerCase())) {
                        config.set(args[0].toLowerCase(), (Object)null);
                        this.saveConfig();
                        player.sendMessage(ChatColor.GREEN + "Bug deleted successfully");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Player not found!");
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "(!) Incorrect arguments / invalid permission");
                }
            }
            return false;
        }
        if (player.hasPermission("reports.seebugs")) {
            for (final String key : config.getKeys(false)) {
                player.sendMessage(ChatColor.YELLOW + key + ": " + config.getString(key));
            }
            if (config.getKeys(false).isEmpty()) {
                player.sendMessage(ChatColor.GREEN + "(!) No bugs found!");
            }
            return true;
        }
        player.sendMessage(ChatColor.RED + "(!) You have no permissions to use this command");
        return false;
    }
}
