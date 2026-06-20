package com.oceanscurse.entity;

import com.oceanscurse.OceansCurse;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/**
 * Cursed Skeleton — a cursed-dead archer raised by the curse on land (the drowned hold the water).
 * Reuses the vanilla skeleton (bow AI + renderer); tougher, and drops cursed gold.
 *
 * Weapon variety on spawn: a bow firing Weakness arrows (its signature), the cutlass for melee, or
 * unarmed — so the strong cutlass isn't on every one. Own texture comes later.
 */
public class CursedSkeleton extends Skeleton {
    private static final double MAX_HEALTH = 24.0;
    private static final float CUTLASS_DROP_CHANCE = 0.08F;

    // Spawn loadout weights (bow / cutlass / the remainder unarmed).
    private static final float BOW_CHANCE = 0.6F;
    private static final float CUTLASS_CHANCE = 0.2F;

    /** Weakness applied by its arrows, in ticks (7 seconds). */
    private static final int WEAKNESS_DURATION = 140;

    public CursedSkeleton(final EntityType<? extends Skeleton> type, final Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractSkeleton.createAttributes();
    }

    @Override
    public SpawnGroupData finalizeSpawn(final ServerLevelAccessor level, final DifficultyInstance difficulty,
                                        final EntitySpawnReason spawnReason, final SpawnGroupData groupData) {
        final SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, groupData);

        // Tougher than a common skeleton.
        final AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(MAX_HEALTH);
            this.setHealth((float) MAX_HEALTH);
        }

        // Pick a weapon, then let the skeleton re-choose its ranged/melee goal accordingly.
        final float roll = this.getRandom().nextFloat();
        if (roll < BOW_CHANCE) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (roll < BOW_CHANCE + CUTLASS_CHANCE) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(OceansCurse.ABORDAGE_CUTLASS.get()));
            this.setDropChance(EquipmentSlot.MAINHAND, CUTLASS_DROP_CHANCE);
        } else {
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
        this.reassessWeaponGoal();

        return data;
    }

    @Override
    protected AbstractArrow getArrow(final ItemStack projectile, final float power, final ItemStack firingWeapon) {
        final AbstractArrow arrow = super.getArrow(projectile, power, firingWeapon);
        // The curse's arrows sap the living: a touch of Weakness.
        if (arrow instanceof Arrow tippedArrow) {
            tippedArrow.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, WEAKNESS_DURATION, 0));
        }
        return arrow;
    }
}
