package com.maxbucknell.minecraft;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.NamespacedKey;

public final class SpawnLocationManager
{
    private static final NamespacedKey SPAWN_LOCATION_KEY = new NamespacedKey(MaxCraft.getPlugin(MaxCraft.class), "spawn_location");

    private static final LocationTag LOCATION_TAG_TYPE = new LocationTag();

    public static Location getPlayerSpawnLocation(Player player) {
        Location location = player.getPersistentDataContainer().get(SPAWN_LOCATION_KEY, LOCATION_TAG_TYPE);
        player.sendMessage(String.format("Getting location: %.2f, %.2f, %.2f", location.getX(), location.getY(), location.getZ()));

        return location;
    }

    public static void setPlayerSpawnLocation(Player player, Location location) {
        player.sendMessage(String.format("Setting location: %.2f, %.2f, %.2f", location.getX(), location.getY(), location.getZ()));

        player.getPersistentDataContainer().set(SPAWN_LOCATION_KEY, LOCATION_TAG_TYPE, location);
    }
}
