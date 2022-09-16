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
import com.chujian.dungeonsByCJ.api.event.player.EditPlayerEditEvent;
import com.chujian.dungeonsByCJ.api.player.GlobalPlayer;
import com.chujian.dungeonsByCJ.api.player.InstancePlayer;
import com.chujian.dungeonsByCJ.api.player.PlayerGroup;
import com.chujian.dungeonsByCJ.api.world.EditWorld;
import com.chujian.dungeonsByCJ.api.world.ResourceWorld;
import com.chujian.dungeonsByCJ.config.DMessage;
import com.chujian.dungeonsByCJ.player.DEditPlayer;
import com.chujian.dungeonsByCJ.player.DPermission;
import com.chujian.dungeonsByCJ.util.commons.chat.MessageUtil;
import com.chujian.dungeonsByCJ.util.commons.config.CommonMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Frank Baumann, Milan Albrecht, Daniel Saukel
 */
public class EditCommand extends DCommand {

    public EditCommand(DungeonsXL plugin) {
        super(plugin);
        setCommand("edit");
        setMinArgs(1);
        setMaxArgs(1);
        setHelp(DMessage.CMD_EDIT_HELP.getMessage());
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        Player player = (Player) sender;

        ResourceWorld resource = plugin.getMapRegistry().getFirstIf(d -> d.getName().equalsIgnoreCase(args[1]));
        if (resource == null) {
            MessageUtil.sendMessage(sender, DMessage.ERROR_NO_SUCH_MAP.getMessage(args[1]));
            return;
        }

        if (!resource.isInvitedPlayer(player) && !DPermission.hasPermission(player, DPermission.EDIT)) {
            MessageUtil.sendMessage(player, CommonMessage.CMD_NO_PERMISSION.getMessage());
            return;
        }

        boolean newlyLoaded = resource.getEditWorld() == null;
        EditWorld editWorld = resource.getOrInstantiateEditWorld(false);
        if (editWorld == null) {
            MessageUtil.sendMessage(player, DMessage.ERROR_TOO_MANY_INSTANCES.getMessage());
            return;
        }

        PlayerGroup dGroup = plugin.getPlayerGroup(player);
        GlobalPlayer dPlayer = dPlayers.get(player);

        if (dPlayer instanceof InstancePlayer) {
            MessageUtil.sendMessage(player, DMessage.ERROR_LEAVE_DUNGEON.getMessage());
            return;
        }

        if (dGroup != null) {
            MessageUtil.sendMessage(player, DMessage.ERROR_LEAVE_GROUP.getMessage());
            return;
        }

        Bukkit.getPluginManager().callEvent(new EditPlayerEditEvent(new DEditPlayer(plugin, player, editWorld), newlyLoaded));
    }

}
