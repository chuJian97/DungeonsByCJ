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
package com.chujian.dungeonsByCJ.sign.windup;

import com.chujian.dungeonsByCJ.player.DPermission;
import de.erethon.caliburn.item.ExItem;
import com.chujian.dungeonsByCJ.api.DungeonsAPI;
import com.chujian.dungeonsByCJ.api.sign.Windup;
import com.chujian.dungeonsByCJ.api.world.InstanceWorld;
import com.chujian.dungeonsByCJ.util.commons.misc.NumberUtil;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Frank Baumann, Milan Albrecht, Daniel Saukel
 */
public class DropSign extends Windup {

    private ItemStack item;
    private Location spawnLocation;

    public DropSign(DungeonsAPI api, Sign sign, String[] lines, InstanceWorld instance) {
        super(api, sign, lines, instance);
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public String getName() {
        return "Drop";
    }

    @Override
    public String getBuildPermission() {
        return DPermission.SIGN.getNode() + ".drop";
    }

    @Override
    public boolean isOnDungeonInit() {
        return false;
    }

    @Override
    public boolean isProtected() {
        return false;
    }

    @Override
    public boolean isSetToAir() {
        return true;
    }

    @Override
    public boolean validate() {
        return api.getCaliburn().getExItem(getLine(1)) != null;
    }

    @Override
    public void initialize() {
        ExItem item = api.getCaliburn().getExItem(getLine(1));

        String[] attributes = getLine(2).split(",");
        if (attributes.length >= 1) {
            this.item = item.toItemStack(NumberUtil.parseInt(attributes[0], 1));
        }
        if (attributes.length == 2) {
            interval = NumberUtil.parseDouble(attributes[1]);
        }

        spawnLocation = getSign().getLocation().add(0.5, 0, 0.5);
        setRunnable(new BukkitRunnable() {
            @Override
            public void run() {
                if (isWorldFinished()) {
                    deactivate();
                    return;
                }
                try {
                    spawnLocation.getWorld().dropItem(spawnLocation, getItem());
                } catch (NullPointerException exception) {
                    deactivate();
                }
            }
        });
    }

}
