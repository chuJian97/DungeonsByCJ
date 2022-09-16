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

import com.chujian.dungeonsByCJ.dungeon.DDungeon;
import com.chujian.dungeonsByCJ.DungeonsXL;
import com.chujian.dungeonsByCJ.config.DMessage;
import com.chujian.dungeonsByCJ.player.DEditPlayer;
import com.chujian.dungeonsByCJ.player.DPermission;
import com.chujian.dungeonsByCJ.util.commons.chat.MessageUtil;
import com.chujian.dungeonsByCJ.world.DEditWorld;
import com.chujian.dungeonsByCJ.world.DResourceWorld;
import java.io.File;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author Frank Baumann, Daniel Saukel
 */
public class CreateCommand extends DCommand {

    public CreateCommand(DungeonsXL plugin) {
        super(plugin);
        setMinArgs(1);
        setMaxArgs(1);
        setCommand("create");
        setHelp(DMessage.CMD_CREATE_HELP.getMessage());
        setPermission(DPermission.CREATE.getNode());
        setPlayerCommand(true);
        setConsoleCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        String name = args[1];

        if (new File(DungeonsXL.MAPS, name).exists()) {
            MessageUtil.sendMessage(sender, DMessage.ERROR_NAME_IN_USE.getMessage(name));
            return;
        }

        if (name.length() > 15) {
            MessageUtil.sendMessage(sender, DMessage.ERROR_NAME_TOO_LONG.getMessage());
            return;
        }

        if (sender instanceof ConsoleCommandSender) {
            MessageUtil.log(plugin, "&6Creating new map.");
            MessageUtil.log(plugin, "&6Generating new world...");

            DResourceWorld resource = new DResourceWorld(plugin, name);
            plugin.getMapRegistry().add(name, resource);
            DEditWorld editWorld = resource.generate();
            editWorld.save();
            editWorld.delete();

            MessageUtil.log(plugin, "&6World generation finished.");

        } else if (sender instanceof Player) {
            Player player = (Player) sender;

            if (dPlayers.getGamePlayer(player) != null) {
                MessageUtil.sendMessage(player, DMessage.ERROR_LEAVE_DUNGEON.getMessage());
                return;
            }

            MessageUtil.log(plugin, "&6Creating new map.");
            MessageUtil.log(plugin, "&6Generating new world...");

            DResourceWorld resource = new DResourceWorld(plugin, name);
            plugin.getMapRegistry().add(name, resource);
            plugin.getDungeonRegistry().add(name, new DDungeon(plugin, resource));
            DEditWorld editWorld = resource.generate();

            MessageUtil.log(plugin, "&6World generation finished.");

            new DEditPlayer(plugin, player, editWorld);
        }
    }

}
