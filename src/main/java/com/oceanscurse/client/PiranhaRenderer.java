package com.oceanscurse.client;

import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

/**
 * Renders the Piranha with the vanilla cod model but our own texture.
 */
public class PiranhaRenderer extends CodRenderer {
    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath("oceanscurse", "textures/entity/piranha.png");

    public PiranhaRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(final LivingEntityRenderState state) {
        return TEXTURE;
    }
}
