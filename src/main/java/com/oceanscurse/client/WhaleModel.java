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
 * Whale body model. Geometry was built in Blockbench (Modded Entity export) and ported to the
 * MC 26.2 model API by hand. Single "bone" group of cubes (head/body/tail) + one rotated jaw cube.
 * Texture/UV is still a placeholder solid colour, so the broken export UVs don't matter yet.
 */
public class WhaleModel extends EntityModel<LivingEntityRenderState> {
    public static final ModelLayerLocation LAYER =
        new ModelLayerLocation(Identifier.fromNamespaceAndPath("oceanscurse", "whale"), "main");

    public WhaleModel(final ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        final MeshDefinition mesh = new MeshDefinition();
        final PartDefinition root = mesh.getRoot();

        final PartDefinition bone = root.addOrReplaceChild("bone", CubeListBuilder.create()
            .texOffs(-75, -62).addBox(-26.0F, -43.0F, -30.0F, 25.0F, 44.0F, 76.0F, new CubeDeformation(0.0F))
            .texOffs(-57, -61).addBox(-43.0F, -23.0F, -30.0F, 17.0F, 24.0F, 75.0F, new CubeDeformation(0.0F))
            .texOffs(-53, -57).addBox(-60.0F, -8.0F, -28.0F, 17.0F, 9.0F, 71.0F, new CubeDeformation(0.0F))
            .texOffs(-45, -36).addBox(-81.0F, -66.0F, -17.0F, 34.0F, 36.0F, 50.0F, new CubeDeformation(0.0F))
            .texOffs(-12, -7).addBox(-82.0F, -90.0F, -2.0F, 14.0F, 25.0F, 21.0F, new CubeDeformation(0.0F))
            .texOffs(-12, -7).addBox(38.0F, -49.0F, -33.0F, 8.0F, 9.0F, 21.0F, new CubeDeformation(0.0F))
            .texOffs(-12, -7).addBox(38.0F, -49.0F, 29.0F, 8.0F, 9.0F, 21.0F, new CubeDeformation(0.0F))
            .texOffs(-12, -7).addBox(-102.0F, -65.0F, -2.0F, 21.0F, 10.0F, 21.0F, new CubeDeformation(0.0F))
            .texOffs(-27, -55).addBox(29.0F, -21.0F, -32.0F, 40.0F, 22.0F, 80.0F, new CubeDeformation(0.0F))
            .texOffs(-51, -49).addBox(-1.0F, -71.0F, -28.0F, 70.0F, 72.0F, 74.0F, new CubeDeformation(0.0F))
            .texOffs(0, 2).addBox(-1.0F, -19.0F, -5.0F, 17.0F, 0.0F, 23.0F, new CubeDeformation(0.0F)),
            PartPose.offset(8.0F, 24.0F, -8.0F));

        bone.addOrReplaceChild("cube_r1", CubeListBuilder.create()
            .texOffs(-44, -48).addBox(-3.0F, -30.0F, -29.0F, 17.0F, 28.0F, 62.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(-74.0F, -1.0F, 6.0F, 0.0F, 0.0F, 0.2618F));

        return LayerDefinition.create(mesh, 128, 128);
    }
}
