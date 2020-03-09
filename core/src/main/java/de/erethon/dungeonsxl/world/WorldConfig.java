/*
 * Copyright (C) 2012-2020 Frank Baumann
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.erethon.dungeonsxl.world;

import de.erethon.caliburn.CaliburnAPI;
import de.erethon.dungeonsxl.DungeonsXL;
import de.erethon.dungeonsxl.api.dungeon.GameRule;
import de.erethon.dungeonsxl.api.dungeon.GameRuleContainer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * The world configuration is a simple game rule source. Besides game rules, WorldConfig also stores some map specific data such as the invited players. It is
 * used directly in dungeon map config.yml files, but also part of dungeon and main config files.
 *
 * @author Frank Baumann, Milan Albrecht, Daniel Saukel
 */
public class WorldConfig extends GameRuleContainer {

    private DungeonsXL plugin;
    private CaliburnAPI caliburn;

    private File file;

    private List<String> invitedPlayers = new ArrayList<>();
    private Environment worldEnvironment;

    public WorldConfig(DungeonsXL plugin) {
        this.plugin = plugin;
        caliburn = plugin.getCaliburn();
    }

    public WorldConfig(DungeonsXL plugin, File file) {
        this(plugin);

        this.file = file;
        FileConfiguration configFile = YamlConfiguration.loadConfiguration(file);
        load(configFile);
    }

    public WorldConfig(DungeonsXL plugin, ConfigurationSection config) {
        this(plugin);

        load(config);
    }

    // Load & Save
    public void load(ConfigurationSection config) {
        for (GameRule rule : GameRule.VALUES) {
            rule.fromConfig(plugin, caliburn, this, config);
        }
    }

    public void save() {
        if (file == null) {
            return;
        }
        FileConfiguration configFile = YamlConfiguration.loadConfiguration(file);

        if (getState(GameRule.MESSAGES) != null) {
            for (int msgs : getState(GameRule.MESSAGES).keySet()) {
                configFile.set("message." + msgs, getState(GameRule.MESSAGES).get(msgs));
            }
        }

        configFile.set("invitedPlayers", invitedPlayers);
        if (worldEnvironment != null) {
            configFile.set("worldEnvironment", worldEnvironment.name());
        }

        try {
            configFile.save(file);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @return the UUIDs or names of the players invited to edit the map
     */
    public List<String> getInvitedPlayers() {
        return new ArrayList<>(invitedPlayers);
    }

    /**
     * @param uuid the player's unique ID
     */
    public void addInvitedPlayer(String uuid) {
        if (!invitedPlayers.contains(uuid)) {
            invitedPlayers.add(uuid);
        }
    }

    /**
     * @param uuid the player's unique ID
     * @param name the player's name
     */
    public void removeInvitedPlayers(String uuid, String name) {
        invitedPlayers.remove(uuid);
        invitedPlayers.remove(name);
    }

    /**
     * @return the world environment
     */
    public Environment getWorldEnvironment() {
        return worldEnvironment;
    }

    /**
     * @param worldEnvironment the world environment to set
     */
    public void setWorldEnvironment(Environment worldEnvironment) {
        this.worldEnvironment = worldEnvironment;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{file=" + file.getPath() + "}";
    }

}
