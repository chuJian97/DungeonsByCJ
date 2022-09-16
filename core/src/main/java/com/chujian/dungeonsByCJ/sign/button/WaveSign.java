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
import com.chujian.dungeonsByCJ.dungeon.DGame;
import com.chujian.dungeonsByCJ.player.DPermission;
import com.chujian.dungeonsByCJ.trigger.InteractTrigger;
import com.chujian.dungeonsByCJ.world.DGameWorld;
import com.chujian.dungeonsByCJ.api.DungeonsAPI;
import com.chujian.dungeonsByCJ.api.sign.Button;
import com.chujian.dungeonsByCJ.api.world.InstanceWorld;
import com.chujian.dungeonsByCJ.util.commons.misc.NumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

/**
 * @author Frank Baumann, Daniel Saukel
 */
public class WaveSign extends Button {

    private double mobCountIncreaseRate;
    private boolean teleport;

    public WaveSign(DungeonsAPI api, Sign sign, String[] lines, InstanceWorld instance) {
        super(api, sign, lines, instance);
    }

    public double getMobCountIncreaseRate() {
        return mobCountIncreaseRate;
    }

    public void setMobCountIncreaseRate(double mobCountIncreaseRate) {
        this.mobCountIncreaseRate = mobCountIncreaseRate;
    }

    public boolean getTeleport() {
        return teleport;
    }

    public void setTeleport(boolean teleport) {
        this.teleport = teleport;
    }

    @Override
    public String getName() {
        return "Wave";
    }

    @Override
    public String getBuildPermission() {
        return DPermission.SIGN.getNode() + ".wave";
    }

    @Override
    public boolean isOnDungeonInit() {
        return false;
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
        return true;
    }

    @Override
    public void initialize() {
        if (!getLine(1).isEmpty()) {
            mobCountIncreaseRate = NumberUtil.parseDouble(getLine(1), 2);
        }

        if (!getLine(2).isEmpty()) {
            teleport = getLine(2).equals("+") || getLine(2).equals("true");
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
        getSign().setLine(1, DMessage.SIGN_WAVE_1.getMessage());
        getSign().setLine(2, DMessage.SIGN_WAVE_2.getMessage());
        getSign().setLine(3, ChatColor.DARK_BLUE + "############");
        getSign().update();
    }

    @Override
    public void push() {
        ((DGame) getGame()).finishWave(mobCountIncreaseRate, teleport);
    }

}
