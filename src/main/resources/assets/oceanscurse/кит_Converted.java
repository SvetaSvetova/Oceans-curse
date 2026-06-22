// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class кит_Converted<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "кит_converted"), "main");
	private final ModelPart bone;
	private final ModelPart bone2;

	public кит_Converted(ModelPart root) {
		this.bone = root.getChild("bone");
		this.bone2 = root.getChild("bone2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 248).addBox(-43.0F, -23.0F, -30.0F, 17.0F, 24.0F, 75.0F, new CubeDeformation(0.0F))
		.texOffs(184, 266).addBox(-60.0F, -8.0F, -28.0F, 17.0F, 9.0F, 71.0F, new CubeDeformation(0.0F))
		.texOffs(288, 0).addBox(-81.0F, -66.0F, -17.0F, 34.0F, 36.0F, 50.0F, new CubeDeformation(0.0F))
		.texOffs(288, 86).addBox(-82.0F, -90.0F, -2.0F, 14.0F, 25.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(80, 347).addBox(38.0F, -49.0F, -33.0F, 8.0F, 9.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(358, 86).addBox(38.0F, -49.0F, 29.0F, 8.0F, 9.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(342, 346).addBox(-102.0F, -65.0F, -2.0F, 21.0F, 10.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(0, 146).addBox(29.0F, -21.0F, -32.0F, 41.0F, 24.0F, 80.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -71.0F, -28.0F, 70.0F, 72.0F, 74.0F, new CubeDeformation(0.0F))
		.texOffs(0, 347).addBox(-1.0F, -19.0F, -5.0F, 17.0F, 0.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(240, 146).addBox(-15.0F, -43.0F, -30.0F, 25.0F, 44.0F, 76.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(184, 346).addBox(-3.0F, -30.0F, -29.0F, 17.0F, 28.0F, 62.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-74.0F, -1.0F, 6.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(240, 146).mirror().addBox(-10.0F, -43.0F, -30.0F, 25.0F, 44.0F, 76.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 146).mirror().addBox(-70.0F, -21.0F, -32.0F, 41.0F, 24.0F, 80.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 248).mirror().addBox(26.0F, -23.0F, -30.0F, 17.0F, 24.0F, 75.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 347).mirror().addBox(-16.0F, -19.0F, -5.0F, 17.0F, 0.0F, 23.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(184, 266).mirror().addBox(43.0F, -8.0F, -28.0F, 17.0F, 9.0F, 71.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(288, 0).mirror().addBox(47.0F, -66.0F, -17.0F, 34.0F, 36.0F, 50.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(342, 346).mirror().addBox(81.0F, -65.0F, -2.0F, 21.0F, 10.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(288, 86).mirror().addBox(68.0F, -90.0F, -2.0F, 14.0F, 25.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(80, 347).mirror().addBox(-46.0F, -49.0F, -33.0F, 8.0F, 9.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(358, 86).mirror().addBox(-46.0F, -49.0F, 29.0F, 8.0F, 9.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-69.0F, -71.0F, -28.0F, 70.0F, 72.0F, 74.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, 24.0F, -8.0F));

		PartDefinition cube_r2 = bone2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(184, 346).mirror().addBox(-14.0F, -30.0F, -29.0F, 17.0F, 28.0F, 62.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(74.0F, -1.0F, 6.0F, 0.0F, 0.0F, -0.2618F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}