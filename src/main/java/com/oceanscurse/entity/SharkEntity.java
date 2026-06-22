package com.oceanscurse.entity;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.dolphin.Dolphin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/**
 * Shark — a predatory sea creature. Reuses the vanilla dolphin's body/swimming (and its own texture);
 * tougher and hostile: it stalks and bites players in the water. (v0.4 first fauna mob; the
 * blood-frenzy and fin/scale drops come later.)
 */
public class SharkEntity extends Dolphin {
    private static final double MAX_HEALTH = 30.0;
    private static final double ATTACK_DAMAGE = 6.0;

    public SharkEntity(final EntityType<? extends Dolphin> type, final Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Dolphin.createAttributes();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.6, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
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
        final AttributeInstance attack = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null) {
            attack.setBaseValue(ATTACK_DAMAGE);
        }
        return data;
    }
}
