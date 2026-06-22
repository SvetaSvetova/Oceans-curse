package com.oceanscurse.client;

import com.oceanscurse.entity.WhaleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

/**
 * Renders the whale with our custom {@link WhaleModel}. Texture is a placeholder solid colour
 * for now (proper UV/skin to come).
 */
public class WhaleRenderer extends MobRenderer<WhaleEntity, LivingEntityRenderState, WhaleModel> {
    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath("oceanscurse", "textures/entity/whale.png");

    public WhaleRenderer(final EntityRendererProvider.Context context) {
        super(context, new WhaleModel(context.bakeLayer(WhaleModel.LAYER)), 1.5F);
    }

    @Override
    public Identifier getTextureLocation(final LivingEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
