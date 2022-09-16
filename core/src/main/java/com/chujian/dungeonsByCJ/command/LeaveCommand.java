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
package com.chujian.dungeonsByCJ.command;

import com.chujian.dungeonsByCJ.DungeonsXL;
import com.chujian.dungeonsByCJ.api.dungeon.Game;
import com.chujian.dungeonsByCJ.api.event.group.GroupPlayerLeaveEvent;
import com.chujian.dungeonsByCJ.api.event.player.EditPlayerLeaveEvent;
import com.chujian.dungeonsByCJ.api.player.EditPlayer;
import com.chujian.dungeonsByCJ.api.player.GamePlayer;
import com.chujian.dungeonsByCJ.api.player.GlobalPlayer;
import com.chujian.dungeonsByCJ.api.player.PlayerGroup;
import com.chujian.dungeonsByCJ.config.DMessage;
import com.chujian.dungeonsByCJ.player.DPermission;
import com.chujian.dungeonsByCJ.util.commons.chat.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Frank Baumann, Daniel Saukel
 */
public class LeaveCommand extends DCommand {

    public LeaveCommand(DungeonsXL plugin) {
        super(plugin);
        setCommand("leave");
        setMinArgs(0);
        setMaxArgs(0);
        setHelp(DMessage.CMD_LEAVE_HELP.getMessage());
        setPermission(DPermission.LEAVE.getNode());
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        Player player = (Player) sender;
        GlobalPlayer globalPlayer = dPlayers.get(player);
        Game game = plugin.getGame(player);

        if (game != null && game.isTutorial()) {
            MessageUtil.sendMessage(player, DMessage.ERROR_NO_LEAVE_IN_TUTORIAL.getMessage());
            return;
        }

        PlayerGroup group = globalPlayer.getGroup();

        if (group == null && !(globalPlayer instanceof EditPlayer)) {
            MessageUtil.sendMessage(player, DMessage.ERROR_JOIN_GROUP.getMessage());
            return;
        }

        if (globalPlayer instanceof GamePlayer) {
            GroupPlayerLeaveEvent event = new GroupPlayerLeaveEvent(group, globalPlayer);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            ((GamePlayer) globalPlayer).leave();
        } else if (globalPlayer instanceof EditPlayer) {
            EditPlayerLeaveEvent event = new EditPlayerLeaveEvent((EditPlayer) globalPlayer, false, true);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            ((EditPlayer) globalPlayer).leave(event.getUnloadIfEmpty());
        } else {
            group.removeMember(player);
        }

        MessageUtil.sendMessage(player, DMessage.CMD_LEAVE_SUCCESS.getMessage());
    }

}
