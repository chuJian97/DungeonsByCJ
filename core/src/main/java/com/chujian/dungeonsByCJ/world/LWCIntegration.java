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
package com.chujian.dungeonsByCJ.world;

import com.griefcraft.lwc.LWC;
import com.griefcraft.scripting.JavaModule;
import com.griefcraft.scripting.event.LWCProtectionRegisterEvent;
import com.chujian.dungeonsByCJ.DungeonsXL;
import com.chujian.dungeonsByCJ.api.world.InstanceWorld;

/**
 * @author Daniel Saukel
 */
public class LWCIntegration extends JavaModule {

    private DungeonsXL plugin;

    public LWCIntegration(DungeonsXL plugin) {
        this.plugin = plugin;
        LWC.getInstance().getModuleLoader().registerModule(plugin, this);
    }

    @Override
    public void onRegisterProtection(LWCProtectionRegisterEvent event) {
        InstanceWorld instance = plugin.getInstanceWorld(event.getBlock().getWorld());
        if (instance != null) {
            event.setCancelled(true);
        }
    }

}
