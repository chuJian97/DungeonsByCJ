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
import de.erethon.dungeonsxxl.util.FireworkUtil;
import org.bukkit.block.Sign;

/**
 * @author Daniel Saukel
 */
public class FireworkSign extends Button {

    public FireworkSign(DungeonsAPI api, Sign sign, String[] lines, InstanceWorld instance) {
        super(api, sign, lines, instance);
    }

    @Override
    public String getName() {
        return "Firework";
    }

    @Override
    public String getBuildPermission() {
        return DPermission.SIGN.getNode() + ".firework";
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
        return true;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void push() {
        FireworkUtil.spawnRandom(getSign().getLocation());
    }

}
