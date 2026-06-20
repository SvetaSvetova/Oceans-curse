package com.oceanscurse.curse;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

/**
 * Per-player "curse meter" (karma).
 *
 * Stored in the player's Forge persistent data so it survives relog; {@link KarmaEvents}
 * copies it across death and dimension change. Negative = darker (hoarding cursed gold),
 * positive = lighter (cleansing it / returning gold to the sea). Each player has their own
 * value, so in multiplayer one player can be deep in the curse while another stays clean.
 */
public final class Karma {
    /** Key inside {@code player.getPersistentData()}. Namespaced to avoid clashes. */
    private static final String KARMA_KEY = "oceanscurse:karma";

    public static final int MIN = -100;
    public static final int MAX = 100;

    private Karma() {
    }

    public static int get(final Player player) {
        return player.getPersistentData().getIntOr(KARMA_KEY, 0);
    }

    public static void set(final Player player, final int value) {
        player.getPersistentData().putInt(KARMA_KEY, Mth.clamp(value, MIN, MAX));
    }

    /** Adds {@code delta} (may be negative), clamps, and returns the new value. */
    public static int add(final Player player, final int delta) {
        final int next = Mth.clamp(get(player) + delta, MIN, MAX);
        player.getPersistentData().putInt(KARMA_KEY, next);
        return next;
    }

    /** Carries the curse from one player instance to another (respawn / dimension change). */
    public static void copy(final Player from, final Player to) {
        if (from.getPersistentData().contains(KARMA_KEY)) {
            to.getPersistentData().putInt(KARMA_KEY, get(from));
        }
    }
}
