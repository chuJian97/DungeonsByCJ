/*
 * Copyright (C) 2012-2021 Frank Baumann
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
package com.chujian.dungeonsByCJ.sign.button;

import com.chujian.dungeonsByCJ.config.DMessage;
import com.chujian.dungeonsByCJ.player.DPermission;
import com.chujian.dungeonsByCJ.trigger.InteractTrigger;
import com.chujian.dungeonsByCJ.world.DGameWorld;
import com.chujian.dungeonsByCJ.DungeonsXL;
import com.chujian.dungeonsByCJ.api.DungeonsAPI;
import com.chujian.dungeonsByCJ.api.sign.Button;
import com.chujian.dungeonsByCJ.api.world.InstanceWorld;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class ResourcePackSign extends Button {

    private String resourcePack;

    public ResourcePackSign(DungeonsAPI api, Sign sign, String[] lines, InstanceWorld instance) {
        super(api, sign, lines, instance);
    }

    public String getResourcePack() {
        return resourcePack;
    }

    public void setExternalMob(String resourcePack) {
        this.resourcePack = resourcePack;
    }

    @Override
    public String getName() {
        return "ResourcePack";
    }

    @Override
    public String getBuildPermission() {
        return DPermission.SIGN.getNode() + ".resourcepack";
    }

    @Override
    public boolean isOnDungeonInit() {
        return true;
    }

    @Override
    public boolean isProtected() {
        return true;
    }

    @Override
    public boolean isSetToAir() {
        return false;
    }

    @Override
    public boolean validate() {
        return ((DungeonsXL) api).getMainConfig().getResourcePacks().get(getLine(1)) != null || getLine(1).equalsIgnoreCase("reset");
    }

    @Override
    public void initialize() {
        Object url = null;
        if (getLine(1).equalsIgnoreCase("reset")) {
            // Placeholder to reset to default
            url = "http://google.com";
        } else {
            url = ((DungeonsXL) api).getMainConfig().getResourcePacks().get(getLine(1));
        }

        if (url instanceof String) {
            resourcePack = (String) url;

        } else {
            markAsErroneous("Unknown resourcepack format");
            return;
        }

        if (!getTriggers().isEmpty()) {
            setToAir();
            return;
        }

        InteractTrigger trigger = InteractTrigger.getOrCreate(0, getSign().getBlock(), (DGameWorld) getGameWorld());
        if (trigger != null) {
            trigger.addListener(this);
            addTrigger(trigger);
        }

        getSign().setLine(0, ChatColor.DARK_BLUE + "############");
        getSign().setLine(1, DMessage.SIGN_RESOURCE_PACK.getMessage());
        getSign().setLine(2, ChatColor.GREEN + getLine(1));
        getSign().setLine(3, ChatColor.DARK_BLUE + "############");
        getSign().update();
    }

    @Override
    public boolean push(Player player) {
        player.setResourcePack(resourcePack);
        return true;
    }

}
