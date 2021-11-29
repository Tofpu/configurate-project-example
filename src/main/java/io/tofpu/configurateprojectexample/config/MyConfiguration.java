package io.tofpu.configurateprojectexample.config;

import org.bukkit.Location;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class MyConfiguration {
    private int number = 20;

    public void setNumber(final int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    private String string = "Our very own string";

    public void setString(final String string) {
        this.string = string;
    }

    public String getString() {
        return this.string;
    }

    private List<String> list = new ArrayList<>();

    public void setList(final List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return this.list;
    }

    @Setting("spawn")
    private Location location;

    public void setLocation(final Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }

    private Section section = new Section();

    public Section getSection() {
        return this.section;
    }

    @ConfigSerializable
    public static class Section {
        private boolean aBoolean = true;

        public void setBoolean(final boolean aBoolean) {
            this.aBoolean = aBoolean;
        }

        public boolean isBoolean() {
            return this.aBoolean;
        }
    }
}
