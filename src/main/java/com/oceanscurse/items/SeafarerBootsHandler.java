package com.oceanscurse.items;

import com.oceanscurse.OceansCurse;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

/**
 * Gives the Seafarer's Boots their magic: while you wear them and are in water, you keep Dolphin's
 * Grace (fast swimming). The effect is refreshed each tick the conditions hold and lapses on land.
 */
public final class SeafarerBootsHandler {
    private static final int GRACE_DURATION = 60;
    private static final int REFRESH_BELOW = 20;

    private SeafarerBootsHandler() {
    }

    public static void onLivingTick(final LivingEvent.LivingTickEvent event) {
        final LivingEntity entity = event.getEntity();
        // Effects are server-authoritative; only the cheap water check runs for everyone else.
        if (entity.level().isClientSide() || !entity.isInWater()) {
            return;
        }
        if (!entity.getItemBySlot(EquipmentSlot.FEET).is(OceansCurse.SEAFARER_BOOTS.get())) {
            return;
        }
        final MobEffectInstance current = entity.getEffect(MobEffects.DOLPHINS_GRACE);
        if (current == null || current.getDuration() < REFRESH_BELOW) {
            entity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, GRACE_DURATION, 0, true, false, false));
        }
    }
}
