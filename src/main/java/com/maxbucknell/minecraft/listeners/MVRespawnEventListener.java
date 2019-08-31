package com.maxbucknell.minecraft.listeners;

import org.bukkit.event.Listener;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import com.onarandombox.MultiverseCore.event.MVRespawnEvent;
import com.maxbucknell.minecraft.SpawnLocationManager;
import com.maxbucknell.minecraft.MaxCraft;

public final class MVRespawnEventListener implements Listener
{
    @EventHandler
    public void handle(MVRespawnEvent event)
    {
        Player player = event.getPlayer();
        Location location = SpawnLocationManager.getPlayerSpawnLocation(player);

        Location old = event.getPlayersRespawnLocation();

        MaxCraft.getPlugin(MaxCraft.class).getServer().getConsoleSender().sendMessage(String.format(
                    "Old location: %.2f %.2f %.2f",
                    old.getX(),
                    old.getY(),
                    old.getZ()
                    ));

        MaxCraft.getPlugin(MaxCraft.class).getServer().getConsoleSender().sendMessage(String.format(
                    "Capturing spawn location: %.2f %.2f %.2f",
                    location.getX(),
                    location.getY(),
                    location.getZ()
                    ));

        event.setRespawnLocation(location);
    }
}
