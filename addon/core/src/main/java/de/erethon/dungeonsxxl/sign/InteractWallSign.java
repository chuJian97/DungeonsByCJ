/*
 * Copyright (C) 2020-2021 Daniel Saukel
 *
 * All rights reserved.
 */
package de.erethon.dungeonsxxl.sign;

import com.chujian.dungeonsByCJ.api.DungeonsAPI;
import com.chujian.dungeonsByCJ.api.world.InstanceWorld;
import com.chujian.dungeonsByCJ.player.DPermission;
import com.chujian.dungeonsByCJ.sign.passive.InteractSign;
import com.chujian.dungeonsByCJ.trigger.InteractTrigger;
import com.chujian.dungeonsByCJ.util.commons.misc.BlockUtil;
import com.chujian.dungeonsByCJ.util.commons.misc.NumberUtil;
import com.chujian.dungeonsByCJ.world.DGameWorld;
import org.bukkit.block.Sign;

/**
 * This sign adds an interact trigger to an attached block, like a "suspicious wall".
 *
 * @author Daniel Saukel
 */
public class InteractWallSign extends InteractSign {

    public InteractWallSign(DungeonsAPI api, Sign sign, String[] lines, InstanceWorld instance) {
        super(api, sign, lines, instance);
    }

    @Override
    public String getName() {
        return "InteractWall";
    }

    @Override
    public String getBuildPermission() {
        return DPermission.SIGN.getNode() + ".interactwall";
    }

    @Override
    public boolean isOnDungeonInit() {
        return false;
    }

    @Override
    public boolean isProtected() {
        return true;
    }

    @Override
    public boolean isSetToAir() {
        return true;
    }

    @Override
    public void initialize() {
        InteractTrigger trigger = InteractTrigger.getOrCreate(NumberUtil.parseInt(getSign().getLine(1)),
                BlockUtil.getAttachedBlock(getSign().getBlock()), (DGameWorld) getGameWorld());
        if (trigger != null) {
            trigger.addListener(this);
            addTrigger(trigger);
        }
    }

}
