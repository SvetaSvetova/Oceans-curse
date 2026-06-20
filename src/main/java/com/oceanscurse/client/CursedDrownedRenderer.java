package com.oceanscurse.client;

import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;

/**
 * Renders the Cursed Drowned with the vanilla drowned model but our own "cursed" texture.
 */
public class CursedDrownedRenderer extends DrownedRenderer {
    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath("oceanscurse", "textures/entity/cursed_drowned.png");

    public CursedDrownedRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(final ZombieRenderState state) {
        return TEXTURE;
    }
}
