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
import com.chujian.dungeonsByCJ.api.DungeonsAPI;
import com.chujian.dungeonsByCJ.api.player.GamePlayer;
import com.chujian.dungeonsByCJ.api.player.PlayerGroup;
import com.chujian.dungeonsByCJ.api.sign.Button;
import com.chujian.dungeonsByCJ.api.world.InstanceWorld;
import com.chujian.dungeonsByCJ.util.commons.chat.MessageUtil;
import com.chujian.dungeonsByCJ.util.commons.misc.EnumUtil;
import com.chujian.dungeonsByCJ.util.commons.misc.NumberUtil;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 * @author Frank Baumann, Milan Albrecht, Daniel Saukel
 */
public class LivesModifierSign extends Button {

    public enum Target {
        GAME,
        GROUP,
        PLAYER,
    }

    private int lives;
    private Target target;

    public LivesModifierSign(DungeonsAPI api, Sign sign, String[] lines, InstanceWorld instance) {
        super(api, sign, lines, instance);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public String getName() {
        return "Lives";
    }

    @Override
    public String getBuildPermission() {
        return DPermission.SIGN.getNode() + ".lives";
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
        return NumberUtil.parseInt(getLine(1)) != 0;
    }

    @Override
    public void initialize() {
        lives = NumberUtil.parseInt(getLine(1));
        if (EnumUtil.isValidEnum(Target.class, getLine(2).toUpperCase())) {
            target = Target.valueOf(getLine(2).toUpperCase());
        } else {
            target = Target.PLAYER;
        }
    }

    @Override
    public boolean push(Player player) {
        switch (target) {
            case GAME:
                for (Player gamePlayer : getGame().getPlayers()) {
                    GamePlayer dPlayer = api.getPlayerCache().getGamePlayer(player);
                    if (gamePlayer != null) {
                        modifyLives(dPlayer);
                    }
                }
                break;

            case GROUP:
                modifyLives(api.getPlayerGroup(player));
                break;

            case PLAYER:
                modifyLives(api.getPlayerCache().getGamePlayer(player));
        }

        return true;
    }

    public void modifyLives(GamePlayer dPlayer) {
        dPlayer.setLives(dPlayer.getLives() + lives);
        if (lives > 0) {
            MessageUtil.sendMessage(dPlayer.getPlayer(), DMessage.PLAYER_LIVES_ADDED.getMessage(String.valueOf(lives)));

        } else {
            MessageUtil.sendMessage(dPlayer.getPlayer(), DMessage.PLAYER_LIVES_REMOVED.getMessage(String.valueOf(-1 * lives)));
        }

        if (dPlayer.getLives() <= 0) {
            dPlayer.kill();
        }
    }

    public void modifyLives(PlayerGroup group) {
        group.setLives(group.getLives() + lives);
        if (lives > 0) {
            group.sendMessage(DMessage.GROUP_LIVES_ADDED.getMessage(String.valueOf(lives)));

        } else {
            group.sendMessage(DMessage.GROUP_LIVES_REMOVED.getMessage(String.valueOf(-1 * lives)));
        }
    }

}
