/*
 * Copyright (C) 2020-2021 Daniel Saukel
 *
 * All rights reserved.
 */
package de.erethon.dungeonsxxl.sign;

import com.chujian.dungeonsByCJ.api.DungeonsAPI;
import com.chujian.dungeonsByCJ.api.sign.Button;
import com.chujian.dungeonsByCJ.api.world.InstanceWorld;
import com.chujian.dungeonsByCJ.player.DPermission;
import com.chujian.dungeonsByCJ.util.commons.misc.EnumUtil;
import com.chujian.dungeonsByCJ.util.commons.misc.NumberUtil;
import org.bukkit.Particle;
import org.bukkit.block.Sign;

/**
 * Spawns particles.
 *
 * @author Daniel Saukel
 */
public class ParticleSign extends Button {

    private Particle particle;
    private int count;
    private double offsetX, offsetY, offsetZ;
    private double extra = 1;

    public ParticleSign(DungeonsAPI api, Sign sign, String[] lines, InstanceWorld instance) {
        super(api, sign, lines, instance);
    }

    @Override
    public String getName() {
        return "Particle";
    }

    @Override
    public String getBuildPermission() {
        return DPermission.SIGN.getNode() + ".particle";
    }

    @Override
    public boolean isOnDungeonInit() {
        return false;
    }

    @Override
    public boolean isProtected() {
        return false;
    }

    @Override
    public boolean isSetToAir() {
        return true;
    }

    @Override
    public boolean validate() {
        particle = EnumUtil.getEnumIgnoreCase(Particle.class, getLine(1));
        if (particle == null) {
            markAsErroneous("Unknown particle type: " + getLine(1));
            return false;
        }
        return true;
    }

    @Override
    public void initialize() {
        String[] args = getLine(2).split(",");
        if (args.length == 1) {
            extra = NumberUtil.parseDouble(args[0], 1);
        } else if (args.length >= 3) {
            offsetX = NumberUtil.parseDouble(args[0], 0);
            offsetX = NumberUtil.parseDouble(args[1], 0);
            offsetX = NumberUtil.parseDouble(args[2], 0);
            if (args.length == 4) {
                extra = NumberUtil.parseDouble(args[3], 1);
            }
        }
    }

    @Override
    public void push() {
        getSign().getWorld().spawnParticle(particle, getSign().getLocation(), count, offsetX, offsetY, offsetZ, extra);
    }

}
