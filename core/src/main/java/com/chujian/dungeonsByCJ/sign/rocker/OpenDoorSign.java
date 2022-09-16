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
package com.chujian.dungeonsByCJ.sign.rocker;

import com.chujian.dungeonsByCJ.player.DPermission;
import com.chujian.dungeonsByCJ.world.DGameWorld;
import com.chujian.dungeonsByCJ.world.block.LockedDoor;
import de.erethon.caliburn.category.Category;
import com.chujian.dungeonsByCJ.api.DungeonsAPI;
import com.chujian.dungeonsByCJ.api.sign.Rocker;
import com.chujian.dungeonsByCJ.api.world.InstanceWorld;
import com.chujian.dungeonsByCJ.util.commons.misc.BlockUtil;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;

/**
 * @author Daniel Saukel
 */
public class OpenDoorSign extends Rocker {

    private LockedDoor door;

    public OpenDoorSign(DungeonsAPI api, Sign sign, String[] lines, InstanceWorld instance) {
        super(api, sign, lines, instance);
    }

    public LockedDoor getDoor() {
        return door;
    }

    public void setDoor(LockedDoor door) {
        this.door = door;
    }

    @Override
    public String getName() {
        return "Door";
    }

    @Override
    public String getBuildPermission() {
        return DPermission.SIGN.getNode() + ".door";
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
        return true;
    }

    @Override
    public void initialize() {
        Block block = BlockUtil.getAttachedBlock(getSign().getBlock());
        if (Category.DOORS.containsBlock(block) || Category.FENCE_GATES.containsBlock(block) || Category.TRAPDOORS.containsBlock(block)) {
            if (block.getRelative(BlockFace.DOWN).getType() == block.getType()) {
                door = new LockedDoor(api, block.getRelative(BlockFace.DOWN));
            } else {
                door = new LockedDoor(api, block);
            }
            ((DGameWorld) getGameWorld()).addGameBlock(door);

        } else {
            markAsErroneous("No door attached");
        }
    }

    @Override
    public void activate() {
        if (door != null) {
            door.open();
            active = true;
        }
    }

    @Override
    public void deactivate() {
        if (door != null) {
            door.close();
            active = false;
        }
    }

}