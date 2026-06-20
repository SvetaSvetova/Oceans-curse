package com.oceanscurse.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.Identifier;

/**
 * Renders the Cursed Skeleton with the vanilla skeleton model but our own "cursed" texture.
 */
public class CursedSkeletonRenderer extends SkeletonRenderer {
    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath("oceanscurse", "textures/entity/cursed_skeleton.png");

    public CursedSkeletonRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(final SkeletonRenderState state) {
        return TEXTURE;
    }
}
