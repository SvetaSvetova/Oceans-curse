package com.oceanscurse.world;

import java.util.Optional;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

/**
 * Worldgen handles for Ocean's Curse. The palm tree itself is defined as a datapack
 * configured feature ({@code data/oceanscurse/worldgen/configured_feature/palm.json}); this just
 * holds the key + the {@link TreeGrower} that the palm sapling uses to grow it.
 */
public final class OceansWorldgen {
    private OceansWorldgen() {
    }

    // Must match the configured_feature JSON path (oceanscurse:palm).
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_TREE =
        ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath("oceanscurse", "palm"));

    // The palm sapling grows the PALM_TREE configured feature.
    public static final TreeGrower PALM_GROWER =
        new TreeGrower("oceanscurse:palm", Optional.empty(), Optional.of(PALM_TREE), Optional.empty());
}
