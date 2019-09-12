package com.maxbucknell.minecraft;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import java.util.List;

public final class OrientCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        MaxCraft plugin = MaxCraft.getPlugin(MaxCraft.class);
        if (!command.getName().equalsIgnoreCase("orient")) {
            return true;
        }

        if (!(sender instanceof Player)) {
            plugin.getServer().getConsoleSender().sendMessage("orient command invoked without player.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("Please specify a waypoint");
            return true;
        }

        Player player = (Player) sender;
        String waypoint = args[0];
        Location location;

        switch (waypoint) {
            case "home":
                location = player.getBedSpawnLocation();

                if (location == null) {
                    location = SpawnLocationManager.getPlayerSpawnLocation(player);
                }

                break;
            case "spawn":
                location = SpawnLocationManager.getPlayerSpawnLocation(player);
                break;
            case "agora":
                location = player.getWorld().getSpawnLocation();
                break;
            default:
                player.sendMessage(String.format(
                    "%s is not a valid waypoint",
                    waypoint
                ));
                return true;
        }

        player.setCompassTarget(location);

        player.sendMessage(String.format(
            "Oriented compass towards %s <%.0f, %.0f, %.0f>",
            waypoint, location.getX(), location.getY(), location.getZ()
        ));


        return true;
    }
}
