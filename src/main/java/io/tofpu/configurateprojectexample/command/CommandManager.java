package io.tofpu.configurateprojectexample.command;

import io.tofpu.configurateprojectexample.config.MyConfiguration;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.CommentedConfigurationNode;

import java.util.*;

public class CommandManager implements CommandExecutor {
    private final MyConfiguration myConfiguration;
    private final CommentedConfigurationNode node;

    public CommandManager(final MyConfiguration myConfiguration, final CommentedConfigurationNode node) {
        this.myConfiguration = myConfiguration;
        this.node = node;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length < 2) {
            return false;
        }
        final boolean retrieve;

        switch (args[0]) {
            case "get":
                retrieve = true;
                break;
            case "set":
                if (args.length < 3) {
                    return true;
                }
                retrieve = false;
                break;
            default:
                return true;
        }

        final String template = args[1] + ": %s";
        switch (args[1].toLowerCase(Locale.ROOT)) {
            case "number":
                if (retrieve) {
                    final int number = myConfiguration.getNumber();
                    // final int number = node.node("number").getInt();
                    sender.sendMessage(String.format(template, number));
                } else {

                    try {
                        myConfiguration.setNumber(Integer.parseInt(args[2]));
                        // node.node("number").set(Integer.parseInt(args[2]));
                    } catch (final NumberFormatException ex) {
                        sender.sendMessage("Number variable accepts Integers only!");
                        return true;
                    }
                }
                break;
            case "string":
                if (retrieve) {
                    final String string = myConfiguration.getString();
                    // final String string = node.node("string").getString();
                    sender.sendMessage(String.format(template, string));
                } else {
                    myConfiguration.setString(args[2]);
                    // node.node("string").set(args[2]);
                }
                break;
            case "list":
                if (retrieve) {
                    final List<String> listOfStrings = myConfiguration.getList();
                    // final List<String> listOfStrings = node.node("listOfStrings").getList(String.class);
                    sender.sendMessage(String.format(template, listOfStrings));
                } else {
                    final List<String> list = new ArrayList<>(Arrays.asList(args)
                            .subList(2, args.length));

                    myConfiguration.setList(list);
                    // node.node("listOfStrings").set(Collections.singletonList(args[1]));
                }
                break;
            case "spawn":
                if (retrieve) {
                    final Location spawn = myConfiguration.getLocation();
                    // final Location spawn = node.node("spawn").get(Location.class);
                    sender.sendMessage(String.format(template, spawn));
                } else {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("This command is restricted for players only!");
                        return false;
                    }
                    final Player player = (Player) sender;

                    // setting the location relative to the player's location
                    myConfiguration.setLocation(player.getLocation());
                    // node.node("spawn").set(player.getLocation());
                }
                break;
            case "boolean":
                final MyConfiguration.Section section = myConfiguration.getSection();
                if (retrieve) {
                    final boolean aBoolean = section.isBoolean();
                    // final Location spawn = node.node("section", "boolean").getBoolean();
                    sender.sendMessage(String.format(template, aBoolean));
                } else {
                    // setting the boolean depending on the player's input
                    section.setBoolean(Boolean.parseBoolean(args[2]));
                    // node.node("section", "boolean").set(Boolean.parseBoolean(args[2]));
                }
            default:
                return true;
        }
        return true;
    }
}
