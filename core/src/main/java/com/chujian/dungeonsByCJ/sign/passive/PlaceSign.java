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
package com.chujian.dungeonsByCJ.sign.passive;

import com.chujian.dungeonsByCJ.player.DPermission;
import com.chujian.dungeonsByCJ.world.DGameWorld;
import com.chujian.dungeonsByCJ.world.block.PlaceableBlock;
import com.chujian.dungeonsByCJ.api.DungeonsAPI;
import com.chujian.dungeonsByCJ.api.sign.Passive;
import com.chujian.dungeonsByCJ.api.world.InstanceWorld;
import org.bukkit.block.Sign;

/**
 * @author Frank Baumann, Daniel Saukel
 */
public class PlaceSign extends Passive {

    public PlaceSign(DungeonsAPI api, Sign sign, String[] lines, InstanceWorld instance) {
        super(api, sign, lines, instance);
    }

    @Override
    public String getName() {
        return "Place";
    }

    @Override
    public String getBuildPermission() {
        return DPermission.SIGN.getNode() + ".place";
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
        ((DGameWorld) getGameWorld()).addGameBlock(new PlaceableBlock(api, (DGameWorld) getGameWorld(), getSign().getBlock(), getLine(1), getLine(2)));
    }

}