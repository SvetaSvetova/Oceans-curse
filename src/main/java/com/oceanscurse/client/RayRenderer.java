package com.oceanscurse.client;

import com.oceanscurse.entity.RayEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

/**
 * Renders the ray with our custom {@link RayModel}. Texture is a placeholder solid colour for now.
 */
public class RayRenderer extends MobRenderer<RayEntity, LivingEntityRenderState, RayModel> {
    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath("oceanscurse", "textures/entity/ray.png");

    public RayRenderer(final EntityRendererProvider.Context context) {
        super(context, new RayModel(context.bakeLayer(RayModel.LAYER)), 0.6F);
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
