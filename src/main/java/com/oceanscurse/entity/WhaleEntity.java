package com.oceanscurse.entity;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.dolphin.Dolphin;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/**
 * Whale — a large, peaceful sea giant. Reuses the vanilla dolphin's swimming/surfacing AI (a whale
 * surfacing to breathe even fits), but is far tougher and unarmed: it never targets the player.
 * Drops whale meat (food) and whale bone (the material that unlocks the cutlass recipe).
 * Uses a custom Blockbench model (see {@link com.oceanscurse.client.WhaleModel}).
 */
public class WhaleEntity extends Dolphin {
    private static final double MAX_HEALTH = 60.0;

    public WhaleEntity(final EntityType<? extends Dolphin> type, final Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Dolphin.createAttributes();
    }

    @Override
    public SpawnGroupData finalizeSpawn(final ServerLevelAccessor level, final DifficultyInstance difficulty,
                                        final EntitySpawnReason spawnReason, final SpawnGroupData groupData) {
        final SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, groupData);

        final AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(MAX_HEALTH);
            this.setHealth((float) MAX_HEALTH);
        }
        return data;
    }
}
