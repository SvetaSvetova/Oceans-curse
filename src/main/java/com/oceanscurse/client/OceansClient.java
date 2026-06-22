package com.oceanscurse.client;

import com.oceanscurse.OceansCurse;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;

/**
 * Client-only entry point. Loaded and called only when {@code FMLEnvironment.dist == Dist.CLIENT}
 * (guarded in {@code OceansCurse}), so a dedicated server never touches client classes.
 */
public final class OceansClient {
    private OceansClient() {
    }

    public static void init() {
        AddGuiOverlayLayersEvent.BUS.addListener(KarmaHudLayer::onAddLayers);
        EntityRenderersEvent.RegisterRenderers.BUS.addListener(OceansClient::onRegisterRenderers);
        EntityRenderersEvent.RegisterLayerDefinitions.BUS.addListener(OceansClient::onRegisterLayers);
    }

    private static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        // Vanilla models, our own textures.
        event.registerEntityRenderer(OceansCurse.CURSED_DROWNED.get(), CursedDrownedRenderer::new);
        event.registerEntityRenderer(OceansCurse.CURSED_SKELETON.get(), CursedSkeletonRenderer::new);
        event.registerEntityRenderer(OceansCurse.SHARK.get(), SharkRenderer::new);
        event.registerEntityRenderer(OceansCurse.SAWFISH.get(), SawfishRenderer::new);
        event.registerEntityRenderer(OceansCurse.PIRANHA.get(), PiranhaRenderer::new);
        // Custom Blockbench model.
        event.registerEntityRenderer(OceansCurse.WHALE.get(), WhaleRenderer::new);
    }

    private static void onRegisterLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WhaleModel.LAYER, WhaleModel::createBodyLayer);
    }
}
