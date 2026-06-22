package com.oceanscurse.client;

import com.oceanscurse.OceansCurse;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.resources.Identifier;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;

/**
 * Client-only entry point. Loaded and called only when {@code FMLEnvironment.dist == Dist.CLIENT}
 * (guarded in {@code OceansCurse}), so a dedicated server never touches client classes.
 */
public final class OceansClient {
    // Boat layer: BoatRenderer derives the texture from this path → textures/entity/boat/palm.png.
    private static final ModelLayerLocation PALM_BOAT_LAYER =
        new ModelLayerLocation(Identifier.fromNamespaceAndPath("oceanscurse", "boat/palm"), "main");

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
        // Custom Blockbench models.
        event.registerEntityRenderer(OceansCurse.WHALE.get(), WhaleRenderer::new);
        event.registerEntityRenderer(OceansCurse.RAY.get(), RayRenderer::new);
        // Palm boat: vanilla boat model + renderer, our own palm-wood texture.
        event.registerEntityRenderer(OceansCurse.PALM_BOAT.get(), context -> new BoatRenderer(context, PALM_BOAT_LAYER));
    }

    private static void onRegisterLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WhaleModel.LAYER, WhaleModel::createBodyLayer);
        event.registerLayerDefinition(RayModel.LAYER, RayModel::createBodyLayer);
        event.registerLayerDefinition(PALM_BOAT_LAYER, BoatModel::createBoatModel);
    }
}
