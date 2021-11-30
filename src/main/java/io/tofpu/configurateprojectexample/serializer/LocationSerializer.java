package io.tofpu.configurateprojectexample.serializer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public final class LocationSerializer implements TypeSerializer<Location> {
    private static final String WORLD = "world";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String Z = "z";
    private static final String YAW = "yaw";
    private static final String PITCH = "pitch";

    // creating a static instance of location serializer for us to access it anytime
    public static final LocationSerializer INSTANCE = new LocationSerializer();

    @Override
    public Location deserialize(final Type type, final ConfigurationNode node) {
        // if the node is empty
        if (node.empty()) {
            return null;
        }

        // retrieving the world name
        final String world = node.node(WORLD).getString();
        // retrieving the x coordinate
        final double x = node.node(X).getDouble();
        // retrieving the y coordinate
        final double y = node.node(Y).getDouble();
        // retrieving the z coordinate
        final double z = node.node(Z).getDouble();
        // retrieving the location's yaw
        final float yaw = node.node(YAW).getFloat();
        // retrieving the location's pitch
        final float pitch = node.node(PITCH).getFloat();

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    @Override
    public void serialize(final Type type, @Nullable final Location obj, final ConfigurationNode node) throws SerializationException {
        final World world;
        // if the given location object is null, or the location world is null
        if (obj == null || (world = obj.getWorld()) == null) {
            node.raw(null);
            return;
        }

        // serializing our location variable here
        node.node(WORLD).set(world.getName());
        node.node(X).set(obj.getX());
        node.node(Y).set(obj.getY());
        node.node(Z).set(obj.getZ());
        node.node(YAW).set(obj.getYaw());
        node.node(PITCH).set(obj.getPitch());
    }
}
