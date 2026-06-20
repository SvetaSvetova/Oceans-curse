package com.oceanscurse.curse;

import com.oceanscurse.network.OceansNetwork;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Forge-bus hooks for {@link Karma}: keep it attached across a new player instance, and push the
 * value to the client (for the HUD) whenever the client would otherwise not know it. Wired up in
 * {@code OceansCurse}.
 */
public final class KarmaEvents {
    private KarmaEvents() {
    }

    public static void onPlayerClone(final PlayerEvent.Clone event) {
        // Copy for both death and dimension change so the curse is never silently lost.
        Karma.copy(event.getOriginal(), event.getEntity());
    }

    public static void onLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        sync(event.getEntity());
    }

    public static void onRespawn(final PlayerEvent.PlayerRespawnEvent event) {
        sync(event.getEntity());
    }

    public static void onChangedDimension(final PlayerEvent.PlayerChangedDimensionEvent event) {
        sync(event.getEntity());
    }

    private static void sync(final Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            OceansNetwork.sendKarma(serverPlayer);
        }
    }
}
