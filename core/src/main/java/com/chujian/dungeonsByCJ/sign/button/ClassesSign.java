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

import com.chujian.dungeonsByCJ.player.DPermission;
import com.chujian.dungeonsByCJ.trigger.InteractTrigger;
import com.chujian.dungeonsByCJ.world.DGameWorld;
import com.chujian.dungeonsByCJ.api.DungeonsAPI;
import com.chujian.dungeonsByCJ.api.player.GamePlayer;
import com.chujian.dungeonsByCJ.api.player.PlayerClass;
import com.chujian.dungeonsByCJ.api.sign.Button;
import com.chujian.dungeonsByCJ.api.world.InstanceWorld;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 * @author Frank Baumann, Daniel Saukel
 */
public class ClassesSign extends Button {

    private PlayerClass playerClass;

    public ClassesSign(DungeonsAPI api, Sign sign, String[] lines, InstanceWorld instance) {
        super(api, sign, lines, instance);
        playerClass = api.getClassRegistry().get(sign.getLine(1));
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;
    }

    @Override
    public String getName() {
        return "Classes";
    }

    @Override
    public String getBuildPermission() {
        return DPermission.SIGN.getNode() + ".classes";
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
        return api.getClassRegistry().get(getLine(1)) != null;
    }

    @Override
    public void initialize() {
        if (playerClass != null) {
            InteractTrigger trigger = InteractTrigger.getOrCreate(0, getSign().getBlock(), (DGameWorld) getGameWorld());
            if (trigger != null) {
                trigger.addListener(this);
                addTrigger(trigger);
            }

            getSign().setLine(0, ChatColor.DARK_BLUE + "############");
            getSign().setLine(1, ChatColor.GREEN + playerClass.getName());
            getSign().setLine(2, "");
            getSign().setLine(3, ChatColor.DARK_BLUE + "############");
            getSign().update();

            getGameWorld().setClassesEnabled(true);

        } else {
            markAsErroneous("No such class");
        }
    }

    @Override
    public boolean push(Player player) {
        GamePlayer gamePlayer = api.getPlayerCache().getGamePlayer(player);
        gamePlayer.setPlayerClass(playerClass);
        return true;
    }

}
