/*
 * Copyright (C) 2012-2020 Frank Baumann
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
package de.erethon.dungeonsxl.mob;

import de.erethon.dungeonsxl.DungeonsXL;
import de.erethon.dungeonsxl.api.world.EditWorld;
import de.erethon.dungeonsxl.api.world.GameWorld;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * @author Daniel Saukel, Frank Baumann
 */
public class DMobListener implements Listener {

    private DungeonsXL plugin;

    public DMobListener(DungeonsXL plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        World world = event.getLocation().getWorld();

        EditWorld editWorld = plugin.getEditWorld(world);
        GameWorld gameWorld = plugin.getGameWorld(world);

        if (editWorld != null || gameWorld != null) {
            switch (event.getSpawnReason()) {
                case CHUNK_GEN:
                case JOCKEY:
                case MOUNT:
                case NATURAL:
                    event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        World world = event.getEntity().getWorld();

        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = event.getEntity();
            GameWorld gameWorld = plugin.getGameWorld(world);
            if (gameWorld != null) {
                if (gameWorld.isPlaying()) {
                    DMob dMob = (DMob) plugin.getDungeonMob(entity);
                    if (dMob != null) {
                        dMob.onDeath(plugin, event);
                    }
                }
            }
        }
    }

    // Zombie / Skeleton combustion from the sun.
    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if (plugin.getGameWorld(event.getEntity().getWorld()) != null) {
            event.setCancelled(true);
        }
    }

    // Allow other combustion
    @EventHandler
    public void onEntityCombustByEntity(EntityCombustByEntityEvent event) {
        if (plugin.getGameWorld(event.getEntity().getWorld()) != null) {
            if (event.isCancelled()) {
                event.setCancelled(false);
            }
        }
    }

}
