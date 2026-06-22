package com.oceanscurse.entity;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.animal.fish.Cod;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/**
 * Ray — a flat, peaceful bottom-glider. Reuses the cod's water-staying/schooling AI (so it never
 * surfaces like a dolphin would), but is bigger and unarmed. Uses a custom Blockbench model
 * (see {@link com.oceanscurse.client.RayModel}). Drops ray meat (and later the electric spike).
 */
public class RayEntity extends Cod {
    private static final double MAX_HEALTH = 10.0;

    public RayEntity(final EntityType<? extends Cod> type, final Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractFish.createAttributes();
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
