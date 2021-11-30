package io.tofpu.configurateprojectexample;

import io.tofpu.configurateprojectexample.command.CommandManager;
import io.tofpu.configurateprojectexample.config.MyConfiguration;
import io.tofpu.configurateprojectexample.serializer.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;

public final class ExamplePlugin extends JavaPlugin {
    // this handles the aspect of loading/saving files
    private YamlConfigurationLoader loader;

    // this is quite similar to the FileConfiguration class
    private CommentedConfigurationNode node;

    // this is our own object,
    private MyConfiguration myConfiguration;

    @Override
    public void onEnable() {
        // creates a default config.yml for us
        saveDefaultConfig();

        // declaring a local file variable, we'll be using this to store the config contents, as usual
        final File file = new File(getDataFolder(), "config.yml");

        // we're building our loader off the config.yml file
        this.loader = YamlConfigurationLoader.builder()
                // linking them our config.yml file
                .file(file)
                // registering our custom serializer here
                .defaultOptions(opts -> opts.serializers(build -> build.register(Location.class, LocationSerializer.INSTANCE)))
                .build();

        try {
            // we're loading the config
            this.node = loader.load();

            // then, populating the MyConfiguration object from our config.yml file
            this.myConfiguration = node.get(MyConfiguration.class);
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }

        // registering our "example" command
        getCommand("example").setExecutor(new CommandManager(myConfiguration, node));
    }

    @Override
    public void onDisable() {
        // attempts to save the results here...
        try {
            // updates the node from our configuration class
            node.set(MyConfiguration.class, myConfiguration);

            // saving the file
            this.loader.save(node);
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
    }
}
