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
package com.chujian.dungeonsByCJ.api.event.player;

import com.chujian.dungeonsByCJ.api.player.GamePlayer;

/**
 * Superclass for events involving {@link GamePlayer}s.
 *
 * @author Daniel Saukel
 */
public abstract class GamePlayerEvent extends GlobalPlayerEvent {

    protected GamePlayerEvent(GamePlayer gamePlayer) {
        super(gamePlayer);
    }

    /**
     * Returns the GamePlayer involved in this event.
     *
     * @return the GamePlayer involved in this event
     */
    public GamePlayer getGamePlayer() {
        return (GamePlayer) globalPlayer;
    }

}