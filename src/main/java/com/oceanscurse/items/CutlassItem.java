package com.oceanscurse.items;

import com.oceanscurse.OceansCurse;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * The Abordage Cutlass — a pirate sword whose victims bleed.
 *
 * Stats mirror an iron sword (set via {@code Item.Properties.sword(...)} at registration);
 * its bite is the {@link com.oceanscurse.effects.BleedingMobEffect} applied on every hit.
 */
public class CutlassItem extends Item {
    /** How long the bleed lingers after a hit (5 seconds). */
    private static final int BLEED_DURATION_TICKS = 100;
    /** Amplifier 0 = bleed level I (one tick of damage per second). */
    private static final int BLEED_AMPLIFIER = 0;

    public CutlassItem(final Properties properties) {
        super(properties);
    }

    @Override
    public void postHurtEnemy(final ItemStack stack, final LivingEntity target, final LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);

        // Only the server applies the effect; it syncs to the client on its own.
        if (!target.level().isClientSide()) {
            target.addEffect(new MobEffectInstance(
                OceansCurse.BLEEDING.getHolder().orElseThrow(),
                BLEED_DURATION_TICKS,
                BLEED_AMPLIFIER));
        }
    }
}
