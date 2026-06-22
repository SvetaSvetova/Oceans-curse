package com.oceanscurse.client;

import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

/**
 * Renders the Sawfish with the vanilla cod model but our own texture.
 */
public class SawfishRenderer extends CodRenderer {
    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath("oceanscurse", "textures/entity/sawfish.png");

    public SawfishRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(final LivingEntityRenderState state) {
        return TEXTURE;
    }
}
