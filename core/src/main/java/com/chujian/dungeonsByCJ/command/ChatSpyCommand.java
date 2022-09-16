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
import com.chujian.dungeonsByCJ.api.player.GlobalPlayer;
import com.chujian.dungeonsByCJ.config.DMessage;
import com.chujian.dungeonsByCJ.player.DPermission;
import com.chujian.dungeonsByCJ.util.commons.chat.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Frank Baumann, Daniel Saukel
 */
public class ChatSpyCommand extends DCommand {

    public ChatSpyCommand(DungeonsXL plugin) {
        super(plugin);
        setCommand("chatSpy");
        setMinArgs(0);
        setMaxArgs(0);
        setHelp(DMessage.CMD_CHATSPY_HELP.getMessage());
        setPermission(DPermission.CHAT_SPY.getNode());
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        Player player = (Player) sender;
        GlobalPlayer dPlayer = dPlayers.get(player);

        dPlayer.setInChatSpyMode(!dPlayer.isInChatSpyMode());
        MessageUtil.sendMessage(player, (dPlayer.isInChatSpyMode() ? DMessage.CMD_CHATSPY_STARTED : DMessage.CMD_CHATSPY_STOPPED).getMessage());
    }

}
