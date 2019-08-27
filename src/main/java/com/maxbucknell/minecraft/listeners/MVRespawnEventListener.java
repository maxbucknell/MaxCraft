package com.maxbucknell.minecraft.listeners;

import org.bukkit.event.Listener;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import com.onarandombox.MultiverseCore.event.MVRespawnEvent;
import com.maxbucknell.minecraft.SpawnLocationSharable;

public final class MVRespawnEventListener implements Listener
{
    @EventHandler
    public void handle(MVRespawnEvent event)
    {
        Player player = event.getPlayer();
        Location location = SpawnLocationSharable.getPlayerSpawnLocation(player);

        player.sendMessage("We are running the listener");

        event.setRespawnLocation(location);
    }
}
