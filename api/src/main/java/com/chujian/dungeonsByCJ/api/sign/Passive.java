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
package com.chujian.dungeonsByCJ.api.sign;

import com.chujian.dungeonsByCJ.api.world.InstanceWorld;
import com.chujian.dungeonsByCJ.api.DungeonsAPI;
import com.chujian.dungeonsByCJ.api.Trigger;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 * A sign that does not do anything on its own. Its function is mostly to mark locations or blocks, like lobby or bed signs.
 *
 * @author Daniel Saukel
 */
public abstract class Passive extends AbstractDSign {

    protected Passive(DungeonsAPI api, Sign sign, String[] lines, InstanceWorld instance) {
        super(api, sign, lines, instance);
    }

    /**
     * Does nothing.
     *
     * @param lastFired unused
     */
    @Override
    public final void updateTriggers(Trigger lastFired) {
    }

    /**
     * Does nothing.
     *
     * @param player unused
     */
    @Override
    public final void trigger(Player player) {
    }

}
