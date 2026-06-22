package com.oceanscurse.client;

import net.minecraft.client.renderer.entity.DolphinRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.DolphinRenderState;
import net.minecraft.resources.Identifier;

/**
 * Renders the Shark with the vanilla dolphin model but our own shark texture.
 */
public class SharkRenderer extends DolphinRenderer {
    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath("oceanscurse", "textures/entity/shark.png");

    public SharkRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(final DolphinRenderState state) {
        return TEXTURE;
    }
}
