package com.oceanscurse.effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * Bleeding — deals magic (armour-piercing) damage on a fixed interval.
 *
 * Applied by the {@link com.oceanscurse.items.CutlassItem} on hit. Modelled on vanilla
 * Poison/Wither: a small amount of damage that ignores armour, ticking once per second.
 */
public class BleedingMobEffect extends MobEffect {
    /** Damage ticks once per second at amplifier 0 (halved each level above). */
    private static final int DAMAGE_INTERVAL_TICKS = 20;
    /** Damage dealt each interval. */
    private static final float DAMAGE_PER_INTERVAL = 1.0F;

    public BleedingMobEffect(final MobEffectCategory category, final int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(final ServerLevel level, final LivingEntity mob, final int amplification) {
        mob.hurtServer(level, mob.damageSources().magic(), DAMAGE_PER_INTERVAL);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(final int tickCount, final int amplification) {
        final int interval = DAMAGE_INTERVAL_TICKS >> amplification;
        return interval > 0 ? tickCount % interval == 0 : true;
    }
}
