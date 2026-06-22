package com.oceanscurse.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

/**
 * Ray body model. Built in Blockbench (Modded Entity, box-UV, 512px) and ported to the MC 26.2 model
 * API by hand from the .bbmodel: Blockbench Y is flipped (flip_y), so each cube's MC min-Y corner is
 * {@code -to.y}. Flat disc (front, -Z) + thin tail (+Z) in a single "body" group. Texture/UV is a
 * placeholder solid colour for now.
 */
public class RayModel extends EntityModel<LivingEntityRenderState> {
    public static final ModelLayerLocation LAYER =
        new ModelLayerLocation(Identifier.fromNamespaceAndPath("oceanscurse", "ray"), "main");

    public RayModel(final ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        final MeshDefinition mesh = new MeshDefinition();
        final PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("body", CubeListBuilder.create()
            .texOffs(63, 295).addBox(-19.0F, -5.0F, -25.0F, 28.0F, 6.0F, 28.0F, new CubeDeformation(0.0F))
            .texOffs(89, 315).addBox(-12.0F, -5.0F, 3.0F, 15.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(88, 308).addBox(-6.0F, -5.0F, 11.0F, 3.0F, 6.0F, 15.0F, new CubeDeformation(0.0F))
            .texOffs(66, 303).addBox(-24.0F, -5.0F, -25.0F, 38.0F, 6.0F, 20.0F, new CubeDeformation(0.0F))
            .texOffs(82, 308).addBox(2.0F, -5.0F, -34.0F, 1.0F, 6.0F, 15.0F, new CubeDeformation(0.0F))
            .texOffs(82, 308).addBox(-10.0F, -5.0F, -34.0F, 1.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(mesh, 512, 512);
    }
}
