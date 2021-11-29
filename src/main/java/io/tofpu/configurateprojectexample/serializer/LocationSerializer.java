package io.tofpu.configurateprojectexample.serializer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class LocationSerializer implements TypeSerializer<Location> {
    // creating a static instance of location serializer for us to access it anytime
    public static final LocationSerializer INSTANCE = new LocationSerializer();

    @Override
    public Location deserialize(final Type type, final ConfigurationNode node) {
        // retrieving required information through our node
        if (node.empty()) {
            return null;
        }

        final String world = node.node("world").getString();
        final double x = node.node("x").getDouble();
        final double y = node.node("y").getDouble();
        final double z = node.node("z").getDouble();
        final float yaw = node.node("yaw").getFloat();
        final float pitch = node.node("pitch").getFloat();

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
        node.node("world").set(world.getName());
        node.node("x").set(obj.getX());
        node.node("y").set(obj.getY());
        node.node("z").set(obj.getZ());
        node.node("yaw").set(obj.getYaw());
        node.node("pitch").set(obj.getPitch());
    }
}
