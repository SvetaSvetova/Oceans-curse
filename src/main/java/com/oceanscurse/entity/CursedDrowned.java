package com.oceanscurse.entity;

import com.oceanscurse.OceansCurse;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/**
 * Cursed Drowned — the mod's own restless dead, raised by the curse at night in place of the vanilla
 * drowned. Tougher, and it wields the Abordage Cutlass.
 *
 * v0.2 step 1: reuses the vanilla drowned model/renderer (so it visibly holds the cutlass); its own
 * texture and the chain-wielding variant come in later steps.
 */
public class CursedDrowned extends Drowned {
    private static final double MAX_HEALTH = 24.0;
    private static final float CUTLASS_DROP_CHANCE = 0.08F;
    /** The rest spawn unarmed — the cutlass is strong, so it shouldn't be on every one of them. */
    private static final float CUTLASS_CHANCE = 0.45F;

    public CursedDrowned(final EntityType<? extends Drowned> type, final Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Drowned.createAttributes();
    }

    @Override
    public SpawnGroupData finalizeSpawn(final ServerLevelAccessor level, final DifficultyInstance difficulty,
                                        final EntitySpawnReason spawnReason, final SpawnGroupData groupData) {
        final SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, groupData);

        // Tougher than a common drowned.
        final AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(MAX_HEALTH);
            this.setHealth((float) MAX_HEALTH);
        }

        // Weapon variety: some carry the cutlass (in place of the drowned's trident), the rest are
        // unarmed for balance. (The chain variant joins here in a later step.)
        if (this.getRandom().nextFloat() < CUTLASS_CHANCE) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(OceansCurse.ABORDAGE_CUTLASS.get()));
            this.setDropChance(EquipmentSlot.MAINHAND, CUTLASS_DROP_CHANCE);
        } else {
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        return data;
    }
}
