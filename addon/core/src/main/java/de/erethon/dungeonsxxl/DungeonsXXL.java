/*
 * Copyright (C) 2020-2021 Daniel Saukel
 *
 * All rights reserved.
 */
package de.erethon.dungeonsxxl;

import com.chujian.dungeonsByCJ.DungeonsXL;
import com.chujian.dungeonsByCJ.api.DungeonModule;
import com.chujian.dungeonsByCJ.api.Requirement;
import com.chujian.dungeonsByCJ.api.Reward;
import com.chujian.dungeonsByCJ.api.dungeon.GameRule;
import com.chujian.dungeonsByCJ.api.sign.DungeonSign;
import com.chujian.dungeonsByCJ.util.commons.compatibility.Internals;
import com.chujian.dungeonsByCJ.util.commons.javaplugin.DREPlugin;
import com.chujian.dungeonsByCJ.util.commons.javaplugin.DREPluginSettings;
import com.chujian.dungeonsByCJ.util.commons.misc.Registry;
import de.erethon.dungeonsxxl.requirement.*;
import de.erethon.dungeonsxxl.sign.*;
import de.erethon.dungeonsxxl.util.GlowUtil;

/**
 * @author Daniel Saukel
 */
public class DungeonsXXL extends DREPlugin implements DungeonModule {

    private DungeonsXL dxl;
    private GlowUtil glowUtil;

    public DungeonsXXL() {
        settings = DREPluginSettings.builder()
                .internals(Internals.v1_15_R1)
                .metrics(false)
                .spigotMCResourceId(-1)
                .build();
    }

    @Override
    public void onEnable() {
        dxl = DungeonsXL.getInstance();
        glowUtil = new GlowUtil(this);
    }

    /**
     * Returns the instance of this plugin.
     *
     * @return the instance of this plugin
     */
    public static DungeonsXXL getInstance() {
        return (DungeonsXXL) DREPlugin.getInstance();
    }

    /**
     * Returns the current {@link com.chujian.dungeonsByCJ.DungeonsXL} singleton.
     *
     * @return the current {@link com.chujian.dungeonsByCJ.DungeonsXL} singleton
     */
    public DungeonsXL getDXL() {
        return dxl;
    }

    /**
     * The loaded instance of GlowUtil.
     *
     * @return the loaded instance of GlowUtil
     */
    public GlowUtil getGlowUtil() {
        return glowUtil;
    }

    @Override
    public void initRequirements(Registry<String, Class<? extends Requirement>> registry) {
        registry.add("feeItems", FeeItemsRequirement.class);
    }

    @Override
    public void initRewards(Registry<String, Class<? extends Reward>> registry) {
    }

    @Override
    public void initSigns(Registry<String, Class<? extends DungeonSign>> registry) {
        registry.add("FIREWORK", FireworkSign.class);
        registry.add("GLOWINGBLOCK", GlowingBlockSign.class);
        registry.add("INTERACTWALL", InteractWallSign.class);
        registry.add("PARTICLE", ParticleSign.class);
    }

    @Override
    public void initGameRules(Registry<String, GameRule> registry) {
    }

}
