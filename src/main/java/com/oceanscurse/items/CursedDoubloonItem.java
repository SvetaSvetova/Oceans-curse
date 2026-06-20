package com.oceanscurse.items;

import com.oceanscurse.OceansCurse;
import com.oceanscurse.curse.Karma;
import com.oceanscurse.network.OceansNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * Cursed Doubloon — the cursed gold at the heart of the mod.
 *
 * Right-clicking water "returns it to the sea": one cursed doubloon is given back to the cursed
 * deep (its rightful home), surfacing as a Cleansed Doubloon, and the player's karma lifts a little.
 * Echoing the films, it costs a drop of blood. The sea itself stays cursed — lifting THAT is the
 * endgame's problem, not gold's.
 */
public class CursedDoubloonItem extends Item {
    private static final int CLEANSE_KARMA = 2;
    private static final float BLOOD_COST = 1.0F; // half a heart
    private static final int COOLDOWN_TICKS = 10;

    public CursedDoubloonItem(final Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        final ItemStack held = player.getItemInHand(hand);
        final BlockHitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (hit.getType() != HitResult.Type.BLOCK || !level.getFluidState(hit.getBlockPos()).is(FluidTags.WATER)) {
            return InteractionResult.PASS;
        }

        if (level instanceof ServerLevel serverLevel) {
            returnToSea(serverLevel, player, held, hit.getBlockPos());
        }
        player.getCooldowns().addCooldown(held, COOLDOWN_TICKS);
        return InteractionResult.SUCCESS;
    }

    private void returnToSea(final ServerLevel level, final Player player, final ItemStack held, final BlockPos waterPos) {
        held.shrink(1);

        final ItemStack cleansed = new ItemStack(OceansCurse.CLEANSED_DOUBLOON.get());
        if (!player.addItem(cleansed)) {
            player.drop(cleansed, false);
        }

        // A drop of blood — never enough to kill, skipped in creative.
        if (!player.isCreative() && player.getHealth() > BLOOD_COST + 1.0F) {
            player.hurtServer(level, player.damageSources().generic(), BLOOD_COST);
        }

        if (player instanceof ServerPlayer serverPlayer) {
            Karma.add(serverPlayer, CLEANSE_KARMA);
            OceansNetwork.sendKarma(serverPlayer);
        }

        level.sendParticles(ParticleTypes.SPLASH,
            waterPos.getX() + 0.5, waterPos.getY() + 1.0, waterPos.getZ() + 0.5,
            12, 0.3, 0.1, 0.3, 0.0);
        level.playSound(null,
            waterPos.getX() + 0.5, waterPos.getY() + 1.0, waterPos.getZ() + 0.5,
            SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 0.8F, 1.2F);
    }
}
