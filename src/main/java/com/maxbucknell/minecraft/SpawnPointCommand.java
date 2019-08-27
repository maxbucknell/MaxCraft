package com.maxbucknell.minecraft;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public final class SpawnPointCommand implements CommandExecutor
{
    private ConsoleCommandSender console;

    public SpawnPointCommand(ConsoleCommandSender console)
    {
        super();
        this.console = console;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (command.getName().equalsIgnoreCase("mbspawnpoint")) {
            console.sendMessage(String.format("[%s]: %s: %s", sender.getClass().getName(), label, String.join(", ", args)));
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            SpawnLocationSharable.setPlayerSpawnLocation(player, player.getLocation());
            player.sendMessage("Set spawn location");
        }

        return true;
    }
}
