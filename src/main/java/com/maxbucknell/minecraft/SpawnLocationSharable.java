package com.maxbucknell.minecraft;

import org.jetbrains.annotations.NotNull;
import org.bukkit.Bukkit;
import java.nio.ByteBuffer;
import java.util.UUID;
import org.bukkit.World;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.persistence.PersistentDataContainer;
import com.onarandombox.multiverseinventories.share.Sharable;
import com.onarandombox.multiverseinventories.share.SharableHandler;
import com.onarandombox.multiverseinventories.share.ProfileEntry;
import com.onarandombox.multiverseinventories.profile.PlayerProfile;

public final class SpawnLocationSharable {
    public static final Sharable<Location> SPAWN_LOCATION = new Sharable.Builder<Location>("spawn_location", Location.class,
            new SharableHandler<Location>() {
                @Override
                public void updateProfile(PlayerProfile profile, Player player) {
                    Location location = getPlayerSpawnLocation(player);
                    profile.set(SPAWN_LOCATION, location);
                }

                @Override
                public boolean updatePlayer(Player player, PlayerProfile profile) {
                    Location location = profile.get(SPAWN_LOCATION);

                    if (location == null) {
                        return false;
                    }

                    setPlayerSpawnLocation(player, location);

                    return true;
                }
            }
    ).defaultSerializer(new ProfileEntry(false, "spawnLocation")).altName("spawn").build();

    private static final NamespacedKey SPAWN_LOCATION_KEY = new NamespacedKey(MaxCraft.getPlugin(MaxCraft.class), "spawn_location");

    private static final LocationTagType LOCATION_TAG_TYPE = new LocationTagType();

    private static class LocationTagType implements PersistentDataType<PersistentDataContainer, Location> {

        private static NamespacedKey WORLD_KEY = new NamespacedKey(SPAWN_LOCATION_KEY.getKey(), "world_uuid");
        private static NamespacedKey X_KEY = new NamespacedKey(SPAWN_LOCATION_KEY.getKey(), "x");
        private static NamespacedKey Y_KEY = new NamespacedKey(SPAWN_LOCATION_KEY.getKey(), "y");
        private static NamespacedKey Z_KEY = new NamespacedKey(SPAWN_LOCATION_KEY.getKey(), "z");
        private static NamespacedKey PITCH_KEY = new NamespacedKey(SPAWN_LOCATION_KEY.getKey(), "pitch");
        private static NamespacedKey YAW_KEY = new NamespacedKey(SPAWN_LOCATION_KEY.getKey(), "yaw");

        @Override
        public @NotNull Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public @NotNull Class<Location> getComplexType() {
            return Location.class;
        }

        @NotNull
        @Override
        public PersistentDataContainer toPrimitive(@NotNull Location complex, @NotNull PersistentDataAdapterContext context) {
            PersistentDataContainer primitive = context.newPersistentDataContainer();

            World world = complex.getWorld();
            if (world != null) {
                primitive.set(WORLD_KEY, UUID_TAG_TYPE, world.getUID());
            }
            primitive.set(X_KEY, PersistentDataType.DOUBLE, complex.getX());
            primitive.set(Y_KEY, PersistentDataType.DOUBLE, complex.getY());
            primitive.set(Z_KEY, PersistentDataType.DOUBLE, complex.getZ());
            primitive.set(PITCH_KEY, PersistentDataType.FLOAT, complex.getPitch());
            primitive.set(YAW_KEY, PersistentDataType.FLOAT, complex.getYaw());

            return primitive;
        }

        @NotNull
        @Override
        public Location fromPrimitive(@NotNull PersistentDataContainer primitive, @NotNull PersistentDataAdapterContext context) {
            UUID worldUid = primitive.get(WORLD_KEY, UUID_TAG_TYPE);
            Double x = primitive.get(X_KEY, PersistentDataType.DOUBLE);
            Double y = primitive.get(Y_KEY, PersistentDataType.DOUBLE);
            Double z = primitive.get(Z_KEY, PersistentDataType.DOUBLE);
            Float pitch = primitive.get(PITCH_KEY, PersistentDataType.FLOAT);
            Float yaw = primitive.get(YAW_KEY, PersistentDataType.FLOAT);

            return new Location(worldUid != null ? Bukkit.getWorld(worldUid) : null,
                    x != null ? x : 0D, y != null ? y : 0D, z != null ? z : 0D,
                    pitch != null ? pitch : 0F, yaw != null ? yaw : 0F);
        }
    }

    private static UUIDTagType UUID_TAG_TYPE = new UUIDTagType();

    private static class UUIDTagType implements PersistentDataType<byte[], UUID> {

        @Override
        public @NotNull Class<byte[]> getPrimitiveType() {
            return byte[].class;
        }

        @Override
        public @NotNull Class<UUID> getComplexType() {
            return UUID.class;
        }

        @Override
        public @NotNull byte[] toPrimitive(@NotNull UUID complex, @NotNull PersistentDataAdapterContext context) {
            ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
            bb.putLong(complex.getMostSignificantBits());
            bb.putLong(complex.getLeastSignificantBits());
            return bb.array();
        }

        @Override
        public @NotNull UUID fromPrimitive(@NotNull byte[] primitive, @NotNull PersistentDataAdapterContext context) {
            ByteBuffer bb = ByteBuffer.wrap(primitive);
            long firstLong = bb.getLong();
            long secondLong = bb.getLong();
            return new UUID(firstLong, secondLong);
        }
    }

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
