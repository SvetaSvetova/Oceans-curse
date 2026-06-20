package com.oceanscurse.client;

import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;

/**
 * Client-only entry point. Loaded and called only when {@code FMLEnvironment.dist == Dist.CLIENT}
 * (guarded in {@code OceansCurse}), so a dedicated server never touches client classes.
 */
public final class OceansClient {
    private OceansClient() {
    }

    public static void init() {
        AddGuiOverlayLayersEvent.BUS.addListener(KarmaHudLayer::onAddLayers);
    }
}
