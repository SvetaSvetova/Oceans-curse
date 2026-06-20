package com.oceanscurse.curse;

import com.oceanscurse.OceansCurse;
import com.oceanscurse.network.OceansNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.TickEvent;

/**
 * The curse loop (server pulse). Carrying CURSED doubloons above a guilt-free amount sinks the
 * player's karma — slowly by day/indoors, much faster (and scaling with hoard size) on a cursed
 * night (overworld, dark, open sky). A clean soul (no cursed gold) slowly mends back toward neutral,
 * never past 0 — going positive takes active cleansing. At a cursed night the drowned rise to hunt
 * the cursed: spawn chance scales with how negative karma is, and none come once karma >= 0.
 *
 * Wired up in {@code OceansCurse} via {@code TickEvent.ServerTickEvent.Post.BUS}.
 */
public final class NightCurse {
    /** Act once every 5 seconds rather than every tick. */
    private static final int PULSE_TICKS = 100;

    // Corruption: carrying cursed gold above a guilt-free amount drags karma down each pulse.
    private static final int FREE_DOUBLOONS = 4;
    private static final float KARMA_LOSS_PER_DOUBLOON = 0.02F; // per pulse, by day / indoors
    private static final float NIGHT_LOSS_MULTIPLIER = 3.0F;    // a cursed night bites far harder
    private static final int MAX_KARMA_LOSS_PER_PULSE = 4;

    // Recovery: a clean soul (no cursed gold) slowly mends back toward neutral (0), never past it.
    private static final float KARMA_HEAL_CHANCE = 0.10F;

    // Drowned hunt the cursed at night; chance scales with how negative karma is, and is 0 at karma >= 0.
    private static final float SPAWN_CHANCE_PER_KARMA = 0.012F;
    private static final float SPAWN_CHANCE_CAP = 0.5F;

    /** Don't pile monsters on an AFK hoarder. */
    private static final int MAX_NEARBY_MONSTERS = 8;
    private static final int MIN_SPAWN_DIST = 8;
    private static final int MAX_SPAWN_DIST = 16;

    private static int pulse = 0;

    private NightCurse() {
    }

    public static void onServerTick(final TickEvent.ServerTickEvent.Post event) {
        if (++pulse < PULSE_TICKS) {
            return;
        }
        pulse = 0;
        for (final ServerPlayer player : event.server().getPlayerList().getPlayers()) {
            tickPlayer(player);
        }
    }

    private static void tickPlayer(final ServerPlayer player) {
        final int doubloons = countCursedDoubloons(player);
        final ServerLevel level = player.level();
        final boolean cursedNight = level.dimension() == Level.OVERWORLD
            && level.isDarkOutside()
            && level.canSeeSky(player.blockPosition());
        final RandomSource random = player.getRandom();

        boolean karmaChanged = false;

        if (doubloons > FREE_DOUBLOONS) {
            // The curse gnaws: a hoard sinks your karma — fast on a cursed night, slower otherwise.
            float loss = (doubloons - FREE_DOUBLOONS) * KARMA_LOSS_PER_DOUBLOON;
            if (cursedNight) {
                loss *= NIGHT_LOSS_MULTIPLIER;
            }
            final int amount = roundStochastic(Math.min(loss, MAX_KARMA_LOSS_PER_PULSE), random);
            if (amount > 0) {
                Karma.add(player, -amount);
                karmaChanged = true;
            }
        } else if (doubloons == 0 && Karma.get(player) < 0 && random.nextFloat() < KARMA_HEAL_CHANCE) {
            // A clean soul slowly mends toward neutral (never past 0 — that takes active cleansing).
            Karma.add(player, 1);
            karmaChanged = true;
        }

        if (karmaChanged) {
            OceansNetwork.sendKarma(player);
        }

        // The drowned rise for the cursed at night; the deeper the curse, the more of them.
        if (cursedNight) {
            final int karma = Karma.get(player);
            if (karma < 0 && random.nextFloat() < Math.min(SPAWN_CHANCE_CAP, SPAWN_CHANCE_PER_KARMA * -karma)) {
                trySpawnCursedDead(level, player, random);
            }
        }
    }

    /** Rounds a fractional amount to a whole number, using the fraction as the chance to round up. */
    private static int roundStochastic(final float value, final RandomSource random) {
        final int whole = (int) value;
        return random.nextFloat() < (value - whole) ? whole + 1 : whole;
    }

    private static int countCursedDoubloons(final ServerPlayer player) {
        int count = 0;
        final Inventory inventory = player.getInventory();
        for (final ItemStack stack : inventory.getNonEquipmentItems()) {
            if (stack.is(OceansCurse.CURSED_DOUBLOON.get())) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private static void trySpawnCursedDead(final ServerLevel level, final ServerPlayer player, final RandomSource random) {
        if (level.getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(24.0)).size() >= MAX_NEARBY_MONSTERS) {
            return;
        }
        final double angle = random.nextDouble() * Math.PI * 2.0;
        final int dist = MIN_SPAWN_DIST + random.nextInt(MAX_SPAWN_DIST - MIN_SPAWN_DIST + 1);
        final int x = Mth.floor(player.getX() + Math.cos(angle) * dist);
        final int z = Mth.floor(player.getZ() + Math.sin(angle) * dist);
        final int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
        final BlockPos pos = new BlockPos(x, y, z);
        if (!level.isLoaded(pos) || !level.canSeeSky(pos)) {
            return;
        }

        // The sea gives up the drowned; dry land raises skeletons (or a drowned wandered ashore).
        final boolean overWater = level.getFluidState(pos.below()).is(FluidTags.WATER);
        final EntityType<?> type = overWater || random.nextBoolean()
            ? OceansCurse.CURSED_DROWNED.get()
            : OceansCurse.CURSED_SKELETON.get();
        type.spawn(level, pos, EntitySpawnReason.EVENT);
    }
}
