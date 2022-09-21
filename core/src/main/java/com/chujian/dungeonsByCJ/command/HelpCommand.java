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
import com.chujian.dungeonsByCJ.util.commons.command.DRECommand;
import com.chujian.dungeonsByCJ.util.commons.misc.NumberUtil;
import java.util.ArrayList;
import java.util.Set;
import org.bukkit.command.CommandSender;

/**
 * @author Frank Baumann, Daniel Saukel
 */
public class HelpCommand extends DCommand {

    public HelpCommand(DungeonsXL plugin) {
        super(plugin);
        setCommand("help");
        setMinArgs(0);
        setMaxArgs(1);
        setHelp(DMessage.CMD_HELP_HELP.getMessage());
        setPermission(DPermission.HELP.getNode());
        setPlayerCommand(true);
        setConsoleCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        Set<DRECommand> dCommandList = plugin.getCommandCache().getCommands();
        ArrayList<DRECommand> toSend = new ArrayList<>();

        int page = 1;
        if (args.length == 2) {
            page = NumberUtil.parseInt(args[1], 1);
        }
        int send = 0;
        int max = 0;
        int min = 0;
        for (DRECommand dCommand : dCommandList) {
            send++;
            if (send >= page * 10 - 9 && send <= page * 10) {
                min = page * 10 - 9;
                max = page * 10;
                toSend.add(dCommand);
            }
        }

        MessageUtil.sendPluginTag(sender, plugin);
        MessageUtil.sendCenteredMessage(sender, "&4&l[ &6" + min + "-" + max + " &4/&6 " + send + " &4|&6 " + page + " &4&l]");

        for (DRECommand dCommand : toSend) {
            MessageUtil.sendMessage(sender, "&b" + dCommand.getCommand() + "&7 - " + dCommand.getHelp());
        }
    }

}
