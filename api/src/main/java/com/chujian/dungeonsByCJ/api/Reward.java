/*
 * Copyright (C) 2014-2021 Daniel Saukel
 *
 * This library is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNULesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.chujian.dungeonsByCJ.api;

import com.chujian.dungeonsByCJ.api.dungeon.Dungeon;
import com.chujian.dungeonsByCJ.api.player.PlayerGroup;
import org.bukkit.entity.Player;

/**
 * Something players are given when they successfully finish a {@link Dungeon}.
 * <p>
 * @see PlayerGroup#getRewards()
 * @author Daniel Saukel
 */
public interface Reward {

    /**
     * Gives the reward to the given player.
     *
     * @param player the player
     */
    void giveTo(Player player);

}
