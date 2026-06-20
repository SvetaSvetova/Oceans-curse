package com.oceanscurse.client;

/**
 * Client-side cache of the local player's karma, fed by {@link com.oceanscurse.network.KarmaSyncMessage}
 * and read by {@link KarmaHudLayer}.
 *
 * Deliberately free of client-only imports so the (common) network message handler can reference it
 * on both physical sides without dragging client classes onto a dedicated server.
 */
public final class ClientKarma {
    private static int karma = 0;

    private ClientKarma() {
    }

    public static void set(final int value) {
        karma = value;
    }

    public static int get() {
        return karma;
    }
}
