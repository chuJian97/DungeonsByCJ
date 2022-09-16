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
import com.chujian.dungeonsByCJ.config.DMessage;
import com.chujian.dungeonsByCJ.player.DPermission;
import com.chujian.dungeonsByCJ.util.commons.chat.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Frank Baumann, Daniel Saukel
 */
public class KickCommand extends DCommand {

    public KickCommand(DungeonsXL plugin) {
        super(plugin);
        setCommand("kick");
        setMinArgs(1);
        setMaxArgs(1);
        setHelp(DMessage.CMD_KICK_HELP.getMessage());
        setPermission(DPermission.KICK.getNode());
        setPlayerCommand(true);
        setConsoleCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        Player player = Bukkit.getPlayer(args[1]);

        if (player != null) {
            plugin.getCommandCache().leave.onExecute(new String[]{plugin.getCommandCache().leave.getCommand()}, player);
            MessageUtil.sendMessage(sender, DMessage.CMD_KICK_SUCCESS.getMessage(player.getName()));

        } else {
            MessageUtil.sendMessage(sender, DMessage.ERROR_NO_SUCH_PLAYER.getMessage(args[1]));
        }
    }

}
